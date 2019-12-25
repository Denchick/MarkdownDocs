package markdowndocs.web.app.MarkdownDocsWebApp;

import markdowndocs.auth.IAuthService;
import markdowndocs.documents.sharing.ISharingService;
import markdowndocs.documents.sharing.ShareError;
import markdowndocs.documentstorage.Document;
import markdowndocs.documentstorage.DocumentStorageError;
import markdowndocs.documentstorage.IDocumentStorage;
import markdowndocs.infrastructure.ValueResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/share")
public class ShareController {

    @Autowired
    private IAuthService authService;
    @Autowired
    private IDocumentStorage documentStorage;
    @Autowired
    private ISharingService sharingService;

    @RequestMapping(value = "/{token}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Document> GetDocumentByToken(@PathVariable String token, @RequestHeader("Auth") String authToken, @RequestHeader("userId") UUID userId) {

        if (authService.NotAuthorized(authToken, userId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        ValueResult<UUID, ShareError> result = sharingService.GetDocumentIdByToken(token);

        if (result.isSuccess()) {
            ValueResult<Document, DocumentStorageError> documentResult = documentStorage.GetDocument(result.getValue());

            if (documentResult.isSuccess())
                return new ResponseEntity<>(documentResult.getValue(), HttpStatus.OK);
            DocumentStorageError getDocumentError = documentResult.getError();

            if (getDocumentError == DocumentStorageError.NotFound)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
        ShareError error = result.getError();
        if (error == ShareError.NotFound)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/{documentId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> CreateToken(@PathVariable UUID documentId, @RequestHeader("Auth") String authToken, @RequestHeader("userId") UUID userId) {
        if (authService.NotAuthorized(authToken, userId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        if (authService.NotHaveAccess(userId, documentId))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        ValueResult<Document, DocumentStorageError> result = documentStorage.GetDocument(documentId);

        if (result.isSuccess()) {
            ValueResult<String, ShareError> shareResult = sharingService.ShareDocument(result.getValue().getMetaInfo().getId());
            if (shareResult.isSuccess())
                return new ResponseEntity<>("/share/" + shareResult.getValue(), HttpStatus.OK);

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

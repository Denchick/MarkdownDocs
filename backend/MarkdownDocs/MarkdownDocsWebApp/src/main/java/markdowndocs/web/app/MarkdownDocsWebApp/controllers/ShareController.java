package markdowndocs.web.app.MarkdownDocsWebApp.controllers;

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

    @RequestMapping(value = "/{shareToken}", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> GetDocumentByToken(@PathVariable String shareToken) {
        ValueResult<UUID, ShareError> result = sharingService.GetDocumentIdByToken(shareToken);

        if (result.isSuccess()) {
            ValueResult<Document, DocumentStorageError> documentResult = documentStorage.GetDocument(result.getValue());

            if (documentResult.isSuccess())
                return new ResponseEntity<>(documentResult.getValue().getContent(), HttpStatus.OK);
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

    @RequestMapping(value = "/{documentId}", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> CreateToken(@PathVariable UUID documentId, @RequestHeader("Auth") String authToken, @RequestHeader("userId") UUID userId) {
        if (!authService.Authorized(authToken, userId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        if (!authService.HaveAccess(userId, documentId))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        ValueResult<String, ShareError> shareResult = sharingService.ShareDocument(documentId);
        if (shareResult.isSuccess())
            return new ResponseEntity<>(shareResult.getValue(), HttpStatus.OK);

        if (shareResult.getError() == ShareError.NotFound)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }

}

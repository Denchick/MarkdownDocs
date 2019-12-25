package markdowndocs.web.app.MarkdownDocsWebApp;

import markdowndocs.auth.IAuthService;
import markdowndocs.documentstorage.Document;
import markdowndocs.documentstorage.DocumentStorageError;
import markdowndocs.documentstorage.IDocumentStorage;
import markdowndocs.documentstorage.MetaInfo;
import markdowndocs.infrastructure.Result;
import markdowndocs.infrastructure.ValueResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping(value = "/documents")
public class DocumentsController {

    @Autowired
    private IDocumentStorage documentStorage;
    @Autowired
    private IAuthService authService;

    @Autowired
    public DocumentsController(IDocumentStorage documentStorage) {
        this.documentStorage = documentStorage;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<MetaInfo>> GetDocuments(@RequestHeader("Auth") String authToken, @RequestHeader("userId") UUID userId) {

        if (!authService.Authorized(authToken, userId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        ValueResult<Collection<MetaInfo>, String> result = documentStorage.GetDocumentInfos(userId);

        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getValue(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/{documentId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Document> GetDocument(@PathVariable UUID documentId, @RequestHeader("Auth") String authToken, @RequestHeader("userId") UUID userId) {

        if (!authService.Authorized(authToken, userId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        if (!authService.HaveAccess(userId, documentId))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        ValueResult<Document, DocumentStorageError> result = documentStorage.GetDocument(documentId);

        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getValue(), HttpStatus.OK);
        }

        DocumentStorageError error = result.getError();
        if (error == DocumentStorageError.NotFound)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UUID> CreateDocument(@RequestBody CreateOrUpdateRequestBody createRequestBody, @RequestHeader("Auth") String authToken, @RequestHeader("userId") UUID userId) {
        if (!authService.Authorized(authToken, userId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        if (createRequestBody.title == null) {
            createRequestBody.title = CreateTitleFromContent(createRequestBody.content);
        }

        ValueResult<UUID, String> result = documentStorage.CreateDocument(createRequestBody.title, createRequestBody.content, userId);

        if (result.isSuccess())
            return new ResponseEntity<>(result.getValue(), HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/{documentId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity UpdateDocument(@RequestBody CreateOrUpdateRequestBody createRequestBody, @PathVariable UUID documentId, @RequestHeader("Auth") String authToken, @RequestHeader("userId") UUID userId) {
        if (!authService.Authorized(authToken, userId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        if (!authService.HaveAccess(userId, documentId))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        if (createRequestBody.title == null) {
            createRequestBody.title = CreateTitleFromContent(createRequestBody.content);
        }

        Result<DocumentStorageError> result = documentStorage.UpdateDocument(createRequestBody.title, createRequestBody.content, documentId);

        if (result.isSuccess())
            return new ResponseEntity(HttpStatus.OK);

        DocumentStorageError error = result.getError();
        if (error == DocumentStorageError.NotFound)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/{documentId}", method = RequestMethod.DELETE)
    public ResponseEntity DeleteDocument(@PathVariable UUID documentId, @RequestHeader("Auth") String authToken, @RequestHeader("userId") UUID userId) {

        if (!authService.Authorized(authToken, userId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        if (!authService.HaveAccess(userId, documentId))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Result<DocumentStorageError> result = documentStorage.DeleteDocument(documentId);

        if (result.isSuccess())
            return new ResponseEntity(HttpStatus.OK);

        DocumentStorageError error = result.getError();
        if (error == DocumentStorageError.NotFound)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static String CreateTitleFromContent(String content) {
        StringBuilder titleBuilder = new StringBuilder();
        for (char symbol : content.toCharArray()) {
            if (symbol == '\n')
                break;
            if (Character.isLetter(symbol) || Character.isSpaceChar(symbol))
                titleBuilder.append(symbol);
        }
        String title = titleBuilder.toString();
        if (title.isEmpty())
            return "new document";
        return title;
    }
}



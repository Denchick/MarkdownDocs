package markdowndocs.web.app.MarkdownDocsWebApp;

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

    private IDocumentStorage documentStorage;

    @Autowired
    public DocumentsController(IDocumentStorage documentStorage) {
        this.documentStorage = documentStorage;
    }

    @RequestMapping(value="/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<MetaInfo>> GetDocuments() {
//        ValueResult<Collection<MetaInfo>, DocumentStorageError> result = documentStorage.GetDocumentInfos();
//
//        if (result.isSuccess()) {
//            return new ResponseEntity<>(result.getValue(), HttpStatus.OK);
//        }
//
//        DocumentStorageError error = result.getError();
//        if (error == DocumentStorageError.NotFound)
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/{documentId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Document> GetDocument(@PathVariable UUID documentId) {
        ValueResult<Document, DocumentStorageError> result = documentStorage.GetDocument(documentId);

        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getValue(), HttpStatus.OK);
        }

        DocumentStorageError error = result.getError();
        if (error == DocumentStorageError.NotFound)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value="/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UUID> CreateDocument(@RequestBody Document document) {
//        ValueResult<UUID, Object> result = documentStorage.CreateOrUpdateDocument(document);
//
//        if (result.isSuccess())
//            return new ResponseEntity<>(result.getValue(), HttpStatus.OK);
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/{documentId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity UpdateDocument(@RequestBody Document document) {
//        ValueResult<UUID, Object> result = documentStorage.CreateOrUpdateDocument(document);
//
//        if (result.isSuccess())
//            return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/{documentId}", method = RequestMethod.DELETE)
    public ResponseEntity<Document> DeleteDocument(@PathVariable UUID documentId) {
        Result<DocumentStorageError> result = documentStorage.DeleteDocument(documentId);

        if (result.isSuccess())
            return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
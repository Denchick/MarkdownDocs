package markdowndocs.web.app.MarkdownDocsWebApp;

import markdowndocs.documentstorage.Document;
import markdowndocs.documentstorage.DocumentStorageError;
import markdowndocs.documentstorage.IDocumentStorage;
import markdowndocs.infrastructure.Result;
import markdowndocs.infrastructure.ValueResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/storage")
public class StorageApiController {

    @Autowired
    private IDocumentStorage documentStorage;

    @RequestMapping(value = "/version", method = RequestMethod.GET)
    @ResponseBody
    public String getMyData() {

        return new String("Markdown Docs storage version 1");
    }

    @RequestMapping(value = "/document/{documentName}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity CreateDocument(@PathVariable String documentName, @RequestBody Document document) {
        Result<DocumentStorageError> saveResult = documentStorage.CreateOrUpdateDocument(documentName, document);

        if (saveResult.isSuccess())
            return new ResponseEntity(HttpStatus.OK);

        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @RequestMapping(value = "/documents/{documentName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<Document> GetDocument(@PathVariable String documentName) {
        ValueResult<Document, DocumentStorageError> getDocumentResult = documentStorage.GetDocument(documentName);

        if (getDocumentResult.isSuccess()) {
            Document document = getDocumentResult.getValue();
            return new ResponseEntity<Document>(document, HttpStatus.OK);
        }


        DocumentStorageError storageError = getDocumentResult.getError();

        if (storageError == DocumentStorageError.NotFound)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
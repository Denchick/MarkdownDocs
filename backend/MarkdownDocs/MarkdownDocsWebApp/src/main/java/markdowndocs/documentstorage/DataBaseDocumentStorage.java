package markdowndocs.documentstorage;

import markdowndocs.infrastructure.Result;
import markdowndocs.infrastructure.ValueResult;

import java.util.ArrayList;
import java.util.UUID;

public class DataBaseDocumentStorage implements IDocumentStorage {

    @Override
    public ValueResult<ArrayList<MetaInfo>, DocumentStorageError> GetDocumentsInfoFor(UUID userId) {
        return null;
    }

    @Override
    public ValueResult<Document, DocumentStorageError> GetDocument(String documentPath) {
        return null;
    }

    @Override
    public Result<DocumentStorageError> CreateOrUpdateDocument(UUID userId, Document document) {
        return null;
    }

    @Override
    public Result<DocumentStorageError> DeleteDocument(String documentName) {
        return null;
    }
}

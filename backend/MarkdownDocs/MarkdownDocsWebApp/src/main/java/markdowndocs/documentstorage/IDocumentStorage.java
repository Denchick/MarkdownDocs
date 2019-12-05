package markdowndocs.documentstorage;


import markdowndocs.infrastructure.ValueResult;
import markdowndocs.infrastructure.Result;

import java.util.ArrayList;
import java.util.UUID;

public interface IDocumentStorage {

    ValueResult<ArrayList<MetaInfo>, DocumentStorageError> GetDocumentsInfoFor(UUID userId);

    ValueResult<Document, DocumentStorageError> GetDocument(String documentPath);

    Result<DocumentStorageError> CreateOrUpdateDocument(UUID userId, Document document);

    Result<DocumentStorageError> DeleteDocument(String documentName);
}

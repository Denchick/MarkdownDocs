package markdowndocs.documentstorage;


import markdowndocs.infrastructure.ValueResult;
import markdowndocs.infrastructure.Result;

import java.util.Collection;
import java.util.UUID;

public interface IDocumentStorage {

    ValueResult<Collection<MetaInfo>, DocumentStorageError> GetDocumentInfos();

    ValueResult<Document, DocumentStorageError> GetDocument(UUID documentId);

    ValueResult<UUID, Object> CreateOrUpdateDocument(Document document);

    Result<DocumentStorageError> DeleteDocument(UUID documentId);
}

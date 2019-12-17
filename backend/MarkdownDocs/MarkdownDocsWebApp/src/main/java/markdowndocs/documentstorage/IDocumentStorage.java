package markdowndocs.documentstorage;


import markdowndocs.infrastructure.ValueResult;
import markdowndocs.infrastructure.Result;

import java.util.Collection;
import java.util.UUID;

public interface IDocumentStorage {

    ValueResult<Collection<MetaInfo>, String> GetDocumentInfos(UUID userId);

    ValueResult<Document, DocumentStorageError> GetDocument(UUID documentId);

    ValueResult<UUID, String> CreateDocument(String title, String content, UUID userId);

    Result<DocumentStorageError> UpdateDocument(String title, String content, UUID documentId);

    Result<DocumentStorageError> DeleteDocument(UUID documentId);
}

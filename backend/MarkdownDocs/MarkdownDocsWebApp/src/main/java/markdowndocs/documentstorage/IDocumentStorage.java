package markdowndocs.documentstorage;


import markdowndocs.infrastructure.ValueResult;
import markdowndocs.infrastructure.Result;

import java.util.ArrayList;

public interface IDocumentStorage {

    ValueResult<ArrayList<MetaInfo>, DocumentStorageError> GetDocumentInfo(String userName);

    ValueResult<Document, DocumentStorageError> GetDocument(String documentPath);

    Result<DocumentStorageError> CreateOrUpdateDocument(String documentName, Document document);

    Result<DocumentStorageError> DeleteDocument(String documentName);
}

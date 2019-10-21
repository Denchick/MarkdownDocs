package DicumentStorage;

import Infrastructure.Result;
import Infrastructure.ValueResult;

public interface IDocumentStorage {

    ValueResult<Document, DocumentStorageError> GetDocument(String documentPath);

    Result<DocumentStorageError> CreateDocument(Document document);

    Result<DocumentStorageError> UpdateDocument(Document document);

    Result<DocumentStorageError> DeleteDocument(String documentPath);
}



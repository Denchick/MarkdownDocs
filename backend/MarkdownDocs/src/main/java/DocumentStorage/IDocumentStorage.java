package DocumentStorage;

import Auth.User;
import Infrastructure.Result;
import Infrastructure.ValueResult;

public interface IDocumentStorage {

    ValueResult<Document, DocumentStorageError> GetDocument(String documentPath, User user);

    Result<DocumentStorageError> CreateDocument(Document document, User user);

    Result<DocumentStorageError> UpdateDocument(Document document, User user);

    Result<DocumentStorageError> DeleteDocument(String documentPath, User user);

    ValueResult<String[], DocumentStorageError> GetAllDocumentsTitle(User user);
}



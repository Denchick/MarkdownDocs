package markdowndocs.documentstorage;


import markdowndocs.infrastructure.ValueResult;
import markdowndocs.infrastructure.Result;

public interface IDocumentStorage {
	 	ValueResult<Document, DocumentStorageError> GetDocument(String documentPath);

	 	Result<DocumentStorageError> CreateOrUpdateDocument(String documentName, Document document);

	    Result<DocumentStorageError> DeleteDocument(String documentName);
}

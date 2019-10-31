package markdowndocs.documentstorage;

import javax.naming.spi.DirStateFactory.Result;

import markdowndocs.infrastructure.*;

public interface IDocumentStorage {
	 	ValueResult<Document, DocumentStorageError> GetDocument(String documentPath);

	    Result<DocumentStorageError> CreateDocument(Document document);

	    Result<DocumentStorageError> UpdateDocument(Document document);

	    Result<DocumentStorageError> DeleteDocument(String documentPath);
}

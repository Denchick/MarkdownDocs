package markdowndocs.documentstorage;

import java.util.HashMap;
import java.util.Map;

import markdowndocs.infrastructure.Result;
import markdowndocs.infrastructure.ResultsFactory;
import markdowndocs.infrastructure.ValueResult;

//Should be async 
public class FakeMemoryDocumentStorage implements IDocumentStorage {

	private Map<String, Document> map;

	public FakeMemoryDocumentStorage() {
		map = new HashMap<String, Document>();
	}

	public ValueResult<Document, DocumentStorageError> GetDocument(String documentName) {

		if (map.containsKey(documentName) == false) {
			return ResultsFactory.Failed(DocumentStorageError.NotFound);
		}
		return ResultsFactory.Success(map.get(documentName));
	}
	
	public Result<DocumentStorageError> CreateOrUpdateDocument(String documentName, Document document) {
		map.put(documentName, document);
		return ResultsFactory.Success();
	}

	public Result DeleteDocument(String documentName) {
		if (!map.containsKey(documentName)) {
			return ResultsFactory.FailedWith(DocumentStorageError.NotFound);
		}
		return ResultsFactory.Success();
	}

}

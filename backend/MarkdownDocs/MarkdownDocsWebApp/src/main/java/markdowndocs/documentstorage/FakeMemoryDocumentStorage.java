package markdowndocs.documentstorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import markdowndocs.infrastructure.Result;
import markdowndocs.infrastructure.ResultsFactory;
import markdowndocs.infrastructure.ValueResult;

import static com.sun.tools.javac.util.List.from;

//Should be async 
public class FakeMemoryDocumentStorage implements IDocumentStorage {

    private Map<String, Document> map;

    public FakeMemoryDocumentStorage() {
        map = new HashMap<String, Document>();
    }

    @Override
    public ValueResult<ArrayList<MetaInfo>, DocumentStorageError> GetDocumentInfo(String userName) {
        ArrayList<Document> documents = new ArrayList<Document>(map.values());
        ArrayList<MetaInfo> result = new ArrayList<>();
        for (Document document : documents) {
            result.add(document.getMetaInfo());
        }

        return ResultsFactory.Success(result);

    }

    private MetaInfo GetMeta(Document document) {
        return document.getMetaInfo();
    }

    public ValueResult<Document, DocumentStorageError> GetDocument(String documentName) {

        if (!map.containsKey(documentName)) {
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

package markdowndocs.documentstorage;

import java.util.*;
import markdowndocs.infrastructure.Result;
import markdowndocs.infrastructure.ResultsFactory;
import markdowndocs.infrastructure.ValueResult;

//Should be async
public class FakeMemoryDocumentStorage implements IDocumentStorage {

    private Map<UUID, Document> map;

    public FakeMemoryDocumentStorage() {
        map = new HashMap<>();
    }

    @Override
    public ValueResult<Collection<MetaInfo>, DocumentStorageError> GetDocumentInfos() {
        ArrayList<MetaInfo> metaInfos = new ArrayList<>();
        for (Map.Entry<UUID, Document> document : map.entrySet()) {
            metaInfos.add(document.getValue().getMetaInfo());
        }
        return ResultsFactory.Success(metaInfos);
    }

    @Override
    public ValueResult<Document, DocumentStorageError> GetDocument(UUID documentId) {
        if (!map.containsKey(documentId)) {
            return ResultsFactory.Failed(DocumentStorageError.NotFound);
        }
        return ResultsFactory.Success(map.get(documentId));
    }

    @Override
    public ValueResult<UUID, Object> CreateOrUpdateDocument(Document document) {
        document.getMetaInfo().setId(UUID.randomUUID());
        map.put(document.getMetaInfo().getId(), document);
        return ResultsFactory.Success(document.getMetaInfo().getId());
    }

    @Override
    public Result<DocumentStorageError> DeleteDocument(UUID documentId) {
        map.put(documentId, null);
        return ResultsFactory.Success();
    }
}

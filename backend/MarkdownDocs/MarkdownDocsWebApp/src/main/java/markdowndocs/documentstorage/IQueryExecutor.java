package markdowndocs.documentstorage;

import markdowndocs.OrmPersistents.DocumentEntity;

import java.util.List;
import java.util.UUID;

public interface IQueryExecutor {
    public DocumentEntity GetDocumentBy(UUID id) throws Exception;

    public List<DocumentEntity> GetMetaInfoBy(UUID userId) throws Exception;

    public void Create(DocumentEntity entity) throws Exception;

    public void Update(DocumentEntity entity) throws Exception;

    public void DeleteById(UUID id) throws Exception;

    public boolean EntityExist(UUID id);
}

package markdowndocs.documentstorage;

import markdowndocs.OrmPersistents.DocumentEntity;

import java.util.List;
import java.util.UUID;

public interface IQueryExecutor {
    public <T> T GetEntityBy(UUID id) throws Exception;

    public List<DocumentEntity> GetMetaInfoBy(UUID userId) throws Exception;

    public <T> void Create(T entity) throws Exception;

    public <T> void Update(T entity) throws Exception;

    public void DeleteById(UUID id) throws Exception;

    public boolean EntityExist(UUID id);
}

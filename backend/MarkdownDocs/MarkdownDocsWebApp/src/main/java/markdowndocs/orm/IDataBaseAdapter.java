package markdowndocs.orm;

import markdowndocs.OrmPersistents.DocumentEntity;

import java.util.List;
import java.util.UUID;

public interface IDataBaseAdapter {
    public <T> T GetEntityBy(UUID id, Class<T> type) throws Exception;

    public <T> T GetEntityBy(String id, Class<T> type) throws Exception;

    public List<DocumentEntity> GetMetaInfoBy(UUID userId) throws Exception;

    public <T> void Create(T entity) throws Exception;

    public <T> void Update(T entity) throws Exception;

    public void DeleteById(UUID id) throws Exception;

    public <T> void DeleteById(String id, Class<T> type) throws Exception;

    public boolean EntityExist(UUID id);

    public <T> List<T> GetEntityWhereEq(String columnName, Object value, Class<T> type);
}

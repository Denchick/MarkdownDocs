package markdowndocs.documentstorage;

import markdowndocs.OrmPersistents.DocumentEntity;

import java.util.List;
import java.util.UUID;

public interface IQueryExecutor {
    public DocumentEntity GetDocumentBy(UUID id);

    public List<DocumentEntity> GetMetaInfoBy(UUID userId);

    public void Create(DocumentEntity entity);

    public void Update(DocumentEntity entity);

    public void DeleteById(UUID id);
}

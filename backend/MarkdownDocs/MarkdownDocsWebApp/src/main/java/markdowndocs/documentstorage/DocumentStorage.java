package markdowndocs.documentstorage;

import markdowndocs.OrmPersistents.DocumentEntity;
import markdowndocs.infrastructure.Result;
import markdowndocs.infrastructure.ResultsFactory;
import markdowndocs.infrastructure.ValueResult;
import org.hibernate.HibernateError;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DocumentStorage implements IDocumentStorage {

    @Autowired
    private Logger logger;
    @Autowired
    private IQueryExecutor queryExecutor;

    public DocumentStorage() {
    }

    public DocumentStorage(IQueryExecutor queryExecutor, Logger logger) {
        this.queryExecutor = queryExecutor;
        this.logger = logger;
    }


    @Override
    public ValueResult<Collection<MetaInfo>, String> GetDocumentInfos(UUID userId) {
        try {

            List<MetaInfo> result = new ArrayList<MetaInfo>();

            List<DocumentEntity> rows = queryExecutor.GetMetaInfoBy(userId);
            if (rows.size() == 0)
                return ResultsFactory.Success(new ArrayList<MetaInfo>() {
                });

            for (DocumentEntity row : rows) {
                MetaInfo metaInfo = EntityConverter.DbEntityToDocument(row).getMetaInfo();

                result.add(metaInfo);
            }

            return ResultsFactory.Success(result);

        } catch (HibernateError error) {
            logger.log(Level.SEVERE, error.getMessage());
            return ResultsFactory.Failed("Can not save document to storage");
        }
    }

    @Override
    public ValueResult<Document, DocumentStorageError> GetDocument(UUID documentId) {
        try {

            DocumentEntity documentEntity = queryExecutor.GetDocumentBy(documentId);
            return ResultsFactory.Success(EntityConverter.DbEntityToDocument(documentEntity));

        } catch (HibernateError error) {
            logger.log(Level.SEVERE, error.getMessage());
            return ResultsFactory.Failed(DocumentStorageError.UnknownError);
        }
    }

    @Override
    public ValueResult<UUID, String> CreateDocument(String title, String content, UUID userId) {

        Document newDocument = Document.CreateBy(title, content);
        DocumentEntity newDbDocumentEntity = EntityConverter.DocumentToDbEntity(newDocument, userId);

        try {
            queryExecutor.Create(newDbDocumentEntity);
        } catch (HibernateError error) {
            logger.log(Level.SEVERE, error.getMessage());
            return ResultsFactory.Failed("Can not save document to storage");
        }

        return ResultsFactory.Success(newDocument.getMetaInfo().getId());
    }

    @Override
    public Result<DocumentStorageError> UpdateDocument(Document newDocument, UUID userId) {
        DocumentEntity newDbDocumentEntity = EntityConverter.DocumentToDbEntity(newDocument, userId);

        try {
            queryExecutor.Update(newDbDocumentEntity);

        } catch (HibernateError error) {
            logger.log(Level.SEVERE, error.getMessage());
            return ResultsFactory.FailedWith(DocumentStorageError.UnknownError);
        }

        return ResultsFactory.Success();

    }

    @Override
    public Result<DocumentStorageError> DeleteDocument(UUID documentId) {
        try {
            queryExecutor.DeleteById(documentId);

        } catch (HibernateError error) {
            logger.log(Level.SEVERE, error.getMessage());
            return ResultsFactory.FailedWith(DocumentStorageError.UnknownError);
        }

        return ResultsFactory.Success();
    }
}



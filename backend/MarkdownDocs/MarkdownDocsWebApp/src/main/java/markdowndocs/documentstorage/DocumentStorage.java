package markdowndocs.documentstorage;

import markdowndocs.OrmPersistents.DocumentEntity;
import markdowndocs.infrastructure.Result;
import markdowndocs.infrastructure.ResultsFactory;
import markdowndocs.infrastructure.ValueResult;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DocumentStorage implements IDocumentStorage {

    private Logger logger;
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
                MetaInfo metaInfo = StorageEntityConverter.DbEntityToDocument(row).getMetaInfo();

                result.add(metaInfo);
            }

            return ResultsFactory.Success(result);

        } catch (Exception error) {
            logger.log(Level.SEVERE, error.getMessage());
            return ResultsFactory.Failed("Can not save document to storage");
        }
    }

    @Override
    public ValueResult<Document, DocumentStorageError> GetDocument(UUID documentId) {
        try {
            DocumentEntity documentEntity = queryExecutor.GetEntityBy(documentId, DocumentEntity.class);
            if (documentEntity == null) {
                logger.log(Level.SEVERE, "Can not get document " + documentId + ". Document not found");
                return ResultsFactory.Failed(DocumentStorageError.NotFound);
            }
            return ResultsFactory.Success(StorageEntityConverter.DbEntityToDocument(documentEntity));

        } catch (Exception error) {
            logger.log(Level.SEVERE, "Can not get document with id" + documentId + ". " + error.getMessage());
            return ResultsFactory.Failed(DocumentStorageError.UnknownError);
        }
    }

    @Override
    public ValueResult<UUID, String> CreateDocument(String title, String content, UUID userId) {

        Document newDocument = Document.CreateBy(title, content);
        DocumentEntity newDbDocumentEntity = StorageEntityConverter.DocumentToDbEntity(newDocument, userId);
        try {
            if (queryExecutor.EntityExist(newDbDocumentEntity.getId())) {
                logger.log(Level.WARNING, "Try create document with id " + newDbDocumentEntity.getId() + ". Id already exist");
                return ResultsFactory.Failed("Document with this already exist");
            }

            queryExecutor.Create(newDbDocumentEntity);
        } catch (Exception error) {
            logger.log(Level.SEVERE, error.getMessage());
            return ResultsFactory.Failed("Can not save document to storage");
        }

        return ResultsFactory.Success(newDocument.getMetaInfo().getId());
    }

    @Override
    public Result<DocumentStorageError> UpdateDocument(String title, String content, UUID documentId) {
        try {

            DocumentEntity storedEntity = queryExecutor.GetEntityBy(documentId, DocumentEntity.class);

            if (storedEntity == null) {
                logger.log(Level.SEVERE, "Can not update document: document with id " + documentId + " not found");
                return ResultsFactory.FailedWith(DocumentStorageError.NotFound);
            }
            storedEntity.setTitle(title);
            storedEntity.setContent(content);
            storedEntity.setEditedAt(new Timestamp(System.currentTimeMillis()));

            queryExecutor.Update(storedEntity);
        } catch (Exception error) {
            logger.log(Level.SEVERE, "Can not update document: " + error.getMessage());
            return ResultsFactory.FailedWith(DocumentStorageError.UnknownError);
        }
        return ResultsFactory.Success();
    }

    @Override
    public Result<DocumentStorageError> DeleteDocument(UUID documentId) {
        try {
            if (!queryExecutor.EntityExist(documentId)) {
                logger.log(Level.SEVERE, "Can not delete document with id " + documentId + ". Document not found");
                return ResultsFactory.FailedWith(DocumentStorageError.NotFound);
            }
            queryExecutor.DeleteById(documentId);
        } catch (Exception error) {
            logger.log(Level.SEVERE, "Can not delete document with id " + documentId + ". " + error.getMessage());
            return ResultsFactory.FailedWith(DocumentStorageError.UnknownError);
        }

        return ResultsFactory.Success();
    }
}



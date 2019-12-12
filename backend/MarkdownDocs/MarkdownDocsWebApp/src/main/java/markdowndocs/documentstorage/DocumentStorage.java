package markdowndocs.documentstorage;

import markdowndocs.OrmPersistents.DocumentEntity;
import markdowndocs.infrastructure.Result;
import markdowndocs.infrastructure.ResultsFactory;
import markdowndocs.infrastructure.ValueResult;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DocumentStorage implements IDocumentStorage {

    @Autowired
    private SessionFactory sessionFactory;
    private Logger logger;
    private String currentConnectionString;

    public DocumentStorage() {
    }

    public DocumentStorage(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public ValueResult<Collection<MetaInfo>, String> GetDocumentInfos(UUID userId) {
        try {

            List<MetaInfo> result = new ArrayList<MetaInfo>();

            Session session = sessionFactory.openSession();
            session.beginTransaction();
            String queryString = " select id, title,  createAt, editedAt from " + currentConnectionString + "where owner = " + userId.toString();
            Query query = session.createQuery(queryString);
            List queryResult = query.list();
            session.getTransaction().commit();
            if (queryResult.size() == 0)
                return ResultsFactory.Success(new ArrayList<MetaInfo>() {
                });

            List<Object[]> rows = new ArrayList<Object[]>(queryResult);
            for (Object[] row : rows) {
                UUID id = UUID.fromString(row[0].toString());
                String title = row[1].toString();
                Timestamp createAt = new Timestamp((int) row[2]);
                Timestamp editedAt = new Timestamp((int) row[3]);

                MetaInfo metaInfo = new MetaInfo();
                metaInfo.setTitle(title);
                metaInfo.setId(id);
                metaInfo.setCreateAt(createAt);
                metaInfo.setEditedAt(editedAt);

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
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            String queryString = " select id, title,  createAt, editedAt, content from " + currentConnectionString + "where id = " + documentId.toString();
            Query query = session.createQuery(queryString);
            //should use session.load(MyObject.class,id);
            List queryResult = query.list();
            session.getTransaction().commit();
            if (queryResult.size() == 0)
                return ResultsFactory.Failed(DocumentStorageError.NotFound);

            if (queryResult.size() > 1) {
                logger.log(Level.SEVERE, "find several documents with " + documentId.toString() + " in storage");
                return ResultsFactory.Failed(DocumentStorageError.UnknownError);
            }

            List<Object[]> rows = new ArrayList<Object[]>(queryResult);
            Object[] row = rows.get(0);

            UUID id = UUID.fromString(row[0].toString());
            String title = row[1].toString();
            Timestamp createAt = new Timestamp((int) row[2]);
            Timestamp editedAt = new Timestamp((int) row[3]);

            MetaInfo metaInfo = new MetaInfo();
            metaInfo.setTitle(title);
            metaInfo.setId(id);
            metaInfo.setCreateAt(createAt);
            metaInfo.setEditedAt(editedAt);

            String content = row[4].toString();

            return ResultsFactory.Success(new Document(metaInfo, content));

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
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(newDbDocumentEntity);
            session.getTransaction().commit();

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
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.update(newDbDocumentEntity);
            session.getTransaction().commit();

        } catch (HibernateError error) {
            logger.log(Level.SEVERE, error.getMessage());
            return ResultsFactory.FailedWith(DocumentStorageError.UnknownError);
        }

        return ResultsFactory.Success();

    }

    @Override
    public Result<DocumentStorageError> DeleteDocument(UUID documentId) {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            Object record = session.load(Document.class, documentId);
            session.delete(record);
            session.getTransaction().commit();

        } catch (HibernateError error) {
            logger.log(Level.SEVERE, error.getMessage());
            return ResultsFactory.FailedWith(DocumentStorageError.UnknownError);
        }

        return ResultsFactory.Success();
    }
}



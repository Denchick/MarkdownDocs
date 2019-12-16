package markdowndocs.documentstorage;

import markdowndocs.OrmPersistents.DocumentEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.UUID;

public class DataStorageQueryExecutor implements IQueryExecutor {

    private String currentConnectionString;
    private SessionFactory sessionFactory;

    public DataStorageQueryExecutor() {

    }

    public DataStorageQueryExecutor(SessionFactory sessionFactory, String currentConnectionString) {
        this.currentConnectionString = currentConnectionString;
        this.sessionFactory = sessionFactory;
    }

    public DocumentEntity GetDocumentBy(UUID id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        DocumentEntity result = session.load(DocumentEntity.class, id.toString());
        session.getTransaction().commit();

        return result;
    }

    public List<Object[]> GetMetaInfoBy(UUID userId) {
        String queryString = " select id, title,  createAt, editedAt from " + currentConnectionString + "where owner = " + userId.toString();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery(queryString);
        List queryResult = query.list();
        session.getTransaction().commit();
        return (List<Object[]>) queryResult;
    }

    public void Create(DocumentEntity entity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
    }

    public void Update(DocumentEntity entity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
    }

    public void DeleteById(UUID id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Object record = session.load(Document.class, id);
        session.delete(record);
        session.getTransaction().commit();
    }

}

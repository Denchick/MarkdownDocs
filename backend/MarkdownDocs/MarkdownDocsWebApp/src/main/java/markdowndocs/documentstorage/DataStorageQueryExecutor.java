package markdowndocs.documentstorage;

import markdowndocs.OrmPersistents.DocumentEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;

import java.lang.annotation.Retention;
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
        DocumentEntity result = session.load(DocumentEntity.class, id);
        session.getTransaction().commit();

        return result;
    }

    public List<DocumentEntity> GetMetaInfoBy(UUID userId) {

        Session session = sessionFactory.openSession();
        Criteria cr = session.createCriteria(DocumentEntity.class)
                .add(Restrictions.like("owner", userId))
                .setProjection(Projections.projectionList()
                        .add(Projections.property("id"), "id")
                        .add(Projections.property("title"), "title")
                        .add(Projections.property("createAt"), "createAt")
                        .add(Projections.property("editedAt"), "editedAt")
                        .add(Projections.property("owner"), "owner"))
                .setResultTransformer(Transformers.aliasToBean(DocumentEntity.class));
        session.beginTransaction();
        List<DocumentEntity> result = cr.list();
        session.getTransaction().commit();
        return result;
    }

    public void Create(DocumentEntity entity) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        session.close();
    }

    public void Update(DocumentEntity entity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
        session.close();
    }

    public void DeleteById(UUID id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        DocumentEntity record = session.get(DocumentEntity.class,id);
        session.getTransaction().commit();
        session.beginTransaction();
        session.delete(record);
        session.getTransaction().commit();
        session.close();
    }

}

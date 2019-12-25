package markdowndocs.orm;

import markdowndocs.OrmPersistents.DocumentEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import java.util.List;
import java.util.UUID;

public class DataBaseAdapter implements IDataBaseAdapter {

    private SessionFactory sessionFactory;

    public DataBaseAdapter() {

    }

    public DataBaseAdapter(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public <T> T GetEntityBy(UUID id, Class<T> type) throws Exception {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        T result = session.get(type, id);
        session.getTransaction().commit();

        return result;
    }

    public List<DocumentEntity> GetMetaInfoBy(UUID userId) throws Exception {

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

    @Override
    public <T> void Create(T entity) throws Exception {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public <T> void Update(T entity) throws Exception {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
        session.close();
    }


    public void DeleteById(UUID id) throws Exception {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        DocumentEntity record = session.get(DocumentEntity.class, id);
        session.getTransaction().commit();
        session.beginTransaction();
        session.delete(record);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public boolean EntityExist(UUID id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        DocumentEntity result = session.get(DocumentEntity.class, id);
        session.getTransaction().commit();
        session.close();

        return result != null;
    }

}

package markdowndocs.documentstorage.tests;

import markdowndocs.OrmPersistents.DocumentEntity;
import markdowndocs.OrmPersistents.UserEntity;
import markdowndocs.orm.DataBaseAdapter;
import markdowndocs.documentstorage.Document;
import markdowndocs.documentstorage.StorageEntityConverter;
import markdowndocs.documentstorage.MetaInfo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class DataStorageQueryExecutorTests {
    private SessionFactory sessionFactory;
    private DataBaseAdapter queryExecutor;

    @Before
    public void Before() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(DocumentEntity.class).addAnnotatedClass(UserEntity.class);
        configuration.setProperty("hibernate.dialect",
                "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class",
                "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./testing_db");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        sessionFactory = configuration.buildSessionFactory();

        queryExecutor = new DataBaseAdapter(sessionFactory);

    }

    @Test
    public void Should_create_entity_in_db() throws Exception {

        DocumentEntity testEntity = CreateTestEntity();

        queryExecutor.Create(testEntity);

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        DocumentEntity storedEntity = session.get(DocumentEntity.class, testEntity.getId());
        session.getTransaction().commit();
        session.close();

        assertEquals(testEntity, storedEntity);
    }

    @Test
    public void Should_update_document_in_db() throws Exception {
        DocumentEntity testEntity = CreateTestEntity();
        queryExecutor.Create(testEntity);

        String newContent = "new Content";
        testEntity.setContent(newContent);
        queryExecutor.Update(testEntity);

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        DocumentEntity storedEntity = session.get(DocumentEntity.class, testEntity.getId());
        session.getTransaction().commit();
        session.close();
        assertEquals(storedEntity.getId(), testEntity.getId());
        assertEquals(storedEntity.getCreateAt(), testEntity.getCreateAt());
        assertEquals(storedEntity.getEditedAt(), testEntity.getEditedAt());
        assertEquals(storedEntity.getTitle(), testEntity.getTitle());
        assertEquals(storedEntity.getOwner(), testEntity.getOwner());
        assertEquals(storedEntity.getContent(), newContent);
    }

    @Test
    public void Should_return_saved_document_from_db() throws Exception {
        DocumentEntity testEntity = CreateTestEntity();
        queryExecutor.Create(testEntity);

        DocumentEntity storedEntity = queryExecutor.GetEntityBy(testEntity.getId(), DocumentEntity.class);

        assertEquals(storedEntity, testEntity);
    }

    @Test
    public void Should_return_all_metainfo_for_user() throws Exception {
        UUID userId = UUID.randomUUID();
        DocumentEntity entity1 = CreateTestEntity();
        DocumentEntity entity2 = CreateTestEntity();
        entity1.setOwner(userId);
        entity2.setOwner(userId);

        queryExecutor.Create(entity1);
        queryExecutor.Create(entity2);

        Document entity1AsDocument = StorageEntityConverter.DbEntityToDocument(entity1);
        Document entity2AsDocument = StorageEntityConverter.DbEntityToDocument(entity2);

        List<DocumentEntity> rows = queryExecutor.GetMetaInfoBy(userId);
        List<MetaInfo> actualMetas = new ArrayList<>();

        for (DocumentEntity row : rows
        ) {
            actualMetas.add(StorageEntityConverter.DbEntityToDocument(row).getMetaInfo());
        }

        assertTrue(actualMetas.contains(entity1AsDocument.getMetaInfo()));
        assertTrue(actualMetas.contains(entity2AsDocument.getMetaInfo()));
    }

    @Test
    public void Should_delete_entity_from_db() throws Exception {
        DocumentEntity testEntity = CreateTestEntity();
        queryExecutor.Create(testEntity);

        queryExecutor.DeleteById(testEntity.getId());

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        DocumentEntity storedEntity = session.get(DocumentEntity.class, testEntity.getId());
        session.getTransaction().commit();
        session.close();


        assertNull(storedEntity);
    }

    @Test
    public void Should_return_true_when_entity_in_storage() throws Exception {
        DocumentEntity entity = CreateTestEntity();
        queryExecutor.Create(entity);

        boolean entityExist = queryExecutor.EntityExist(entity.getId());

        assertTrue(entityExist);
    }

    @Test
    public void Should_return_false_when_entity_not_in_storage() throws Exception {
        DocumentEntity entity = CreateTestEntity();

        boolean entityExist = queryExecutor.EntityExist(entity.getId());

        assertFalse(entityExist);
    }

    @Test
    public void Should_return_other_entity() throws Exception {
        DocumentEntity documentEntity = CreateTestEntity();
        UserEntity userEntity = CreateTestUser();
        queryExecutor.Create(documentEntity);
        queryExecutor.Create(userEntity);

        DocumentEntity storedEntity = queryExecutor.GetEntityBy(documentEntity.getId(), DocumentEntity.class);
        UserEntity storedUser = queryExecutor.GetEntityBy(userEntity.getId(), UserEntity.class);

        assertEquals(documentEntity, storedEntity);
        assertEquals(userEntity, storedUser);

    }


    private DocumentEntity CreateTestEntity() {
        DocumentEntity testEntity = new DocumentEntity();
        testEntity.setId(UUID.randomUUID());
        testEntity.setOwner(UUID.randomUUID());
        testEntity.setCreateAt(new Timestamp(System.currentTimeMillis()));
        testEntity.setEditedAt(new Timestamp(System.currentTimeMillis()));
        testEntity.setTitle("title");
        testEntity.setContent("some content");

        return testEntity;
    }

    private UserEntity CreateTestUser() {
        UserEntity testUser = new UserEntity();
        testUser.setId(UUID.randomUUID());
        testUser.setLogin("login");
        testUser.setPasswordHash("hash");
        return testUser;
    }

    @After
    public void after() {
        sessionFactory.close();
    }
}

package markdowndocs.documentstorage.tests;

import junit.framework.Assert;
import markdowndocs.OrmPersistents.DocumentEntity;
import markdowndocs.OrmPersistents.UserEntity;
import markdowndocs.documentstorage.DataStorageQueryExecutor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.UUID;


public class ORMDocumentStorageIntegrationTests extends Assert {

    private SessionFactory sessionFactory;

    @BeforeAll
    public void Before() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(DocumentEntity.class).addAnnotatedClass(UserEntity.class);
        configuration.setProperty("hibernate.dialect",
                "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class",
                "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:test_db");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        sessionFactory = configuration.buildSessionFactory();
    }

    @Test
    public void SimpleTest() {
        int a = 1;
        assertEquals(1, a);
    }
}

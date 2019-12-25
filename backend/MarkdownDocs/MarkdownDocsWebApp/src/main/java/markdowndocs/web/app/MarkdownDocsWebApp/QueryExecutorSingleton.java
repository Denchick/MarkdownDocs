package markdowndocs.web.app.MarkdownDocsWebApp;

import markdowndocs.OrmPersistents.DocumentEntity;
import markdowndocs.OrmPersistents.UserEntity;
import markdowndocs.documentstorage.DataStorageQueryExecutor;
import markdowndocs.documentstorage.IQueryExecutor;
import org.hibernate.SessionFactory;

public class QueryExecutorSingleton {

    private static IQueryExecutor queryExecutor;

    public static IQueryExecutor create() {
        if (queryExecutor != null)
            return queryExecutor;

        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.addAnnotatedClass(DocumentEntity.class).addAnnotatedClass(UserEntity.class);
        configuration.setProperty("hibernate.dialect",
                "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class",
                "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./test_db;DB_CLOSE_ON_EXIT=FALSE");
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        queryExecutor = new DataStorageQueryExecutor(sessionFactory);

        return queryExecutor;

    }
}

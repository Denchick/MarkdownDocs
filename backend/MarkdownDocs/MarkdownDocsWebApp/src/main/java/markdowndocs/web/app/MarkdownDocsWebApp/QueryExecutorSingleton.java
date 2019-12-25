package markdowndocs.web.app.MarkdownDocsWebApp;


import markdowndocs.OrmPersistents.DocumentEntity;
import markdowndocs.OrmPersistents.ShareEntity;
import markdowndocs.OrmPersistents.UserEntity;
import markdowndocs.orm.DataBaseAdapter;
import markdowndocs.orm.IDataBaseAdapter;
import org.hibernate.SessionFactory;


public class QueryExecutorSingleton {

    private static IDataBaseAdapter queryExecutor;

    public static IDataBaseAdapter create() {
        if (queryExecutor != null)
            return queryExecutor;

        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.addAnnotatedClass(DocumentEntity.class).addAnnotatedClass(UserEntity.class).addAnnotatedClass(ShareEntity.class);
        configuration.setProperty("hibernate.dialect",
                "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class",
                "org.h2.Driver");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./test_db;DB_CLOSE_ON_EXIT=FALSE");
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        queryExecutor = new DataBaseAdapter(sessionFactory);

        return queryExecutor;
    }
}

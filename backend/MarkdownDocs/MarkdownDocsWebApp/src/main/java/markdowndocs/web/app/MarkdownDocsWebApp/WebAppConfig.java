package markdowndocs.web.app.MarkdownDocsWebApp;

import markdowndocs.OrmPersistents.DocumentEntity;
import markdowndocs.OrmPersistents.UserEntity;
import markdowndocs.auth.FakeAuthService;
import markdowndocs.auth.IAuthService;
import markdowndocs.documentstorage.DataStorageQueryExecutor;
import markdowndocs.documentstorage.DocumentStorage;
import markdowndocs.documentstorage.IDocumentStorage;
import markdowndocs.documentstorage.IQueryExecutor;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import java.util.logging.Logger;

@Configuration
@EnableWebMvc
@ComponentScan("markdowndocs.web.app.MarkdownDocsWebApp")
public class WebAppConfig {

    @Bean
    public UrlBasedViewResolver setupViewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix("/pages/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);

        return resolver;
    }

    @Bean
    public IDocumentStorage setupDocumentStorageResolver() {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.addAnnotatedClass(DocumentEntity.class).addAnnotatedClass(UserEntity.class);
        configuration.setProperty("hibernate.dialect",
                "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class",
                "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./test_db");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        IQueryExecutor queryExecutor = new DataStorageQueryExecutor(sessionFactory);
        Logger logger = Logger.getLogger(DocumentStorage.class.getName());
        return new DocumentStorage(queryExecutor, logger);

    }

    @Bean
    public IAuthService setupAuthServiceResolver() {
        return new FakeAuthService();
    }

}

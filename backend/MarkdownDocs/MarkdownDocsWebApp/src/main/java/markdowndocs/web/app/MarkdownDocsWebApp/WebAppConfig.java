package markdowndocs.web.app.MarkdownDocsWebApp;

import markdowndocs.auth.*;
import markdowndocs.auth.helpers.CredentialsValidator;
import markdowndocs.auth.helpers.IAuthValidator;
import markdowndocs.documentstorage.DocumentStorage;
import markdowndocs.documentstorage.IDocumentStorage;
import markdowndocs.orm.IDataBaseAdapter;
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
        IDataBaseAdapter queryExecutor = QueryExecutorSingleton.create();
        Logger logger = LoggerSingleton.create();
        return new DocumentStorage(queryExecutor, logger);

    }

    @Bean
    public IAuthService setupAuthServiceResolver() {
        IAuthValidator authValidator = new CredentialsValidator();
        IDataBaseAdapter queryExecutor = QueryExecutorSingleton.create();
        Logger logger = LoggerSingleton.create();
        return new AuthService(queryExecutor, authValidator, logger);
    }
}


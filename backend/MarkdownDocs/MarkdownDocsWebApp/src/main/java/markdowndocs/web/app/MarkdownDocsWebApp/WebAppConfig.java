package markdowndocs.web.app.MarkdownDocsWebApp;

import markdowndocs.auth.FakeAuthService;
import markdowndocs.auth.IAuthService;
import markdowndocs.documentstorage.DocumentStorage;
import markdowndocs.documentstorage.IDocumentStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

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
        return new DocumentStorage();
    }

    @Bean
    public IAuthService setupAuthService() {
        return new FakeAuthService();
    }

}

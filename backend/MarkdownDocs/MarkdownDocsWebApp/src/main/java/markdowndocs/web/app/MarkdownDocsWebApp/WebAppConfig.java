package markdowndocs.web.app.MarkdownDocsWebApp;

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
	        // указываем где будут лежать наши веб-страницы
	        resolver.setPrefix("/pages/");
	        // формат View который мы будем использовать
	        resolver.setSuffix(".jsp");
	        resolver.setViewClass(JstlView.class);

	        return resolver;
	    }
	}

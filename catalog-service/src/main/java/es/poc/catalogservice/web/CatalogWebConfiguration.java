package es.poc.catalogservice.web;

import es.poc.catalogservice.backend.CatalogBackendConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@Import({CatalogBackendConfiguration.class})
@ComponentScan({ "es.poc.common", "es.poc.catalogservice.web"})
public class CatalogWebConfiguration extends WebMvcConfigurerAdapter {
}

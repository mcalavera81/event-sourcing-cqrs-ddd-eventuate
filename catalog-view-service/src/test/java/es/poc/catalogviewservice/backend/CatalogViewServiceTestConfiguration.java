package es.poc.catalogviewservice.backend;

import es.poc.catalogviewservice.backend.CatalogViewMongoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import(CatalogViewMongoConfiguration.class)
public class CatalogViewServiceTestConfiguration {
}

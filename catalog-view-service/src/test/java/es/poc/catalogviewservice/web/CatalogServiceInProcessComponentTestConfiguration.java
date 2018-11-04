package es.poc.catalogviewservice.web;

import io.eventuate.javaclient.spring.jdbc.EmbeddedTestAggregateStoreConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CatalogViewWebConfiguration.class,
        EmbeddedTestAggregateStoreConfiguration.class
        })
@EnableAutoConfiguration
public class CatalogServiceInProcessComponentTestConfiguration {


}

package es.poc.catalogservice;

import es.poc.catalogservice.web.CatalogWebConfiguration;
import io.eventuate.javaclient.spring.jdbc.EmbeddedTestAggregateStoreConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CatalogWebConfiguration.class,
        EmbeddedTestAggregateStoreConfiguration.class
        })
@EnableAutoConfiguration
public class CatalogServiceInProcessComponentTestConfiguration {


}

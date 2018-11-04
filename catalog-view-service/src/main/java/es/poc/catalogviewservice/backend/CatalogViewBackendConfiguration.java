package es.poc.catalogviewservice.backend;

import es.poc.catalogviewservice.backend.handlers.CatalogViewEventSubscriber;
import es.poc.catalogviewservice.backend.service.CatalogViewService;
import es.poc.catalogviewservice.backend.service.CatalogViewServiceImpl;
import io.eventuate.javaclient.spring.EnableEventHandlers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(CatalogViewMongoConfiguration.class)
@EnableEventHandlers
public class CatalogViewBackendConfiguration {

  @Bean
  public CatalogViewEventSubscriber orderHistoryViewWorkflow(CatalogViewService service) {
    return new CatalogViewEventSubscriber(service);
  }

}

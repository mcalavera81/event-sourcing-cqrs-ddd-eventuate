package es.poc.catalogviewservice.backend;

import es.poc.catalogviewservice.backend.repository.CatalogViewRepository;
import es.poc.catalogviewservice.backend.service.CatalogViewService;
import es.poc.catalogviewservice.backend.service.CatalogViewServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories
public class CatalogViewMongoConfiguration {


  @Bean
  public CatalogViewService orderHistoryViewService(
    CatalogViewRepository catalogViewRepository,
    MongoTemplate mongoTemplate) {

    return new CatalogViewServiceImpl(catalogViewRepository, mongoTemplate);
  }
}

package es.poc.catalogviewservice.backend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class CatalogViewRepositoryImpl implements CatalogViewRepositoryCustom {

  private MongoTemplate mongoTemplate;

  @Autowired
  public CatalogViewRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }


}

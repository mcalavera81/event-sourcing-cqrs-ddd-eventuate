package es.poc.orderservice.backend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

  private MongoTemplate mongoTemplate;

  @Autowired
  public ProductRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }


}

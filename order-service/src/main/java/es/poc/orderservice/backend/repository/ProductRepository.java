package es.poc.orderservice.backend.repository;


import es.poc.orderservice.backend.domain.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository
  extends MongoRepository<Product, String>, ProductRepositoryCustom {
}

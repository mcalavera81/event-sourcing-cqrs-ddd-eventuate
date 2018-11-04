package es.poc.catalogviewservice.backend.repository;


import es.poc.catalogviewservice.backend.domain.CatalogView;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CatalogViewRepository extends MongoRepository<CatalogView, String>, CatalogViewRepositoryCustom {
}

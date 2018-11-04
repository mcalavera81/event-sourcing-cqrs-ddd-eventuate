package es.poc.catalogviewservice.backend.service;

import es.poc.catalogviewservice.backend.domain.CatalogView;
import es.poc.catalogviewservice.backend.repository.CatalogViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class CatalogViewServiceImpl implements  CatalogViewService {

  private CatalogViewRepository catalogViewRepository;

  @Autowired
  public CatalogViewServiceImpl(CatalogViewRepository catalogViewRepository, MongoTemplate mongoTemplate) {
    this.catalogViewRepository = catalogViewRepository;
  }

  @Override
  public CatalogView saveEntry(CatalogView view) {
    return catalogViewRepository.save(view);

  }

  @Override
  public void deleteEntry(String entryId) {
    catalogViewRepository.delete(entryId);
  }


}

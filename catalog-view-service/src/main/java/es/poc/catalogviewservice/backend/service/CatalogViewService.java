package es.poc.catalogviewservice.backend.service;

import es.poc.catalogviewservice.backend.domain.CatalogView;

public interface CatalogViewService {


  CatalogView saveEntry(CatalogView view);
  void deleteEntry(String entryId);



}

package es.poc.catalogservice.web;


import es.poc.common.model.CatalogEntryInfo;
import es.poc.common.model.Money;

public class CreateCatalogEntryRequest {
  private CatalogEntryInfo catalogEntryInfo;

  public CatalogEntryInfo getInfo() {
    return catalogEntryInfo;
  }

  public void setInfo(CatalogEntryInfo info) {
    this.catalogEntryInfo = info;
  }

  public CreateCatalogEntryRequest() {
  }

  public CreateCatalogEntryRequest(CatalogEntryInfo info) {
    this.catalogEntryInfo = info;
  }

}

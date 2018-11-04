package es.poc.catalogservice.web;

import es.poc.common.model.CatalogEntryInfo;

public class GetCatalogEntryResponse {
  private String entryId;
  private CatalogEntryInfo entryInfo;

  public GetCatalogEntryResponse() {
  }

  public GetCatalogEntryResponse(String entryId, CatalogEntryInfo entryInfo) {
    this.entryId = entryId;
    this.entryInfo = entryInfo;
  }

  public String getEntryId() {
    return entryId;
  }

  public CatalogEntryInfo getInfo() {
    return entryInfo;
  }
}

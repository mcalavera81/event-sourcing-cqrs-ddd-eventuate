package es.poc.catalogservice.web;


public class CreateCatalogEntryResponse {
  private String entryId;

  public CreateCatalogEntryResponse() {
  }

  public CreateCatalogEntryResponse(String entryId) {
    this.entryId = entryId;

  }

  public String getEntryId() {
    return entryId;
  }
}

package es.poc.catalogservice.backend.command;


import es.poc.common.model.CatalogEntryInfo;

public class CreateCatalogEntryCommand implements CatalogCommand {

  private CatalogEntryInfo catalogEntryInfo;

  public CreateCatalogEntryCommand(CatalogEntryInfo info) {
    this.catalogEntryInfo= info;
  }

  public CatalogEntryInfo getInfo() {
    return catalogEntryInfo;
  }

}

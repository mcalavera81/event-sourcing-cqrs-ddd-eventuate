package es.poc.catalogservice.backend.command;


import es.poc.common.model.CatalogEntryInfo;

public class UpdateCatalogEntryCommand implements CatalogCommand {

  private CatalogEntryInfo catalogEntryInfo;

  public UpdateCatalogEntryCommand(CatalogEntryInfo info) {
    this.catalogEntryInfo = info;
  }

  public CatalogEntryInfo getInfo() {
    return catalogEntryInfo;
  }

}

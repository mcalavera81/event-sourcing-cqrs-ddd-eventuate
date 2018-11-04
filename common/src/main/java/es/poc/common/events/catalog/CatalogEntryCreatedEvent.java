package es.poc.common.events.catalog;

import es.poc.common.model.CatalogEntryInfo;
import es.poc.common.model.Money;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class CatalogEntryCreatedEvent implements CatalogEvent {
  private CatalogEntryInfo catalogEntryInfo;
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  public CatalogEntryCreatedEvent() {
  }

  public CatalogEntryCreatedEvent(CatalogEntryInfo catalogEntryInfo) {
    this.catalogEntryInfo = catalogEntryInfo;
  }

  public void setInfo(CatalogEntryInfo catalogEntryInfo) {
    this.catalogEntryInfo = catalogEntryInfo;
  }

  public CatalogEntryInfo getInfo() {
    return catalogEntryInfo;
  }

}

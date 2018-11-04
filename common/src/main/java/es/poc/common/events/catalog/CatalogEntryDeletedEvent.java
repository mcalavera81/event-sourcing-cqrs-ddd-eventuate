package es.poc.common.events.catalog;


import es.poc.common.model.CatalogEntryInfo;

import java.util.Objects;

public class CatalogEntryDeletedEvent implements CatalogEvent {

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
   return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash(1);
  }

}

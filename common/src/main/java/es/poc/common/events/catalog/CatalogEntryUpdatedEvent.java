package es.poc.common.events.catalog;


import es.poc.common.model.CatalogEntryInfo;

import java.util.Objects;

public class CatalogEntryUpdatedEvent implements CatalogEvent {

    private CatalogEntryInfo catalogEntryInfo;

    private CatalogEntryUpdatedEvent() {
    }

    public CatalogEntryUpdatedEvent(CatalogEntryInfo info) {
        this.catalogEntryInfo = info;
    }

    public void setInfo(CatalogEntryInfo catalogEntryInfo) {
        this.catalogEntryInfo = catalogEntryInfo;
    }

    public CatalogEntryInfo getInfo() {
        return catalogEntryInfo;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatalogEntryUpdatedEvent that = (CatalogEntryUpdatedEvent) o;
        return Objects.equals(catalogEntryInfo, that.catalogEntryInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(catalogEntryInfo);
    }
}
package es.poc.catalogservice.backend.service;

import es.poc.catalogservice.backend.domain.CatalogEntry;
import es.poc.common.model.CatalogEntryInfo;
import io.eventuate.EntityWithIdAndVersion;
import io.eventuate.EntityWithMetadata;

public interface CatalogService {

  EntityWithIdAndVersion<CatalogEntry> createEntry(CatalogEntryInfo entryInfo);

  EntityWithMetadata<CatalogEntry> findById(String entryId);

  EntityWithIdAndVersion<CatalogEntry> updateEntry(String id,
                                                   CatalogEntryInfo info);

  EntityWithIdAndVersion<CatalogEntry> deleteEntry(String entryId);

}

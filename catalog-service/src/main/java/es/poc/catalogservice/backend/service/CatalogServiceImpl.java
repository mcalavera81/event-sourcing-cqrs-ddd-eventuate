package es.poc.catalogservice.backend.service;

import es.poc.catalogservice.backend.command.CatalogCommand;
import es.poc.catalogservice.backend.command.CreateCatalogEntryCommand;
import es.poc.catalogservice.backend.command.DeleteCatalogEntryCommand;
import es.poc.catalogservice.backend.command.UpdateCatalogEntryCommand;
import es.poc.catalogservice.backend.domain.CatalogEntry;
import es.poc.common.model.CatalogEntryInfo;
import io.eventuate.EntityWithIdAndVersion;
import io.eventuate.EntityWithMetadata;
import io.eventuate.sync.AggregateRepository;

import java.util.concurrent.CompletableFuture;

public class CatalogServiceImpl implements CatalogService {

  private final AggregateRepository<CatalogEntry, CatalogCommand> aggregateRepo;

  public CatalogServiceImpl(AggregateRepository<CatalogEntry, CatalogCommand> repo) {
    this.aggregateRepo = repo;
  }

  @Override
  public EntityWithIdAndVersion<CatalogEntry> createEntry
    (CatalogEntryInfo entryInfo) {
    return aggregateRepo.save(new CreateCatalogEntryCommand(entryInfo));
  }

  @Override
  public EntityWithMetadata<CatalogEntry> findById(String entryId) {
    return aggregateRepo.find(entryId);
  }

  @Override
  public EntityWithIdAndVersion<CatalogEntry> updateEntry(String id,
                                                          CatalogEntryInfo info) {
    return aggregateRepo.update(id, new UpdateCatalogEntryCommand(info));
  }

  @Override
  public EntityWithIdAndVersion<CatalogEntry> deleteEntry(String entryId) {
    return aggregateRepo.update(entryId, new DeleteCatalogEntryCommand());
  }
}

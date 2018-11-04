package es.poc.catalogservice.backend.domain;

import es.poc.catalogservice.backend.command.CatalogCommand;
import es.poc.catalogservice.backend.command.CreateCatalogEntryCommand;
import es.poc.catalogservice.backend.command.DeleteCatalogEntryCommand;
import es.poc.catalogservice.backend.command.UpdateCatalogEntryCommand;
import es.poc.common.events.catalog.CatalogEntryCreatedEvent;
import es.poc.common.events.catalog.CatalogEntryDeletedEvent;
import es.poc.common.events.catalog.CatalogEntryUpdatedEvent;
import es.poc.common.model.CatalogEntryInfo;
import io.eventuate.Event;
import io.eventuate.EventUtil;
import io.eventuate.ReflectiveMutableCommandProcessingAggregate;

import java.util.Collections;
import java.util.List;

public class CatalogEntry extends
  ReflectiveMutableCommandProcessingAggregate<CatalogEntry, CatalogCommand> {


  private CatalogEntryInfo catalogEntry;


  private boolean deleted;

  public boolean markedDeleted() {
    return deleted;
  }

  /********************* Command Processing ****************************/
  public List<Event> process(UpdateCatalogEntryCommand cmd) {
    if (this.deleted) {
      return Collections.emptyList();
    }
    return EventUtil.events(new CatalogEntryUpdatedEvent(cmd.getInfo()));
  }

  public List<Event> process(DeleteCatalogEntryCommand cmd) {
    if (this.deleted) {
      return Collections.emptyList();
    }
    return EventUtil.events(new CatalogEntryDeletedEvent());
  }

  public List<Event> process(CreateCatalogEntryCommand cmd) {
    if (this.deleted) {
      return Collections.emptyList();
    }
    return EventUtil.events(new CatalogEntryCreatedEvent(cmd.getInfo()));
  }


  /********************* Event Rehydration ****************************/

  public void apply(CatalogEntryCreatedEvent event) {
    this.catalogEntry = event.getInfo();
  }

  public void apply(CatalogEntryUpdatedEvent event) {
    this.catalogEntry = event.getInfo();
  }

  public void apply(CatalogEntryDeletedEvent event) {
    this.deleted = true;
  }

  public CatalogEntryInfo getInfo() {
    return catalogEntry;
  }
}

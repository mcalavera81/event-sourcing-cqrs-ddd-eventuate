package es.poc.catalogviewservice.backend.handlers;

import es.poc.catalogviewservice.backend.domain.CatalogView;
import es.poc.catalogviewservice.backend.service.CatalogViewService;
import es.poc.catalogviewservice.backend.service.CatalogViewServiceImpl;
import es.poc.common.events.catalog.CatalogEntryCreatedEvent;
import es.poc.common.events.catalog.CatalogEntryDeletedEvent;
import es.poc.common.events.catalog.CatalogEntryUpdatedEvent;
import io.eventuate.DispatchedEvent;
import io.eventuate.EventHandlerMethod;
import io.eventuate.EventSubscriber;
import org.springframework.beans.factory.annotation.Autowired;

@EventSubscriber(id = "catalogQuerySideEventHandlers")
public class CatalogViewEventSubscriber {


  private CatalogViewService catalogViewService;


  @Autowired
  public CatalogViewEventSubscriber(CatalogViewService catalogViewService) {
    this.catalogViewService = catalogViewService;
  }

  @EventHandlerMethod
  public void create(DispatchedEvent<CatalogEntryCreatedEvent> de) {
    CatalogView view = new CatalogView(
      de.getEntityId(),
      de.getEvent().getInfo());

    catalogViewService.saveEntry(view);
  }

  @EventHandlerMethod
  public void update(DispatchedEvent<CatalogEntryUpdatedEvent> de) {

    CatalogView view = new CatalogView(
      de.getEntityId(),
      de.getEvent().getInfo());

    catalogViewService.saveEntry(view);
  }

  @EventHandlerMethod
  public void delete(DispatchedEvent<CatalogEntryDeletedEvent> de) {
    catalogViewService.deleteEntry(de.getEntityId());
  }

}

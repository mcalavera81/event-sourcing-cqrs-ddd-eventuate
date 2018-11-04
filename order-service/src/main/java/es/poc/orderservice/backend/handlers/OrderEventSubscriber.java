package es.poc.orderservice.backend.handlers;

import es.poc.common.events.catalog.CatalogEntryCreatedEvent;
import es.poc.common.events.catalog.CatalogEntryDeletedEvent;
import es.poc.common.events.catalog.CatalogEntryUpdatedEvent;
import es.poc.common.model.CatalogEntryInfo;
import es.poc.orderservice.backend.domain.Product;
import es.poc.orderservice.backend.service.OrderService;
import io.eventuate.DispatchedEvent;
import io.eventuate.EventHandlerMethod;
import io.eventuate.EventSubscriber;
import org.springframework.beans.factory.annotation.Autowired;


@EventSubscriber(id = "orderComandSideEventHandlers")
public class OrderEventSubscriber {


  private OrderService orderService;


  @Autowired
  public OrderEventSubscriber(OrderService catalogViewService) {
    this.orderService = catalogViewService;
  }

  @EventHandlerMethod
  public void createProduct(DispatchedEvent<CatalogEntryCreatedEvent> de) {
    final CatalogEntryInfo catalogEntry = de.getEvent().getInfo();
    Product view = new Product(
      de.getEntityId(),
      catalogEntry.getPrice());

    orderService.saveProduct(view);
  }

  @EventHandlerMethod
  public void updateProduct(DispatchedEvent<CatalogEntryUpdatedEvent> de) {
    final CatalogEntryInfo catalogEntry = de.getEvent().getInfo();
    Product view = new Product(
      de.getEntityId(),
      catalogEntry.getPrice());

    orderService.saveProduct(view);
  }

  @EventHandlerMethod
  public void deleteProduct(DispatchedEvent<CatalogEntryDeletedEvent> de) {
    orderService.deleteProduct(de.getEntityId());
  }
}

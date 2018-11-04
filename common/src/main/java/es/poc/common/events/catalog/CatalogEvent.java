package es.poc.common.events.catalog;

import io.eventuate.Event;
import io.eventuate.EventEntity;

@EventEntity(entity = "es.poc.catalogservice.backend.domain.CatalogEntry")
public interface CatalogEvent extends Event {
}

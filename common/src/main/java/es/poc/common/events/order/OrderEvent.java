package es.poc.common.events.order;

import io.eventuate.Event;
import io.eventuate.EventEntity;

@EventEntity(entity = "es.poc.orderservice.backend.domain.Order")
public interface OrderEvent extends Event {
}

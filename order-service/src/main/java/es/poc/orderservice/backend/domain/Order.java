package es.poc.orderservice.backend.domain;

import es.poc.common.events.order.OrderCreatedEvent;
import es.poc.common.events.order.OrderDeletedEvent;
import es.poc.common.events.order.OrderUpdatedEvent;
import es.poc.common.model.Money;
import es.poc.common.model.OrderLineItem;
import es.poc.common.model.UserInfo;
import es.poc.orderservice.backend.command.CreateOrderCommand;
import es.poc.orderservice.backend.command.DeleteOrderCommand;
import es.poc.orderservice.backend.command.OrderCommand;
import es.poc.orderservice.backend.command.UpdateOrderCommand;
import io.eventuate.Event;
import io.eventuate.EventUtil;
import io.eventuate.ReflectiveMutableCommandProcessingAggregate;

import java.util.Collections;
import java.util.List;

import static java.lang.String.format;

public class Order extends
  ReflectiveMutableCommandProcessingAggregate<Order, OrderCommand> {


  private UserInfo userInfo;
  private List<OrderLineItem> items;

  private boolean deleted;

  public boolean markedDeleted() {
    return deleted;
  }

  /********************* Command Processing ****************************/

  public List<Event> process(CreateOrderCommand cmd) {
    if (this.deleted) {
      return Collections.emptyList();
    }

    validateItems(cmd.getItems());
    return EventUtil.events(
      new OrderCreatedEvent(
        cmd.getUserInfo(),
        cmd.getItems()));
  }



  public List<Event> process(UpdateOrderCommand cmd) {
    if (this.deleted) {
      return Collections.emptyList();
    }

    validateItems(cmd.getItems());
    return EventUtil.events(
      new OrderUpdatedEvent(
        cmd.getUserInfo(),
        cmd.getItems()));
  }

  public List<Event> process(DeleteOrderCommand cmd) {
    if (this.deleted) {
      return Collections.emptyList();
    }
    return EventUtil.events(new OrderDeletedEvent());
  }

  /********************* Event Rehydration ****************************/

  public void apply(OrderCreatedEvent event) {
    this.userInfo = event.getUserInfo();
    this.items = event.getItems();
  }

  public void apply(OrderUpdatedEvent event) {
    this.userInfo = event.getUserInfo();
    this.items = event.getItems();
  }

  public void apply(OrderDeletedEvent event) {
    this.deleted = true;
  }


  public UserInfo getUserInfo() {
    return userInfo;
  }

  public List<OrderLineItem> getItems() {
    return items;
  }

  public Money getTotal(){
    return items
      .stream()
      .map(li -> li.getPrice().multiply(li.getQuantity())).reduce(Money::add).orElse(Money.ZERO);
  }

  private void validateItems(List<OrderLineItem> items) {
    items.forEach(it-> {
      if(it.getPrice()==null)
        throw new IllegalStateException(format("item %s is missing price",it.getId()));
    });
  }
}

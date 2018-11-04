package es.poc.orderservice.backend;

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
import es.poc.orderservice.backend.domain.Order;
import io.eventuate.Aggregates;
import io.eventuate.DefaultMissingApplyEventMethodStrategy;
import io.eventuate.Event;
import io.eventuate.EventuateCommandProcessingFailedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static io.eventuate.EventUtil.events;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class OrderTest {

  private Order order;
  private List<Event> events;

  OrderLineItem item1;
  OrderLineItem item2;
  private UserInfo userInfo;
  List<OrderLineItem> items;

  @Before
  public void createOrder() {
    order = new Order();
    userInfo = new UserInfo("name", "surname", "email@domain.com");

    item1 =new OrderLineItem("id1", 1);
    item2=new OrderLineItem("id2", 2);

    items = new LinkedList<>();
    items.add(item1);
    items.add(item2);

  }

  @Test
  public void shouldFailCreationOnPriceMissing() {

    try{
      process(new CreateOrderCommand(userInfo, items));
      Assert.fail("Should fail");
    }catch (EventuateCommandProcessingFailedException ex){
    }

  }



  @Test
  public void shouldCreateOrder() {


    item1.setPrice(new Money(1));
    item2.setPrice(new Money(2));
    process(new CreateOrderCommand(userInfo, items));

    assertEventEquals(new OrderCreatedEvent(userInfo, items));

    applyEventsToMutableAggregate();

    assertEquals(userInfo, order.getUserInfo());
    assertEquals(items, order.getItems());
    assertEquals(new Money(5), order.getTotal());

  }

  @Test
  public void shouldUpdateOrder() {

    initializeOrder();

    item1.setPrice(new Money(2));
    item2.setPrice(new Money(3));

    process(new UpdateOrderCommand(userInfo, items));

    assertEventEquals(new OrderUpdatedEvent(userInfo, items));

    applyEventsToMutableAggregate();

    assertEquals(userInfo, order.getUserInfo());
    assertEquals(items, order.getItems());
    assertEquals(new Money(8), order.getTotal());

  }

  @Test
  public void shouldDeleteOrder() {

    initializeOrder();

    process(new DeleteOrderCommand());

    assertEventEquals(new OrderDeletedEvent());

    assertFalse(order.markedDeleted());
    applyEventsToMutableAggregate();

    assertTrue(order.markedDeleted());

  }

  private void assertEventEquals(Event expectedEvent) {
    assertEquals(events(expectedEvent), events);
  }

  private <T extends OrderCommand> void process(T command) {
    events = order.processCommand(command);
  }

  private void applyEventsToMutableAggregate() {
    Aggregates.applyEventsToMutableAggregate(order, events, DefaultMissingApplyEventMethodStrategy.INSTANCE);
  }

  private void initializeOrder() {

    item1.setPrice(new Money(1));
    item2.setPrice(new Money(2));

    process(new CreateOrderCommand(userInfo,items));
    applyEventsToMutableAggregate();
  }

}

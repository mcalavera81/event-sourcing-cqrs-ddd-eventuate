package es.poc.orderservice.backend;

import es.poc.common.model.Money;
import es.poc.common.model.OrderLineItem;
import es.poc.common.model.UserInfo;
import es.poc.orderservice.OrderServiceInProcessComponentTestConfiguration;
import es.poc.orderservice.backend.command.OrderCommand;
import es.poc.orderservice.backend.domain.Order;
import es.poc.orderservice.backend.domain.Product;
import es.poc.orderservice.backend.repository.ProductRepository;
import es.poc.orderservice.backend.service.OrderService;
import io.eventuate.EntityWithIdAndVersion;
import io.eventuate.sync.AggregateRepository;
import lombok.experimental.var;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static es.poc.orderservice.TestUtils.newUserInfo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(classes =
  OrderServiceInProcessComponentTestConfiguration.class,
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderServiceTest {

  @Autowired
  private OrderService orderService;

  @Autowired
  private AggregateRepository<Order, OrderCommand> orderRepo;

  @Autowired
  private ProductRepository productRepo;

  @Autowired
  private MongoTemplate mongoTemplate;

  private OrderLineItem item1, item2;
  private UserInfo userInfo;
  private List<OrderLineItem> items;


  @Before
  public void setUp() {

    userInfo = newUserInfo();

    item1 = new OrderLineItem("id1", 2);
    item2 = new OrderLineItem("id2", 3);

    items = Arrays.asList(item1, item2);

    orderService.saveProduct(new Product("id1", Money.of(4)));
    orderService.saveProduct(new Product("id2", Money.of(3)));
    orderService.saveProduct(new Product("id3", Money.of(5)));
  }


  @Test
  public void shouldCreateUpdateAndDeleteOrder() {


    val orderId = createAndAssertOrderCreation();
    updateAndAssertOrderModified(orderId);
    deleteAndAssertOrderMarkedAsDeleted(orderId);

  }

  private void deleteAndAssertOrderMarkedAsDeleted(String orderId) {
    orderService.deleteOrder(orderId);
    val orderDb = orderService.findOrderById(orderId);
    val deletedOrder = orderDb.getEntity();
    assertTrue(deletedOrder.markedDeleted());
  }

  private void updateAndAssertOrderModified(String orderId) {
    item1 = new OrderLineItem("id1", 1);
    item2 = new OrderLineItem("id3", 4);

    orderService.updateOrder(orderId, userInfo, Arrays.asList(item1, item2));
    val orderDb = orderService.findOrderById(orderId);
    assertEquals(Money.of(24), orderDb.getEntity().getTotal());
  }

  private String createAndAssertOrderCreation() {
    val order = orderService.createOrder(userInfo, items);

    final String orderId = order.getEntityId();
    Assert.assertNotNull(orderId);

    val orderDb = orderService.findOrderById(orderId);
    List<OrderLineItem> items = orderDb.getEntity().getItems();

    Assertions.assertThat(items)
      .hasSize(2)
      .extracting(OrderLineItem::getPrice)
      .containsExactly(Money.of(4),Money.of(3));
    assertEquals(new Money(17), orderDb.getEntity().getTotal());
    return orderId;
  }

}
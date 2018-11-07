package es.poc.orderservice.backend;

import es.poc.common.model.Money;
import es.poc.common.model.OrderLineItem;
import es.poc.common.model.UserInfo;
import es.poc.orderservice.OrderServiceInProcessComponentTestConfiguration;
import es.poc.orderservice.backend.command.CreateOrderCommand;
import es.poc.orderservice.backend.command.OrderCommand;
import es.poc.orderservice.backend.command.UpdateOrderCommand;
import es.poc.orderservice.backend.domain.Order;
import io.eventuate.EntityWithIdAndVersion;
import io.eventuate.EntityWithMetadata;
import io.eventuate.sync.AggregateRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static es.poc.orderservice.TestUtils.newUserInfo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= OrderServiceInProcessComponentTestConfiguration.class,
        webEnvironment= SpringBootTest.WebEnvironment.NONE)
public class OrderPersistenceTest {

  @Autowired
  private AggregateRepository<Order, OrderCommand> repo;

  OrderLineItem item1;
  OrderLineItem item2;
  private UserInfo userInfo;
  List<OrderLineItem> items;

  @Before
  public void createOrder() {
    userInfo = newUserInfo();

    item1 =new OrderLineItem("id1", 1);
    item1.setPrice(new Money(2));
    item2=new OrderLineItem("id2", 2);
    item2.setPrice(new Money(5));

    items = Arrays.asList(item1, item2);

  }



  @Test
  public void shouldCreateAndUpdateOrder() {

    EntityWithIdAndVersion<Order> saveResults= repo.save(new CreateOrderCommand(userInfo, items));

    final String entityId = saveResults.getEntityId();
    Assert.assertNotNull(entityId);

    EntityWithMetadata<Order> getResults =repo.find(entityId);
    Assert.assertEquals(new Money(12),getResults.getEntity().getTotal());


    item1.setPrice(new Money(20));
    repo.update(entityId,new UpdateOrderCommand(userInfo, items));

    getResults =repo.find(entityId);
    Assert.assertEquals(new Money(30),getResults.getEntity().getTotal());

  }
}

package es.poc.orderservice.web;

import com.jayway.restassured.filter.log.RequestLoggingFilter;
import com.jayway.restassured.filter.log.ResponseLoggingFilter;
import com.jayway.restassured.http.ContentType;
import es.poc.common.model.CatalogEntryInfo;
import es.poc.common.model.Money;
import es.poc.common.model.OrderLineItem;
import es.poc.common.model.UserInfo;
import es.poc.orderservice.OrderServiceInProcessComponentTestConfiguration;
import es.poc.orderservice.backend.command.CreateOrderCommand;
import es.poc.orderservice.backend.domain.Product;
import es.poc.orderservice.backend.repository.ProductRepository;
import es.poc.orderservice.backend.service.OrderService;
import es.poc.orderservice.web.order.CreateOrderRequest;
import es.poc.orderservice.web.order.LineItemView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

@RunWith(SpringRunner.class)
@SpringBootTest(classes =
  OrderServiceInProcessComponentTestConfiguration.class,
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderServiceInProcessComponentTest {

  @Value("${local.server.port}")
  private int port;

  private String baseUrl(String path) {
    return "http://localhost:" + port + path;
  }

  LineItemView item1, item2,itemZero;
  private UserInfo userInfo;
  List<LineItemView> items;

  @Autowired
  private ProductRepository productRepo;

  @Before
  public void setUp() {

    userInfo = UserInfo.of("name", "surname", "email@domain.com");

    item1 = new LineItemView("id1", 2);
    item2 = new LineItemView("id2", 1);
    itemZero = new LineItemView("id1", 0);
    items = Arrays.asList(item1, item2);

    productRepo.deleteAll();

  }


  @Test
  public void shouldCreateOrder() {
    setupCatalogData();


    String orderUrl = baseUrl("/order");

    // @formatter:off
    String orderId =
    given().
        filter(new RequestLoggingFilter()).
      body(CreateOrderRequest.of(userInfo, items)).
            contentType("application/json").
    when().
           post(orderUrl).
    then().
           statusCode(HttpStatus.OK.value()).
           contentType(ContentType.JSON).
           body("total", equalTo(new Money(11).asString())).
    extract().
        path("orderId");
    // @formatter:on

    assertNotNull(orderId);

    // @formatter:off
  given().
  when().
     get(orderUrl+"/"+orderId).
  then().
     statusCode(HttpStatus.OK.value()).
     contentType(ContentType.JSON).
     body("orderId", equalTo(orderId)).
     body("items.quantity", hasItems(2,1)).
     body("items.itemId", hasItems("id1","id2")).
     body("items.price", hasItems(new Money(4).asString(),new Money(3).asString())).
     body("total", equalTo(new Money(11).asString()));
  // @formatter:on

  }

  private void setupCatalogData() {
    productRepo.save(new Product("id1", new Money(4)));
    productRepo.save(new Product("id2", new Money(3)));
  }
  @Test
  public void shouldNotCreateOrderInvalidProductId() {

    String orderUrl = baseUrl("/order");

    // @formatter:off
    given().
        filter(new RequestLoggingFilter()).
      body(CreateOrderRequest.of(userInfo, items)).
            contentType("application/json").
    when().
           post(orderUrl).
    then().
           statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    // @formatter:on
  }

  @Test
  public void shouldNotCreateOrderInvalidQuantity() {
    setupCatalogData();
    String orderUrl = baseUrl("/order");

    // @formatter:off
    given().
        filter(new RequestLoggingFilter()).
        filter(new ResponseLoggingFilter()).
      body(CreateOrderRequest.of(userInfo, Arrays.asList(itemZero))).
            contentType("application/json").
    when().
           post(orderUrl).
    then().
           statusCode(HttpStatus.BAD_REQUEST.value());
    // @formatter:on
  }
}


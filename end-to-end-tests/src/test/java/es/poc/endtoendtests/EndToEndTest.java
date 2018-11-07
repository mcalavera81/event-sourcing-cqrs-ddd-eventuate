package es.poc.endtoendtests;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.ObjectMapperConfig;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.http.ContentType;
import es.poc.catalogservice.web.CreateCatalogEntryRequest;
import es.poc.common.config.CommonJsonMapperInitializer;
import es.poc.common.model.Money;
import es.poc.common.model.UserInfo;
import es.poc.orderservice.web.order.CreateOrderRequest;
import es.poc.orderservice.web.order.LineItemView;
import io.eventuate.javaclient.commonimpl.JSonMapper;
import io.eventuate.util.test.async.Eventually;
import lombok.val;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static com.jayway.restassured.RestAssured.given;
import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EndToEndTest {

  private String host = System.getenv("DOCKER_HOST_IP");
  private Integer phoneId;

  private String baseUrl(int port, String path, String... pathElements) {
    StringBuilder sb = new StringBuilder("http://");
    sb.append(host).append(":").append(port).append("/");
    sb.append(path);

    for (String pe : pathElements) {
      sb.append("/");
      sb.append(pe);
    }
    String s = sb.toString();
    System.out.println("url=" + s);
    return s;
  }

  private int catalogCommandPort = 8081;
  private int catalogQueryPort = 8082;
  private int orderCommandPort = 8083;

  private String catalogCommandBaseUrl(String... pathElements) {
    return baseUrl(catalogCommandPort, "catalog", pathElements);
  }

  private String catalogQueryBaseUrl(String... pathElements) {
    return baseUrl(catalogQueryPort, "catalog", pathElements);
  }

  private String orderCommandBaseUrl(String... pathElements) {
    return baseUrl(orderCommandPort, "order", pathElements);
  }

  private String productCommandBaseUrl(String... pathElements) {
    return baseUrl(orderCommandPort, "product", pathElements);
  }

  @BeforeClass
  public static void initialize() {
    CommonJsonMapperInitializer.registerMoneyModule();

    RestAssured.config =
      RestAssuredConfig.config().objectMapperConfig(new ObjectMapperConfig().jackson2ObjectMapperFactory(
        (aClass, s) -> JSonMapper.objectMapper
      ));

  }


  @Test
  public void createPhoneOrder() {

    String name = "name";
    int price = 300;
    int quantity = 5;
    String phoneId = createPhoneRecord(name, price);
    verifyPhoneInCatalogViewService(phoneId,name, price);
    verifyPhoneReplicatedInOrderService(phoneId,price);
    String orderId = orderPhones(phoneId, quantity);
    verifyOrderInOrderService(orderId,quantity*price);

    name= "new_name";
    price = 500;
    quantity = 3;
    updatePhoneRecord(phoneId,name ,price);
    verifyPhoneInCatalogViewService(phoneId,name, price);
    verifyPhoneReplicatedInOrderService(phoneId, price);
    orderId = orderPhones(phoneId, quantity);
    verifyOrderInOrderService(orderId,quantity*price);

    deletePhoneRecord(phoneId);
    verifyPhoneDeletedInOrderService(phoneId);
    orderNonExistentPhone(phoneId);
    //Delete catalog entry
    //Create order with deleted entry (422)

  }



  private void deletePhoneRecord(String phoneId){
    // @formatter:off
      given().
      when().
        delete(catalogCommandBaseUrl(phoneId)).
      then().
        statusCode(HttpStatus.OK.value());
      // @formatter:on

    System.out.println(format("Deleted phone %s", phoneId));
  }


  private void updatePhoneRecord(String phoneId, String newName, int newPrice) {

    CreateCatalogEntryRequest request =
      CreateCatalogEntryRequest
        .builder()
        .image("image")
        .name(newName)
        .description("desc")
        .price(new Money(newPrice))
        .build();

    // @formatter:off
      given().
        body(request).
        contentType("application/json").
      when().
        put(catalogCommandBaseUrl(phoneId)).
      then().
        statusCode(HttpStatus.OK.value());
      // @formatter:on

    System.out.println(format("Updated phone %s", phoneId));

  }

  private void verifyOrderInOrderService(String orderId, int total) {
    // @formatter:off
    Eventually.eventually(format("verifyOrderInOrderService %s", orderId), () ->
      given().
      when().
        get(orderCommandBaseUrl(orderId)).
      then().
        body("orderId", equalTo(orderId)).
        body("total", equalTo(String.valueOf(total))).
        statusCode(HttpStatus.OK.value()));
    // @formatter:on

    System.out.println(format("Verified order %s", orderId));

  }

  private String orderPhones(String phoneId, int quantity) {

    val phoneItem = new LineItemView(phoneId, quantity);

    val request = CreateOrderRequest.of(
      UserInfo.of("name", "surname", "email@domain.com"),
      singletonList(phoneItem));

    // @formatter:off
    String orderId =
      given().
        body(request).
        contentType("application/json").
      when().
        post(orderCommandBaseUrl()).
      then().
        statusCode(HttpStatus.OK.value()).
        contentType(ContentType.JSON).
        extract().
        path("orderId");
    // @formatter:on

    System.out.println(
      format("%s Phone(s) %s Ordered %s", quantity, phoneId, orderId));

    return orderId;
  }


  private void orderNonExistentPhone(String phoneId) {
    val phoneItem = new LineItemView(phoneId, 1);

    val request = CreateOrderRequest.of(
      UserInfo.of("name", "surname", "email@domain.com"),
      singletonList(phoneItem));

    // @formatter:off
      given().
        body(request).
        contentType("application/json").
      when().
        post(orderCommandBaseUrl()).
      then().
        statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    // @formatter:on

    System.out.println(
      format("Order for non existent phone %s", phoneId));

  }

  private String createPhoneRecord(String name, int price) {
    CreateCatalogEntryRequest request =
      CreateCatalogEntryRequest
        .builder()
        .image("image")
        .name(name)
        .description("desc")
        .price(new Money(price))
        .build();

    String phoneId =
      given().
        body(request).
        contentType("application/json").
        when().
        post(catalogCommandBaseUrl()).
        then().
        statusCode(HttpStatus.OK.value()).
        extract().
        path("entryId");

    System.out.println(format("Created phone %s", phoneId));

    return phoneId;
  }

  private void verifyPhoneInCatalogViewService(String phoneId,
                                               String name,
                                               int price) {
    Eventually.eventually(format("verifyPhoneInCatalogViewService %s", phoneId), () ->
      given().
        when().
        get(catalogQueryBaseUrl(phoneId)).
        then().
        body("id", equalTo(phoneId)).
        body("name", equalTo(name)).
        body("price", equalTo(String.valueOf(price))).
        statusCode(HttpStatus.OK.value()));

    Eventually.eventually(format("verifyPhoneInCatalogViewService %s", phoneId), () ->
      given().
        when().
        get(catalogQueryBaseUrl()).
        then().
        body("id", hasItem(phoneId)).
        statusCode(HttpStatus.OK.value()));

    System.out.println(format("Verified phone view %s", phoneId));
  }
  private void verifyPhoneReplicatedInOrderService(String phoneId, int price) {

    // @formatter:off
    Eventually.eventually(format("verifyPhoneReplicatedInOrderService %s", phoneId), () ->
      given().
      when().
        get(productCommandBaseUrl(phoneId)).
        then().
        body("id", equalTo(phoneId)).
        body("price", equalTo(String.valueOf(price))).
        statusCode(HttpStatus.OK.value()));
    // @formatter:on
    System.out.println(format("Verified phone replicated %s", phoneId));
  }

  private void verifyPhoneDeletedInOrderService(String phoneId) {
    // @formatter:off
    Eventually.eventually(format("verifyPhoneDeletedInOrderService %s", phoneId), () ->
      given().
      when().
        get(productCommandBaseUrl(phoneId)).
      then().
        statusCode(HttpStatus.NOT_FOUND.value()));
    // @formatter:on
    System.out.println(format("Verified phone deleted %s", phoneId));
  }



}

package es.poc.catalogservice.web;

import com.jayway.restassured.http.ContentType;
import es.poc.catalogservice.CatalogServiceInProcessComponentTestConfiguration;
import es.poc.catalogservice.backend.command.CreateCatalogEntryCommand;
import es.poc.common.model.CatalogEntryInfo;
import es.poc.common.model.Money;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.restassured.RestAssured.given;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes =
  CatalogServiceInProcessComponentTestConfiguration.class,
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CatalogServiceInProcessComponentTest {

  @Value("${local.server.port}")
  private int port;

  private String baseUrl(String path) {
    return "http://localhost:" + port + path;
  }

  @Test
  public void shouldCreateOrder() {
    String catalogUrl = baseUrl("/catalog");

    CatalogEntryInfo info = new CatalogEntryInfo(
      "image", "name", "desc", new Money(300));

    // @formatter:off
    String entryId =
    given().
      //filter(new RequestLoggingFilter()).
      body(new CreateCatalogEntryCommand(info)).
            contentType("application/json").
    when().
           post(catalogUrl).
    then().
           statusCode(HttpStatus.OK.value()).
    extract().
        path("entryId");
    // @formatter:on

    assertNotNull(entryId);

  // @formatter:off
   given().
     //filter(new RequestLoggingFilter()).
  when().
     get(catalogUrl+"/"+entryId).
  then().
     statusCode(HttpStatus.OK.value()).
     contentType(ContentType.JSON).
     body("entryId", equalTo(entryId)).
     body("info.image", equalTo(info.getImage())).
     body("info.name", equalTo(info.getName())).
     body("info.description", equalTo(info.getDescription())).
     body("info.price.amount", equalTo(info.getPrice().getAmount().intValue()));
  // @formatter:on

  }
}

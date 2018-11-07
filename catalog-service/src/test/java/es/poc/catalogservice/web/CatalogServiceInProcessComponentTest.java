package es.poc.catalogservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.ObjectMapperConfig;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.filter.log.RequestLoggingFilter;
import com.jayway.restassured.filter.log.ResponseLoggingFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.mapper.factory.Jackson2ObjectMapperFactory;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import es.poc.catalogservice.CatalogServiceInProcessComponentTestConfiguration;
import es.poc.catalogservice.backend.command.CreateCatalogEntryCommand;
import es.poc.common.config.MoneyModule;
import es.poc.common.model.CatalogEntryInfo;
import es.poc.common.model.Money;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
  public void shouldCreateCatalogEntry() {
    String catalogUrl = baseUrl("/catalog");

    RestAssured.config = RestAssuredConfig.config().objectMapperConfig(new ObjectMapperConfig().jackson2ObjectMapperFactory(
      (clazz, string) -> {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new MoneyModule());
        return objectMapper;
      }
    ));

    final CreateCatalogEntryRequest request =
      CreateCatalogEntryRequest
      .builder()
      .image("image")
      .name("name")
      .description("desc")
      .price(new Money(300))
      .build();

    // @formatter:off
    String entryId =
    given().
      filter(new RequestLoggingFilter()).
      filter(new ResponseLoggingFilter()).
      body(request).
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
     filter(new RequestLoggingFilter()).
     filter(new ResponseLoggingFilter()).
  when().
     get(catalogUrl+"/"+entryId).
  then().
     statusCode(HttpStatus.OK.value()).
     contentType(ContentType.JSON).
     body("entryId", equalTo(entryId)).
     body("image", equalTo(request.getImage())).
     body("name", equalTo(request.getName())).
     body("description", equalTo(request.getDescription())).
     body("price", equalTo(request.getPrice().asString()));
  // @formatter:on

  }
}

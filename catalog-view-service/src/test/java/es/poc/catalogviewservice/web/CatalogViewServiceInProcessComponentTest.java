package es.poc.catalogviewservice.web;

import com.jayway.restassured.http.ContentType;
import es.poc.catalogviewservice.backend.domain.CatalogView;
import es.poc.catalogviewservice.backend.repository.CatalogViewRepository;
import es.poc.common.model.CatalogEntryInfo;
import es.poc.common.model.Money;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes =
  CatalogServiceInProcessComponentTestConfiguration.class,
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CatalogViewServiceInProcessComponentTest {

  @Value("${local.server.port}")
  private int port;

  private String baseUrl(String path) {
    return "http://localhost:" + port + path;
  }

  @Autowired
  private CatalogViewRepository repo;

  @Test
  public void shouldCreateCatalogEntry() {
    String catalogUrl = baseUrl("/catalog");
    String entryId = "id";

    CatalogEntryInfo info = new CatalogEntryInfo(
      "image", "name", "desc", new Money(300));

    CatalogView view = new CatalogView(entryId, info);
    repo.save(view);

    // @formatter:off
   given().
     //filter(new RequestLoggingFilter()).
  when().
     get(catalogUrl+"/"+entryId).
  then().
     statusCode(HttpStatus.OK.value()).
     contentType(ContentType.JSON).
     body("id", equalTo(entryId)).
     body("image", equalTo(info.getImage())).
     body("name", equalTo(info.getName())).
     body("description", equalTo(info.getDescription())).
     body("price", equalTo(info.getPrice().asString()));
  // @formatter:on

  }
}

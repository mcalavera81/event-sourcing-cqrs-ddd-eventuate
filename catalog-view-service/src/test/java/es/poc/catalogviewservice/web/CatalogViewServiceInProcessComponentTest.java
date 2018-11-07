package es.poc.catalogviewservice.web;

import com.jayway.restassured.http.ContentType;
import es.poc.catalogviewservice.backend.repository.CatalogViewRepository;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.restassured.RestAssured.given;
import static es.poc.catalogviewservice.TestUtils.newCatalogView;
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

    val view = newCatalogView("1",300);
    repo.save(view);

    // @formatter:off
  given().
     //filter(new RequestLoggingFilter()).
  when().
     get(catalogUrl+"/"+view.getId()).
  then().
     statusCode(HttpStatus.OK.value()).
     contentType(ContentType.JSON).
     body("id", equalTo(view.getId())).
     body("image", equalTo(view.getImage())).
     body("name", equalTo(view.getName())).
     body("description", equalTo(view.getDescription())).
     body("price", equalTo(view.getPrice().asString()));
  // @formatter:on

  }
}

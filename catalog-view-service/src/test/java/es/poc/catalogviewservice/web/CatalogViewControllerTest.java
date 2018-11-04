package es.poc.catalogviewservice.web;

import com.jayway.restassured.http.ContentType;
import es.poc.catalogviewservice.backend.domain.CatalogView;
import es.poc.catalogviewservice.backend.repository.CatalogViewRepository;
import es.poc.common.config.CommonConfiguration;
import es.poc.common.model.CatalogEntryInfo;
import es.poc.common.model.Money;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class CatalogViewControllerTest {

  private CatalogViewRepository catalogDao;
  private CatalogViewController catalogViewController;

  @Before
  public void setUp() {
    catalogDao = mock(CatalogViewRepository.class);
    catalogViewController = new CatalogViewController(catalogDao);
  }


  @Value("${local.server.port}")
  private int port;

  private String baseUrl(String path) {
    return "http://localhost:" + port + path;
  }


  @Test
  public void shouldGetCatalogEntry() {
    CatalogEntryInfo info= new CatalogEntryInfo(
      "ulr2",
      "name2",
      "desc2",
      new Money(600));

    CatalogView view = new CatalogView("id", info);
    when(catalogDao.findOne("id")).thenReturn(view);


    String catalogUrl = baseUrl("/catalog");

    // @formatter:off
    given().
      standaloneSetup(configureControllers(catalogViewController)).
    when().
           get(catalogUrl+"/"+"id").
    then().
           statusCode(HttpStatus.OK.value()).
           contentType(ContentType.JSON).
           body("id", equalTo("id")).
           body("image", equalTo(info.getImage())).
           body("name", equalTo(info.getName())).
           body("description", equalTo(info.getDescription())).
           body("price", equalTo(info.getPrice().asString()));
    // @formatter:on



  }

  @Test
  public void shouldGetAllEntries() {
    CatalogEntryInfo info1= new CatalogEntryInfo(
      "ulr1",
      "name1",
      "desc1",
      new Money(300));

    CatalogEntryInfo info2= new CatalogEntryInfo(
      "ulr2",
      "name2",
      "desc2",
      new Money(600));

    CatalogView view1 = new CatalogView("id1", info1);
    CatalogView view2 = new CatalogView("id2", info2);
    when(catalogDao.findAll()).thenReturn(asList(view1, view2));


    String catalogUrl = baseUrl("/catalog");

    // @formatter:off
    given().
      standaloneSetup(configureControllers(catalogViewController)).
    when().
           get(catalogUrl).
    then().
           statusCode(HttpStatus.OK.value()).
           contentType(ContentType.JSON).
           body("size()", is(2));
    // @formatter:on



  }


  private StandaloneMockMvcBuilder configureControllers(Object... controllers) {
    CommonConfiguration conf = new CommonConfiguration();
    return MockMvcBuilders.standaloneSetup(controllers).setMessageConverters(conf.myConverter());
  }


}
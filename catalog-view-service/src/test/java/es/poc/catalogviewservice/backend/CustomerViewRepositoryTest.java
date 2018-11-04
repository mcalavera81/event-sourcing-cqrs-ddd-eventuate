package es.poc.catalogviewservice.backend;

import es.poc.catalogviewservice.backend.domain.CatalogView;
import es.poc.catalogviewservice.backend.repository.CatalogViewRepository;
import es.poc.common.model.CatalogEntryInfo;
import es.poc.common.model.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertSame;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CatalogViewServiceTestConfiguration.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CustomerViewRepositoryTest {

  @Autowired
  private CatalogViewRepository catalogRepo;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Before
  public void init(){
    mongoTemplate.dropCollection(CatalogView.class);
  }


  @Test
  public void shouldCreateAndFindCatalogEntry() {

    CatalogEntryInfo info= new CatalogEntryInfo(
      "ulr",
      "name",
      "desc",
      new Money(300));

    CatalogView view = new CatalogView("id", info);
    catalogRepo.save(view);

    CatalogView viewDb = catalogRepo.findOne("id");
    assertEquals(view, viewDb);
  }
}

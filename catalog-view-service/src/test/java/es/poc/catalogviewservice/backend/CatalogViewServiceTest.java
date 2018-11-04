package es.poc.catalogviewservice.backend;

import es.poc.catalogviewservice.backend.domain.CatalogView;
import es.poc.catalogviewservice.backend.repository.CatalogViewRepository;
import es.poc.catalogviewservice.backend.service.CatalogViewService;
import es.poc.common.model.CatalogEntryInfo;
import es.poc.common.model.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CatalogViewServiceTestConfiguration.class,
  webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CatalogViewServiceTest {

  @Autowired
  private CatalogViewService catalogViewService;

  @Autowired
  private CatalogViewRepository catalogViewRepo;


  @Autowired
  private MongoTemplate mongoTemplate;

  @Before
  public void init(){
    mongoTemplate.dropCollection(CatalogView.class);
  }

  @Test
  public void shouldCreateCatalogEntries() {



    CatalogEntryInfo info1= new CatalogEntryInfo(
      "ulr",
      "name",
      "desc",
      new Money(300));

    CatalogEntryInfo info2= new CatalogEntryInfo(
      "ulr2",
      "name2",
      "desc2",
      new Money(600));

    CatalogView view1 = new CatalogView("id1", info1);
    CatalogView view2 = new CatalogView("id2", info2);

    catalogViewService.saveEntry(view1);
    catalogViewService.saveEntry(view2);

    final CatalogView viewDb = catalogViewRepo.findOne("id1");
    final CatalogView viewDb2 = catalogViewRepo.findOne("id2");

    assertEquals("desc",viewDb.getDescription() );
    assertEquals("desc2",viewDb2.getDescription() );

    assertEquals(2, catalogViewRepo.findAll().size());

  }


}
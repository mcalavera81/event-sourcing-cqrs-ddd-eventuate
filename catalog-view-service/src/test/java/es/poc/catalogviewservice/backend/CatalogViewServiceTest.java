package es.poc.catalogviewservice.backend;

import es.poc.catalogviewservice.backend.domain.CatalogView;
import es.poc.catalogviewservice.backend.repository.CatalogViewRepository;
import es.poc.catalogviewservice.backend.service.CatalogViewService;
import es.poc.common.model.CatalogEntryInfo;
import es.poc.common.model.Money;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static es.poc.catalogviewservice.TestUtils.newCatalogView;
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


    CatalogView view1 = newCatalogView("1",300);
    CatalogView view2 = newCatalogView("2",600);

    catalogViewService.saveEntry(view1);
    catalogViewService.saveEntry(view2);

    final CatalogView viewDb = catalogViewRepo.findOne(view1.getId());
    final CatalogView viewDb2 = catalogViewRepo.findOne(view2.getId());

    assertEquals(view1.getDescription(),viewDb.getDescription() );
    assertEquals(view2.getDescription(),viewDb2.getDescription() );

    assertEquals(2, catalogViewRepo.findAll().size());

  }


}
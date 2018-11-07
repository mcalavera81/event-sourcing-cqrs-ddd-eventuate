package es.poc.catalogviewservice;

import es.poc.catalogviewservice.backend.domain.CatalogView;
import es.poc.catalogviewservice.backend.repository.CatalogViewRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static es.poc.catalogviewservice.TestUtils.newCatalogView;
import static junit.framework.TestCase.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CatalogViewServiceTestConfiguration.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CatalogViewRepositoryIntegrationTest {

  @Autowired
  private CatalogViewRepository catalogRepo;

  @Test
  public void shouldCreateAndFindCatalogEntry() {

    CatalogView view = newCatalogView("1", 300);
    catalogRepo.save(view);

    CatalogView viewDb = catalogRepo.findOne(view.getId());
    assertEquals(view, viewDb);
  }
}

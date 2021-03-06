package es.poc.catalogservice.backend;

import es.poc.catalogservice.CatalogServiceInProcessComponentTestConfiguration;
import es.poc.catalogservice.backend.command.CatalogCommand;
import es.poc.catalogservice.backend.command.CreateCatalogEntryCommand;
import es.poc.catalogservice.backend.command.UpdateCatalogEntryCommand;
import es.poc.catalogservice.backend.domain.CatalogEntry;
import es.poc.common.model.CatalogEntryInfo;
import es.poc.common.model.Money;
import io.eventuate.EntityWithIdAndVersion;
import io.eventuate.EntityWithMetadata;
import io.eventuate.sync.AggregateRepository;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= CatalogServiceInProcessComponentTestConfiguration.class,
        webEnvironment= SpringBootTest.WebEnvironment.NONE)
public class CatalogPersistenceTest {

  @Autowired
  private AggregateRepository<CatalogEntry, CatalogCommand> repo;

  @Test
  public void shouldCreateAndUpdateCatalogEntry() {


    val initialPrice = Money.of(300);

    val info= CatalogEntryInfo.of("ulr", "name", "desc", initialPrice);
    val saveResults=repo.save(new CreateCatalogEntryCommand(info));

    final String entityId = saveResults.getEntityId();
    Assert.assertNotNull(entityId);

    EntityWithMetadata<CatalogEntry> getResults =repo.find(entityId);
    Assert.assertEquals(initialPrice,getResults.getEntity().getInfo().getPrice());

    val updatedPrice = Money.of(400);
    info.setPrice(updatedPrice);
    repo.update(entityId,new UpdateCatalogEntryCommand(info));

    getResults =repo.find(entityId);
    Assert.assertEquals(updatedPrice,getResults.getEntity().getInfo().getPrice());

  }
}

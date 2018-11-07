package es.poc.catalogservice.backend;

import es.poc.catalogservice.backend.command.CatalogCommand;
import es.poc.catalogservice.backend.command.CreateCatalogEntryCommand;
import es.poc.catalogservice.backend.command.DeleteCatalogEntryCommand;
import es.poc.catalogservice.backend.command.UpdateCatalogEntryCommand;
import es.poc.catalogservice.backend.domain.CatalogEntry;
import es.poc.common.events.catalog.CatalogEntryCreatedEvent;
import es.poc.common.events.catalog.CatalogEntryDeletedEvent;
import es.poc.common.events.catalog.CatalogEntryUpdatedEvent;
import es.poc.common.model.CatalogEntryInfo;
import es.poc.common.model.Money;
import io.eventuate.Aggregates;
import io.eventuate.DefaultMissingApplyEventMethodStrategy;
import io.eventuate.Event;
import lombok.val;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static io.eventuate.EventUtil.events;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CatalogTest {

  private CatalogEntry catalogEntry;
  private List<Event> events;

  @Before
  public void createEmptyCatalogEntry() {
    catalogEntry = new CatalogEntry();
  }


  @Test
  public void shouldCreate() {

    val info = CatalogEntryInfo.of("image", "name", "desc",Money.of(300));

    process(new CreateCatalogEntryCommand(info));

    assertEventEquals(new CatalogEntryCreatedEvent(info));

    applyEventsToMutableAggregate();

    assertEquals(info, catalogEntry.getInfo());
  }


  @Test
  public void shouldDelete() {
    initializeCatalogEntry();

    process(new DeleteCatalogEntryCommand());


    assertEventEquals(new CatalogEntryDeletedEvent());

    assertFalse(catalogEntry.markedDeleted());
    applyEventsToMutableAggregate();

    assertTrue(catalogEntry.markedDeleted());

  }

  @Test
  public void shouldUpdate() {
    initializeCatalogEntry();


    val info = CatalogEntryInfo.of("new_image", "name", "desc",Money.of(400));
    process(new UpdateCatalogEntryCommand(info));


    assertEventEquals(new CatalogEntryUpdatedEvent(info));

    applyEventsToMutableAggregate();

    assertEquals(info,catalogEntry.getInfo());

  }

  private <T extends CatalogCommand> void process(T command) {
    events = catalogEntry.processCommand(command);
  }

  private void applyEventsToMutableAggregate() {
    Aggregates.applyEventsToMutableAggregate(catalogEntry, events, DefaultMissingApplyEventMethodStrategy.INSTANCE);
  }

  private void initializeCatalogEntry() {

    val info = CatalogEntryInfo.of("image", "name", "desc",Money.of(300));
    process(new CreateCatalogEntryCommand(info));
    applyEventsToMutableAggregate();
  }

  private void assertEventEquals(Event expectedEvent) {
    assertEquals(events(expectedEvent), events);
  }


}

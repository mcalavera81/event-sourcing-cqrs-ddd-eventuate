package es.poc.catalogservice.backend;

import es.poc.catalogservice.backend.command.CatalogCommand;
import es.poc.catalogservice.backend.command.CreateCatalogEntryCommand;
import es.poc.catalogservice.backend.command.DeleteCatalogEntryCommand;
import es.poc.catalogservice.backend.command.UpdateCatalogEntryCommand;
import es.poc.catalogservice.backend.domain.CatalogEntry;
import es.poc.catalogservice.backend.service.CatalogService;
import es.poc.catalogservice.backend.service.CatalogServiceImpl;
import es.poc.common.model.CatalogEntryInfo;
import es.poc.common.model.Money;
import io.eventuate.EntityWithIdAndVersion;
import io.eventuate.sync.AggregateRepository;
import lombok.val;
import org.hamcrest.core.IsInstanceOf;
import org.hibernate.sql.Update;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class CatalogServiceTest {

  private CatalogService catalogService;
  private AggregateRepository<CatalogEntry, CatalogCommand> aggregateRepo;

  @Before
  public void setUp() {
    aggregateRepo = mock(AggregateRepository.class);
    catalogService = new CatalogServiceImpl(aggregateRepo);
  }

  @Test
  public void shouldCreateCatalogEntry() {


    val returned =new EntityWithIdAndVersion<CatalogEntry>(null, null);

    when(aggregateRepo.save(any(CreateCatalogEntryCommand.class))).thenReturn(returned);


    val info = CatalogEntryInfo.of("image", "name", "desc",Money.of(300));
    val result = catalogService.createEntry(info);

    assertSame(returned, result);

    val argument = ArgumentCaptor.forClass(CreateCatalogEntryCommand.class);

    verify(aggregateRepo).save(argument.capture());
    verifyNoMoreInteractions(aggregateRepo);

    CreateCatalogEntryCommand command = argument.getValue();

    assertEquals(info, command.getInfo());

  }

  @Test
  public void shouldDeleteCatalogEntry() {

    String entryId = "entryId";

    val returned = new EntityWithIdAndVersion<CatalogEntry>(null, null);


    when(aggregateRepo.update(eq(entryId),
      any(DeleteCatalogEntryCommand.class))).thenReturn(returned);

    EntityWithIdAndVersion<CatalogEntry> result = catalogService.deleteEntry(entryId);

    assertSame(returned, result);

    val argument = ArgumentCaptor.forClass(CatalogCommand.class);

    verify(aggregateRepo).update(eq(entryId), argument.capture());
    verifyNoMoreInteractions(aggregateRepo);

    CatalogCommand command = argument.getValue();

    assertThat(command, instanceOf(DeleteCatalogEntryCommand.class));

  }

  @Test
  public void shouldUpdateCatalogEntry() {

    String entryId = "entryId";

    val returned = new EntityWithIdAndVersion<CatalogEntry>(null, null);

    when(aggregateRepo.update(eq(entryId),
      any(UpdateCatalogEntryCommand.class))).thenReturn(returned);

    val info = CatalogEntryInfo.of("image", "name", "desc",Money.of(300));

    val result = catalogService.updateEntry(entryId, info);

    assertSame(returned, result);

    val argument = ArgumentCaptor.forClass(UpdateCatalogEntryCommand.class);

    verify(aggregateRepo).update(eq(entryId), argument.capture());
    verifyNoMoreInteractions(aggregateRepo);

    UpdateCatalogEntryCommand command = argument.getValue();

    assertEquals(info, command.getInfo());

  }
}
package es.poc.catalogservice.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.poc.catalogservice.backend.command.CatalogCommand;
import es.poc.catalogservice.backend.domain.CatalogEntry;
import es.poc.catalogservice.backend.handlers.CatalogEventSubscriber;
import es.poc.catalogservice.backend.service.CatalogService;
import es.poc.catalogservice.backend.service.CatalogServiceImpl;
import es.poc.common.config.MoneyModule;
import io.eventuate.javaclient.spring.EnableEventHandlers;
import io.eventuate.sync.AggregateRepository;
import io.eventuate.sync.EventuateAggregateStore;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;


@Configuration
//@EntityScan("net.chrisrichardson.eventstore.examples.todolist")
//@EnableJpaRepositories("net.chrisrichardson.eventstore.examples.todolist")
@EnableEventHandlers
public class CatalogBackendConfiguration {


  @Bean
  public CatalogEventSubscriber catalogEventSubscriber() {
    return new CatalogEventSubscriber();
  }

  @Bean
  public AggregateRepository<CatalogEntry, CatalogCommand> catalogRepository(EventuateAggregateStore eventStore) {
    return new AggregateRepository<>(CatalogEntry.class, eventStore);
  }

  @Bean
  public CatalogService catalogService(
    AggregateRepository<CatalogEntry,CatalogCommand> catalogRepository) {
    return new CatalogServiceImpl(catalogRepository);
  }

  @Bean
  public MappingJackson2HttpMessageConverter myConverter() {
    return new MappingJackson2HttpMessageConverter(new ObjectMapper().registerModule(new MoneyModule()));
  }

}

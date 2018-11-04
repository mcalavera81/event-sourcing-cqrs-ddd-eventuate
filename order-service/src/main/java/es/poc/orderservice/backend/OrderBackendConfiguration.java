package es.poc.orderservice.backend;

import com.sun.org.apache.xml.internal.resolver.CatalogEntry;
import es.poc.orderservice.backend.command.OrderCommand;
import es.poc.orderservice.backend.domain.Order;
import es.poc.orderservice.backend.handlers.OrderEventSubscriber;
import es.poc.orderservice.backend.repository.ProductRepository;
import es.poc.orderservice.backend.service.OrderService;
import es.poc.orderservice.backend.service.OrderServiceImpl;
import io.eventuate.javaclient.spring.EnableEventHandlers;
import io.eventuate.sync.AggregateRepository;
import io.eventuate.sync.EventuateAggregateStore;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;


@Configuration
//@EntityScan("net.chrisrichardson.eventstore.examples.todolist")
//@EnableJpaRepositories("net.chrisrichardson.eventstore.examples.todolist")
@EnableMongoRepositories
@EnableEventHandlers
public class OrderBackendConfiguration {


  @Bean
  public OrderEventSubscriber orderEventSubscriber(OrderService orderService) {
    return new OrderEventSubscriber(orderService);
  }

  @Bean
  public AggregateRepository<Order, OrderCommand> orderRepository(EventuateAggregateStore eventStore) {
    return new AggregateRepository<>(Order.class, eventStore);
  }

  @Bean
  public OrderService orderService(AggregateRepository<Order, OrderCommand> orderRepo,
                                   ProductRepository productRepo,
                                   MongoTemplate mongoTemplate) {
    return new OrderServiceImpl(orderRepo, productRepo, mongoTemplate);
  }

  @Bean
  public HttpMessageConverters customConverters() {
    HttpMessageConverter<?> additional = new MappingJackson2HttpMessageConverter();
    return new HttpMessageConverters(additional);
  }
}

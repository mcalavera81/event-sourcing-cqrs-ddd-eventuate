package es.poc.orderservice;

import es.poc.orderservice.web.OrderWebConfiguration;
import io.eventuate.javaclient.spring.jdbc.EmbeddedTestAggregateStoreConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({OrderWebConfiguration.class,
        EmbeddedTestAggregateStoreConfiguration.class
        })
@EnableAutoConfiguration
public class OrderServiceInProcessComponentTestConfiguration {


}

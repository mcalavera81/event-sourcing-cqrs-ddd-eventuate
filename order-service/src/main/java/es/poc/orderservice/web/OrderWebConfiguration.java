package es.poc.orderservice.web;

import es.poc.orderservice.backend.OrderBackendConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@Import({OrderBackendConfiguration.class})
@ComponentScan({ "es.poc.common", "es.poc.orderservice.web"})
public class OrderWebConfiguration extends WebMvcConfigurerAdapter {
}

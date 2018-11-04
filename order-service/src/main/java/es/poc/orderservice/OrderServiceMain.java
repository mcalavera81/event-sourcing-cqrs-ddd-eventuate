package es.poc.orderservice;

import es.poc.commonswagger.CommonSwaggerConfiguration;
import es.poc.orderservice.web.OrderWebConfiguration;
import io.eventuate.javaclient.driver.EventuateDriverConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({OrderWebConfiguration.class,
        EventuateDriverConfiguration.class,
        CommonSwaggerConfiguration.class})
@EnableAutoConfiguration
public class OrderServiceMain {

  public static void main(String[] args) {
    SpringApplication.run(OrderServiceMain.class, args);
  }

}
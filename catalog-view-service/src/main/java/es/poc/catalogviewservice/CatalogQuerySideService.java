package es.poc.catalogviewservice;

import es.poc.catalogviewservice.web.CatalogViewWebConfiguration;
import es.poc.commonswagger.CommonSwaggerConfiguration;
import io.eventuate.javaclient.driver.EventuateDriverConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CatalogViewWebConfiguration.class, EventuateDriverConfiguration.class,
  CommonSwaggerConfiguration.class})
@EnableAutoConfiguration
@ComponentScan
public class CatalogQuerySideService {

  public static void main(String[] args) {
    SpringApplication.run(CatalogQuerySideService.class, args);
  }
}

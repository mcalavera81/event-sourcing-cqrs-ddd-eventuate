package es.poc.catalogservice;

import es.poc.catalogservice.web.CatalogWebConfiguration;
import es.poc.commonswagger.CommonSwaggerConfiguration;
import io.eventuate.javaclient.driver.EventuateDriverConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CatalogWebConfiguration.class,
        EventuateDriverConfiguration.class,
        CommonSwaggerConfiguration.class})
@EnableAutoConfiguration
public class CatalogServiceMain {

  public static void main(String[] args) {
    SpringApplication.run(CatalogServiceMain.class, args);
  }

}
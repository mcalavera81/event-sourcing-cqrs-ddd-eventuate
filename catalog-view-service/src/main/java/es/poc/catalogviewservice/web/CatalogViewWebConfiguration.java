package es.poc.catalogviewservice.web;

import es.poc.catalogviewservice.backend.CatalogViewBackendConfiguration;
import es.poc.common.config.CommonConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
@ComponentScan
@Import({CommonConfiguration.class,CatalogViewBackendConfiguration.class})
public class CatalogViewWebConfiguration {

  /*@Bean
  public HttpMessageConverters customConverters() {
    HttpMessageConverter<?> additional = new MappingJackson2HttpMessageConverter();
    return new HttpMessageConverters(additional);
  }*/

}

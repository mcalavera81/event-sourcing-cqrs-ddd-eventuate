package es.poc.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class CommonConfiguration {


  @Bean
  public MappingJackson2HttpMessageConverter myConverter() {
    return new MappingJackson2HttpMessageConverter(new ObjectMapper().registerModule(new MoneyModule()));
  }

  /*@Bean
  public CommonJsonMapperInitializer commonJsonMapperInitializer() {
    return new CommonJsonMapperInitializer();

  }*/
}

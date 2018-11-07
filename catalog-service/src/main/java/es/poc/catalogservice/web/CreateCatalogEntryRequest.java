package es.poc.catalogservice.web;


import es.poc.common.model.CatalogEntryInfo;
import es.poc.common.model.Money;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCatalogEntryRequest {

  @NotBlank
  private String image;
  @NotBlank
  private String name;
  @NotBlank
  private String description;
  @NotNull
  private Money price;

}

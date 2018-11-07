package es.poc.common.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor(staticName= "of")
@NoArgsConstructor
public class CatalogEntryInfo{

  private String image;
  private String name;
  private String description;
  private Money price;

}

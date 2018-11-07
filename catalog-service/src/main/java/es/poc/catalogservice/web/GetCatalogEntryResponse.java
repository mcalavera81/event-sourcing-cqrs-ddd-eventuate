package es.poc.catalogservice.web;

import es.poc.common.model.CatalogEntryInfo;
import es.poc.common.model.Money;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetCatalogEntryResponse {
  @NonNull
  private String entryId;
  @NonNull
  private String image;
  @NonNull
  private String name;
  @NonNull
  private String description;
  @NonNull
  private Money price;

  public static class GetCatalogEntryResponseBuilder {
    private String image;
    private String name;
    private String description;
    private Money price;

    public GetCatalogEntryResponseBuilder entryInfo(CatalogEntryInfo info) {
      this.image = info.getImage();
      this.name = info.getName();
      this.description = info.getDescription();
      this.price = info.getPrice();
      return this;
    }
  }
}

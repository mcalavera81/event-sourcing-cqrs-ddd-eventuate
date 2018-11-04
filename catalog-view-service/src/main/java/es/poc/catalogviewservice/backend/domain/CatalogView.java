package es.poc.catalogviewservice.backend.domain;

import es.poc.common.model.CatalogEntryInfo;
import es.poc.common.model.Money;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public class CatalogView {

  public CatalogView(){}

  public CatalogView(String id, CatalogEntryInfo info) {
    this.id = id;
    this.image = info.getImage();
    this.name = info.getName();
    this.description = info.getDescription();
    this.price = info.getPrice();
  }

  @Id
  private String id;

  private String image;
  private String name;
  private String description;
  private Money price;

  public String getId() {
    return id;
  }

  public String getImage() {
    return image;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Money getPrice() {
    return price;
  }


  @Override
  public boolean equals(Object o) {

    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CatalogView that = (CatalogView) o;
    return Objects.equals(id, that.id) &&
      Objects.equals(image, that.image) &&
      Objects.equals(name, that.name) &&
      Objects.equals(description, that.description) &&
      Objects.equals(price, that.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, image, name, description, price);
  }
}

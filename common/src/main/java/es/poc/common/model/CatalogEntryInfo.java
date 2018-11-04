package es.poc.common.model;


import java.io.Serializable;
import java.util.Objects;

public class CatalogEntryInfo{

  private String image;
  private String name;
  private String description;
  private Money price;

  public CatalogEntryInfo(String image, String name, String description, Money price) {
    this.image = image;
    this.name = name;
    this.description = description;
    this.price = price;
  }

  public CatalogEntryInfo(){}

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Money getPrice() {
    return price;
  }

  public void setPrice(Money price) {
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CatalogEntryInfo info = (CatalogEntryInfo) o;
    return Objects.equals(image, info.image) &&
      Objects.equals(name, info.name) &&
      Objects.equals(description, info.description) &&
      Objects.equals(price, info.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(image, name, description, price);
  }
}

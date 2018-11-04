package es.poc.orderservice.backend.domain;

import es.poc.common.model.CatalogEntryInfo;
import es.poc.common.model.Money;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public class Product {

  @Id
  private String id;
  private Money price;

  public Product() {

  }

  public Product(String id, Money price) {
    this.id = id;
    this.price = price;
  }

  public String getId() {
    return id;
  }

  public Money getPrice() {
    return price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Product product = (Product) o;
    return Objects.equals(id, product.id) &&
      Objects.equals(price, product.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, price);
  }
}

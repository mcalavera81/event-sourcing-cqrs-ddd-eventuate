package es.poc.common.model;

import java.util.Objects;

public class OrderLineItem {

  public OrderLineItem() {
  }

  public OrderLineItem(String id, int quantity) {
    this.id = id;
    this.quantity = quantity;
  }



  private String id;
  private int quantity;
  private Money price;

  public String getId() {
    return id;
  }

  public int getQuantity() {
    return quantity;
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
    OrderLineItem that = (OrderLineItem) o;
    return quantity == that.quantity &&
      Objects.equals(id, that.id) &&
      Objects.equals(price, that.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, quantity, price);
  }
}
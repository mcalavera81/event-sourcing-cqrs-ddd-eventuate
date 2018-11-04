package es.poc.orderservice.web.order;

import es.poc.common.model.Money;

public class LineItemView {

  private String itemId;
  private int quantity;
  private Money price;


  private LineItemView() {
  }

  public LineItemView(String itemId, int quantity) {
    this.itemId = itemId;
    this.quantity = quantity;
  }

  public LineItemView(String itemId, int quantity, Money price) {
    this.itemId = itemId;
    this.quantity = quantity;
    this.price = price;
  }

  public String getItemId() {
    return itemId;
  }

  public int getQuantity() {
    return quantity;
  }

  public Money getPrice() {
    return price;
  }
}

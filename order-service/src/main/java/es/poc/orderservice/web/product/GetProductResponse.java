package es.poc.orderservice.web.product;

import es.poc.common.model.Money;

public class GetProductResponse {
  private String id;
  private Money price;

  public GetProductResponse() {
  }

  public GetProductResponse(String id, Money price) {
    this.id = id;
    this.price = price;
  }

  public String getId() {
    return id;
  }

  public Money getPrice() {
    return price;
  }
}

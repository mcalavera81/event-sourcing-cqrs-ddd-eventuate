package es.poc.orderservice.web.order;


import es.poc.common.model.Money;

public class CreateOrderResponse {
  private String orderId;
  private Money total;

  public CreateOrderResponse() {
  }

  public CreateOrderResponse(String orderId,
                             Money total) {
    this.orderId = orderId;
    this.total = total;

  }

  public String getOrderId() {
    return orderId;
  }

  public Money getTotal() { return total;}
}

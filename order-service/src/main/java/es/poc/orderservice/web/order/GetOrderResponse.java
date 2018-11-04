package es.poc.orderservice.web.order;

import es.poc.common.model.Money;
import es.poc.common.model.UserInfo;

import java.util.List;

public class GetOrderResponse {
  private String orderId;

  private UserInfo userInfo;
  private List<LineItemView> items;

  private Money total;


  public GetOrderResponse() {
  }

  public GetOrderResponse(String orderId,
                          UserInfo userInfo,
                          List<LineItemView> items,
                          Money total) {

    this.orderId = orderId;
    this.userInfo = userInfo;
    this.items = items;
    this.total = total;
  }

  public String getOrderId() {
    return orderId;
  }

  public UserInfo getUserInfo() {
    return userInfo;
  }

  public List<LineItemView> getItems() {
    return items;
  }

  public Money getTotal() {
    return total;
  }
}

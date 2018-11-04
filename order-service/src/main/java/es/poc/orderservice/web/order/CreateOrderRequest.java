package es.poc.orderservice.web.order;


import es.poc.common.model.UserInfo;

import java.util.List;

public class CreateOrderRequest {
  private UserInfo userInfo;
  private List<LineItemView> lineItems;

  public CreateOrderRequest() {
  }

  public CreateOrderRequest(
    UserInfo info,
    List<LineItemView> lineItems) {
    this.userInfo = info;
    this.lineItems = lineItems;
  }

  public UserInfo getUserInfo() {
    return userInfo;
  }

  public List<LineItemView> getLineItems() {
    return lineItems;
  }

  public void setUserInfo(UserInfo userInfo) {
    this.userInfo = userInfo;
  }

  public void setLineItems(List<LineItemView> lineItems) {
    this.lineItems = lineItems;
  }


}

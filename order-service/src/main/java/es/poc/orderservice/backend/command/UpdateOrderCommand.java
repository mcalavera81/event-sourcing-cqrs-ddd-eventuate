package es.poc.orderservice.backend.command;


import es.poc.common.model.UserInfo;
import es.poc.common.model.OrderLineItem;

import java.util.List;

public class UpdateOrderCommand implements OrderCommand {

  private UserInfo userInfo;
  private List<OrderLineItem> items;

  public UpdateOrderCommand(
    UserInfo info,
    List<OrderLineItem> items) {
    this.userInfo = info;
    this.items = items;
  }


  public UserInfo getUserInfo() {
    return userInfo;
  }

  public List<OrderLineItem> getItems() {
    return items;
  }

}

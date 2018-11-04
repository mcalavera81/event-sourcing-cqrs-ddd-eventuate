package es.poc.common.events.order;

import es.poc.common.model.OrderLineItem;
import es.poc.common.model.UserInfo;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.List;

public class OrderCreatedEvent implements OrderEvent {

  private UserInfo userInfo;
  private List<OrderLineItem> items;

  public OrderCreatedEvent() {
  }

  public OrderCreatedEvent(
    UserInfo info,
    List<OrderLineItem> items) {
    this.userInfo = info;
    this.items = items;
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }


  public UserInfo getUserInfo() {
    return userInfo;
  }

  public List<OrderLineItem> getItems() {
    return items;
  }
}

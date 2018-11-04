package es.poc.orderservice.backend.service;

import es.poc.common.model.UserInfo;
import es.poc.orderservice.backend.domain.Order;
import es.poc.common.model.OrderLineItem;
import es.poc.orderservice.backend.domain.Product;
import io.eventuate.EntityWithIdAndVersion;
import io.eventuate.EntityWithMetadata;

import java.util.List;

public interface OrderService {


  Product saveProduct(Product product);
  void deleteProduct(String productId);


  EntityWithMetadata<Order> findOrderById(String orderId);

  EntityWithIdAndVersion<Order> createOrder(UserInfo info,
                                            List<OrderLineItem> items);

  EntityWithIdAndVersion<Order> updateOrder(String orderId,
                                            UserInfo info,
                                            List<OrderLineItem> items);

  EntityWithIdAndVersion<Order> deleteOrder(String orderId);

}

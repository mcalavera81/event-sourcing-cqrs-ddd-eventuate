package es.poc.orderservice.backend.service;


import es.poc.common.model.OrderLineItem;
import es.poc.common.model.UserInfo;
import es.poc.orderservice.backend.command.CreateOrderCommand;
import es.poc.orderservice.backend.command.DeleteOrderCommand;
import es.poc.orderservice.backend.command.OrderCommand;
import es.poc.orderservice.backend.command.UpdateOrderCommand;
import es.poc.orderservice.backend.domain.Order;
import es.poc.orderservice.backend.domain.Product;
import es.poc.orderservice.backend.repository.ProductRepository;
import io.eventuate.EntityWithIdAndVersion;
import io.eventuate.EntityWithMetadata;
import io.eventuate.sync.AggregateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

import static java.lang.String.format;

public class OrderServiceImpl implements OrderService {

  private AggregateRepository<Order, OrderCommand> orderRepo;
  private ProductRepository productRepo;
  private MongoTemplate mongoTemplate;

  @Autowired
  public OrderServiceImpl(
    AggregateRepository<Order, OrderCommand> orderRepo,
    ProductRepository productRepo,
    MongoTemplate mongoTemplate) {

    this.orderRepo = orderRepo;
    this.productRepo = productRepo;
    this.mongoTemplate = mongoTemplate;
  }


  @Override
  public Product saveProduct(Product product) {
    return productRepo.save(product);
  }

  @Override
  public void deleteProduct(String productId) {
    productRepo.delete(productId);
  }


  @Override
  public EntityWithMetadata<Order> findOrderById(String orderId){
    return orderRepo.find(orderId);
  }

  @Override
  public EntityWithIdAndVersion<Order> createOrder(UserInfo userInfo,
                                                   List<OrderLineItem> items) {
    setPricing(items);
    return orderRepo.save(new CreateOrderCommand(userInfo,items));
  }

  @Override
  public EntityWithIdAndVersion<Order> updateOrder(String orderId,
                                                   UserInfo userInfo,
                                                   List<OrderLineItem> items) {

    setPricing(items);
    return orderRepo.update(orderId, new UpdateOrderCommand(userInfo,items));
  }

  @Override
  public EntityWithIdAndVersion<Order> deleteOrder(String orderId) {
    return orderRepo.update(orderId, new DeleteOrderCommand());
  }

  private void setPricing(List<OrderLineItem> items) {
    items.forEach(item->{
      final Product p = productRepo.findOne(item.getId());
      if(p==null){
        throw new IllegalStateException(format("Product Id not found: %s", item.getId()));
      }
      item.setPrice(p.getPrice());
    });
  }
}

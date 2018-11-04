package es.poc.orderservice.web.order;


import es.poc.orderservice.backend.domain.Order;
import es.poc.common.model.OrderLineItem;
import es.poc.orderservice.backend.service.OrderService;
import es.poc.orderservice.web.order.CreateOrderRequest;
import es.poc.orderservice.web.order.CreateOrderResponse;
import es.poc.orderservice.web.order.GetOrderResponse;
import es.poc.orderservice.web.order.LineItemView;
import io.eventuate.EntityNotFoundException;
import io.eventuate.EntityWithIdAndVersion;
import io.eventuate.EntityWithMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

  private OrderService orderService;

  @Autowired
  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @RequestMapping(method = RequestMethod.POST)
  public CreateOrderResponse createOrder(@RequestBody CreateOrderRequest createOrderRequest) {

    List<OrderLineItem> items = createOrderRequest
      .getLineItems()
      .stream()
      .map(li -> new OrderLineItem(li.getItemId(), li.getQuantity()))
      .collect(Collectors.toList());

    EntityWithIdAndVersion<Order> results =
      orderService.createOrder(
        createOrderRequest.getUserInfo(),
        items);


    return new CreateOrderResponse(
      results.getEntityId(),
      results.getAggregate().getTotal());
  }

  @RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
  public ResponseEntity<GetOrderResponse> getOrder(@PathVariable String orderId) {
    EntityWithMetadata<Order> entryWithMetadata;
    try {
      entryWithMetadata = orderService.findOrderById(orderId);
    } catch (EntityNotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    Order order = entryWithMetadata.getEntity();

    List<LineItemView> items = order
      .getItems()
      .stream()
      .map(li -> new LineItemView(li.getId(), li.getQuantity(), li.getPrice()))
      .collect(Collectors.toList());

    GetOrderResponse response =
      new GetOrderResponse(
        entryWithMetadata.getEntityIdAndVersion().getEntityId(),
        order.getUserInfo(),
        items,
        order.getTotal());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}

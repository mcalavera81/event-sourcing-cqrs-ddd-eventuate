package es.poc.orderservice.web.order;


import es.poc.orderservice.backend.domain.Order;
import es.poc.common.model.OrderLineItem;
import es.poc.orderservice.backend.service.OrderService;
import io.eventuate.EntityNotFoundException;
import io.eventuate.EntityWithIdAndVersion;
import io.eventuate.EntityWithMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
  public ResponseEntity<CreateOrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {

    EntityWithIdAndVersion<Order> results;
    try{
      results =
        orderService.createOrder(request.getUserInfo(),adaptOrderLineItems(request));
    }catch (IllegalStateException e){
      return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    return buildCreateOrderResponse(results);

  }

  @RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
  public ResponseEntity<GetOrderResponse> getOrder(@PathVariable String orderId) {
    EntityWithMetadata<Order> entryWithMetadata;
    try {
      entryWithMetadata = orderService.findOrderById(orderId);
    } catch (EntityNotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return buildGetOrderResponse(entryWithMetadata);
  }

  private ResponseEntity<CreateOrderResponse> buildCreateOrderResponse(EntityWithIdAndVersion<Order> results) {
    CreateOrderResponse response =new CreateOrderResponse(
      results.getEntityId(),
      results.getAggregate().getTotal());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  private List<OrderLineItem> adaptOrderLineItems(CreateOrderRequest createOrderRequest) {
    return createOrderRequest
      .getLineItems()
      .stream()
      .map(li -> new OrderLineItem(li.getItemId(), li.getQuantity()))
      .collect(Collectors.toList());
  }


  private ResponseEntity<GetOrderResponse> buildGetOrderResponse(EntityWithMetadata<Order> entryWithMetadata) {

    Order order = entryWithMetadata.getEntity();

    List<LineItemView> items = order
      .getItems()
      .stream()
      .map(li -> LineItemView.of(li.getId(), li.getQuantity(), li.getPrice()))
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

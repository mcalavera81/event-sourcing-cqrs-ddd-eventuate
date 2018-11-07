package es.poc.orderservice.web.order;

import es.poc.common.model.Money;
import lombok.*;

import javax.validation.constraints.Min;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
public class LineItemView {

  private String itemId;
  @Min(value = 1L, message = "The value must be positive")
  private int quantity;
  private Money price;


  public LineItemView(String itemId, int quantity) {
    this.itemId = itemId;
    this.quantity = quantity;
  }

}

package es.poc.orderservice.web.order;


import es.poc.common.model.UserInfo;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class CreateOrderRequest {
  @NotNull
  private UserInfo userInfo;
  @Valid
  private List<LineItemView> lineItems;

}

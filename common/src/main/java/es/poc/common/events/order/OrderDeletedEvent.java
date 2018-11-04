package es.poc.common.events.order;


import java.util.Objects;

public class OrderDeletedEvent implements OrderEvent {

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
   return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash(1);
  }

}

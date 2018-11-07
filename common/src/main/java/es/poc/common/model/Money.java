package es.poc.common.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

@EqualsAndHashCode
@ToString
@Getter
public class Money {

  public static final Money ZERO = Money.of(0);

  private BigDecimal amount;

  public Money() {
  }

  public Money(String s) {
    this.amount = new BigDecimal(s);
  }

  public static Money of(int i){
    return new Money(i);
  }

  public Money(int i) {
    this.amount = new BigDecimal(i);
  }

  public Money(BigDecimal amount) {
    this.amount = amount;
  }

  public String asString() {
    return amount.toPlainString();
  }


  public Money add(Money other) {
    return new Money(amount.add(other.amount));
  }

  public Money multiply(int x) {
    return new Money(amount.multiply(new BigDecimal(x)));
  }

}

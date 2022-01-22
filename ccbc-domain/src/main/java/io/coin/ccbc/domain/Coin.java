package io.coin.ccbc.domain;

import io.coin.ccbc.domain.exceptions.NotImplementedMethodException;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "coins")
public class Coin extends DomainEntity {

  @Embedded
  @AttributeOverride(name = "value", column = @Column(name = "address", nullable = false))
  private Address address;

  @Column(name = "symbol", nullable = false)
  private String symbol;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "decimals", nullable = false)
  private Integer decimals;

  @Column(name = "original_symbol", nullable = false)
  private String originalSymbol;

  @Column(name = "symbol_image_url", nullable = false)
  private String symbolImageUrl;

  @Column(name = "order_priority", nullable = false)
  private Integer orderPriority;

  @Embedded
  @AttributeOverride(name = "value", column = @Column(name = "price", nullable = false))
  private Price price;

  private Coin(
      Address address,
      String symbol,
      String name,
      Integer decimals,
      String originalSymbol,
      String symbolImageUrl,
      Price price
  ) {
    this.address = address;
    this.symbol = symbol;
    this.name = name;
    this.decimals = decimals;
    this.originalSymbol = originalSymbol;
    this.symbolImageUrl = symbolImageUrl;
    this.price = price;
    this.orderPriority = 0;
  }

  public static Coin withoutPrice(
      Address address,
      String symbol,
      String name,
      Integer decimals,
      String originalSymbol,
      String symbolImageUrl
  ) {
    return new Coin(address, symbol, name, decimals, originalSymbol, symbolImageUrl, Price.zero());
  }

  public static Coin withPrice(
      Address address,
      String symbol,
      String name,
      Integer decimals,
      String originalSymbol,
      String symbolImageUrl,
      Price price
  ) {
    return new Coin(address, symbol, name, decimals, originalSymbol, symbolImageUrl, price);
  }

  public void updatePrice(Price price) {
    this.price = price;
  }

  public boolean isToken() {
    throw new NotImplementedMethodException();
  }
}

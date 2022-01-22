package io.coin.ccbc.domain;

import java.time.LocalDateTime;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(
    name = "price_histories",
    indexes = {
        @Index(name = "price_histories_collected_at", columnList = "collected_at"),
        @Index(name = "price_histories_coin_id_collected_at", columnList = "coin_id,collected_at")
    }
)
public class PriceHistory extends History {

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "coin_id", foreignKey = @ForeignKey(name = "fk_price_histories_coin_id"), nullable = false)
  private Coin coin;

  @Embedded
  @AttributeOverride(name = "value", column = @Column(name = "price", nullable = false))
  private Price price;

  private PriceHistory(
      Coin coin,
      LocalDateTime collectedAt
  ) {
    super(collectedAt);
    this.coin = coin;
    this.price = coin.getPrice();
  }

  public static PriceHistory collected(
      Coin coin,
      LocalDateTime collectedAt
  ) {
    return new PriceHistory(coin, collectedAt);
  }
}

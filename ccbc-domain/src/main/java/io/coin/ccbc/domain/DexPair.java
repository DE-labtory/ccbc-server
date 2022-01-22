package io.coin.ccbc.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dex_pairs")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DexPair extends DomainEntity {

  @Embedded
  @AttributeOverride(name = "value", column = @Column(name = "address", nullable = false))
  private Address address;

  @Column(name = "decimals", nullable = false)
  private Integer decimals;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "coin_0_id", foreignKey = @ForeignKey(name = "fk_dex_pairs_coin_0_id"), nullable = false)
  private Coin coin0;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "coin_1_id", foreignKey = @ForeignKey(name = "fk_dex_pairs_coin_1_id"), nullable = false)
  private Coin coin1;

  @Column(name = "type", nullable = false)
  @Enumerated(EnumType.STRING)
  private Type type;

  public enum Type {
    KLAYSWAP, DEFINIX
  }

  public Coin getOppositeCoin(Coin coin) {
    if (coin.getAddress().equals(this.coin0.getAddress())) {
      return this.coin1;
    } else if (coin.getAddress().equals(this.coin1.getAddress())) {
      return this.coin0;
    } else {
      throw new IllegalArgumentException(
          String.format("dex pair [%s] does not contain coin [%s]", this.address,
              coin.getAddress()));
    }
  }

}

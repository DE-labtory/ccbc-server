package io.coin.ccbc.klayswap;


import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.DomainEntity;
import java.util.Arrays;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
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
@Table(name = "klayswap_pools")
public class KlayswapPool extends DomainEntity {

  @Embedded
  @AttributeOverride(name = "value", column = @Column(name = "address", nullable = false))
  private Address address;

  @Column(name = "decimals", nullable = false)
  private Integer decimals;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "coin_0_id", foreignKey = @ForeignKey(name = "fk_klayswap_pools_coin_0_id"), nullable = false)
  private Coin coin0;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "coin_1_id", foreignKey = @ForeignKey(name = "fk_klayswap_pools_coin_1_id"), nullable = false)
  private Coin coin1;


  public List<Coin> getCoins() {
    return Arrays.asList(this.coin0, this.coin1);
  }

  public Coin getOppositeCoin(Coin coin) {
    if (coin.getAddress().equals(this.coin0.getAddress())) {
      return this.coin1;
    } else if (coin.getAddress().equals(this.coin1.getAddress())) {
      return this.coin0;
    } else {
      throw new IllegalArgumentException(
          String.format("klayswap pool [%s] does not contain coin [%s]", this.address,
              coin.getAddress()));
    }
  }

  private KlayswapPool(
      Address poolAddress,
      Integer decimal,
      Coin coin0,
      Coin coin1
  ) {
    this.address = poolAddress;
    this.decimals = decimal;
    this.coin0 = coin0;
    this.coin1 = coin1;
  }

  public static KlayswapPool of(
      Address poolAddress,
      Integer decimal,
      Coin coin0,
      Coin coin1
  ) {
    return new KlayswapPool(
        poolAddress,
        decimal,
        coin0,
        coin1
    );
  }

  public String getName() {
    return coin0.getName() + "-" + coin1.getName();
  }
}

package io.coin.ccbc.definix;

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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "definix_farms")
public class DefinixFarm extends DomainEntity {

  @Embedded
  @AttributeOverride(name = "value", column = @Column(name = "address", nullable = false))
  private Address address;

  @Column(name = "decimals", nullable = false)
  private Integer decimals;

  @Column(name = "pid")
  private Integer pid;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "coin_0_id", foreignKey = @ForeignKey(name = "fk_definix_pairs_coin_0_id"), nullable = false)
  private Coin coin0;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "coin_1_id", foreignKey = @ForeignKey(name = "fk_definix_pairs_coin_1_id"), nullable = false)
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
          String.format("definix pair [%s] does not contain coin [%s]", this.address,
              coin.getAddress()));
    }
  }


  public DefinixFarm(
      Address farmAddress,
      Integer decimal,
      Integer pid,
      Coin coin0,
      Coin coin1
  ) {
    this.address = farmAddress;
    this.decimals = decimal;
    this.pid = pid;
    this.coin0 = coin0;
    this.coin1 = coin1;
  }


  public String getName() {
    return coin0.getName() + "-" + coin1.getName();
  }
}

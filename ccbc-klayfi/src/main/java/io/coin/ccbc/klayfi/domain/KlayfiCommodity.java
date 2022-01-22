package io.coin.ccbc.klayfi.domain;

import com.github.dockerjava.api.exception.BadRequestException;
import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.DomainEntity;
import io.coin.ccbc.klayswap.KlayswapPool;
import java.util.Arrays;
import java.util.Objects;
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
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "klayfi_commodity")
public class KlayfiCommodity extends DomainEntity {

  /*
  reward
  core pool: klay
  core vault: kfi
  vault: lp + kfi
   */
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "pool_id", foreignKey = @ForeignKey(name = "fk_klayfi_commodity_info_histories_pool_id"))
  private KlayswapPool pool;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "coin_id", foreignKey = @ForeignKey(name = "fk_klayfi_commodity_coin_id"))
  private Coin coin;

  @Embedded
  @AttributeOverride(name = "value", column = @Column(name = "address", nullable = false))
  private Address address;

  @Column(name = "decimals", nullable = false)
  private Integer decimals;

  @Column(name = "type", nullable = false)
  @Enumerated(EnumType.STRING)
  private Type type;

  public boolean isStaking() {
    return this.type.equals(Type.CORE_POOL);
  }

  public Coin getMatchedCoin(Address address) {
    if (Objects.nonNull(this.coin) && this.coin.getAddress().equals(address)) {
      return this.coin;
    }

    return this.pool.getCoins().stream()
        .filter(coin -> coin.getAddress().equals(address))
        .findFirst()
        .orElseThrow(() -> new BadRequestException(
            String.format("there is no matched coin with '%s'", address.getValue())));
  }

  public enum Type {
    CORE_POOL("core_pool"),
    CORE_VAULT("core_vault"),
    GROWTH_VAULT("growth_vault"),
    PRIME_VAULT("prime_vault");

    private final String name;

    Type(String name) {
      this.name = name;
    }

    public static Type of(String name) {
      return Arrays.stream(values())
          .filter(v -> name.equals(v.name) || name.equalsIgnoreCase(v.name))
          .findFirst()
          .orElseThrow(() ->
              new IllegalArgumentException(
                  String.format("'%s' is not supported klayfi commodity type", name))
          );
    }
  }
}

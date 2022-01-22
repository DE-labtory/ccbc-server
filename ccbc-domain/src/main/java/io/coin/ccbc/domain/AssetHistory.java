package io.coin.ccbc.domain;

import java.time.LocalDateTime;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(
    name = "asset_histories",
    indexes = {
        @Index(name = "asset_histories_collected_at", columnList = "collected_at"),
    }
)
public class AssetHistory extends History {

  @Embedded
  @AttributeOverride(name = "value", column = @Column(name = "wallet_address", nullable = false))
  private Address walletAddress;

  @Embedded
  @AttributeOverride(name = "value", column = @Column(name = "value", nullable = false))
  private Value value;

  private AssetHistory(
      Address walletAddress,
      Value value,
      LocalDateTime collectedAt
  ) {
    super(collectedAt);
    this.walletAddress = walletAddress;
    this.value = value;
  }

  public static AssetHistory collected(
      Address address,
      Value value,
      LocalDateTime collectedAt
  ) {
    return new AssetHistory(address, value, collectedAt);
  }
}

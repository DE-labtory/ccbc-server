package io.coin.ccbc.api.domain;

import io.coin.ccbc.domain.Value;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TotalAssetSnapshot {

  LocalDateTime time;
  Value totalAsset;
}

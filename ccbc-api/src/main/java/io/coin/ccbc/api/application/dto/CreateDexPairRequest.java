package io.coin.ccbc.api.application.dto;

import io.coin.ccbc.domain.DexPair.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDexPairRequest {

  private String coin0Id;
  private String coin1Id;
  private String address;
  public Type type;
}

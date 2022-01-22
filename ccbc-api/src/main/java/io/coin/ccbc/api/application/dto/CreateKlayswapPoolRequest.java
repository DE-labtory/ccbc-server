package io.coin.ccbc.api.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateKlayswapPoolRequest {

  private String coin0Id;
  private String coin1Id;
  private String name;
  private String address;
}

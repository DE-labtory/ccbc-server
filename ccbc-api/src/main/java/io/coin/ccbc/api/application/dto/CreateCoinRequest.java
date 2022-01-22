package io.coin.ccbc.api.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCoinRequest {

  private String symbol;
  private String originalSymbol;
  private String name;
  private String address;
  private int decimals;
  private String symbolImageUrl;
}

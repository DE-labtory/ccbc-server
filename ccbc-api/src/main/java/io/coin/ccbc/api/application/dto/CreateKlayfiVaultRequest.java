package io.coin.ccbc.api.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateKlayfiVaultRequest {

  private String vaultAddress;
  private String poolAddress;
  private String type;
}

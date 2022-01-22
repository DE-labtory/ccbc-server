package io.coin.ccbc.api.application.dto;

import io.coin.ccbc.domain.Defi;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DefiDto {

  private String name;
  private String symbolImageUrl;
  private String color; // hex representation
  private double tvl;

  public static DefiDto from(Defi defi) {
    return new DefiDto(
        defi.getInformation().getName(),
        defi.getInformation().getSymbolUrl(),
        defi.getInformation().getColor(),
        defi.getInformation().getTvl()
    );
  }
}

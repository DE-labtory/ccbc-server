package io.coin.ccbc.api.application.dto;

import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Commodity;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommodityDto {

  private String name;
  private Address address;
  private double apr;
  private double tvl;
  private List<CoinDto> relatedCoin;

  public static CommodityDto from(Commodity commodity) {
    return new CommodityDto(
        commodity.getName(),
        commodity.getAddress(),
        commodity.getApr(),
        commodity.getTvl(),
        commodity.getRelatedCoin()
            .stream()
            .map(CoinDto::from)
            .collect(Collectors.toList())
    );
  }
}

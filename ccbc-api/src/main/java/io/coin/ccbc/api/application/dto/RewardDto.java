package io.coin.ccbc.api.application.dto;

import io.coin.ccbc.domain.Reward;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RewardDto {

  private String protocol;
  private CommodityDto commodity;
  private double receivableInterestValue;

  public static RewardDto from(String protocolName, Reward reward) {
    return new RewardDto(
        protocolName,
        CommodityDto.from(reward.getCommodity()),
        reward.getReceivableInterestValue()
    );
  }
}
package io.coin.ccbc.klayfi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StakingInfo {

  private Balance stakedBalance;
  private Balance rewardBalance;
}

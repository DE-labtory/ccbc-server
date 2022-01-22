package io.coin.ccbc.klayfi.domain;

import java.util.List;
import lombok.Getter;

@Getter
public class VaultInfo {

  private List<Reward> rewards;
  private VaultBalance vaultBalance;

  public VaultInfo(List<Reward> rewards, VaultBalance vaultBalance) {
    this.rewards = rewards;
    this.vaultBalance = vaultBalance;
  }
}

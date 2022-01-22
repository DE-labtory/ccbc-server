package io.coin.ccbc.kai;

import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Amount;

public interface KaiProtocolClient {

  Amount getStakedBalance(Address address);

  Reward getReward(Address address);

  double getTotalTvl();

  double getTvl();

  double getApr();
}

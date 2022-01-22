package io.coin.ccbc.domain;

import java.util.List;

public interface Defi {

  List<Commodity> getAllCommodities();

  List<InvestmentAsset> getAllInvestmentAssets(Address userAddress);

  String getProtocolName();

  DefiInformation getInformation();

  List<Reward> getRewards(Address address);
}

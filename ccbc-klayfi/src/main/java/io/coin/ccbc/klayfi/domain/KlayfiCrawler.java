package io.coin.ccbc.klayfi.domain;

import java.util.List;

public interface KlayfiCrawler {

  List<KlayfiCommodityInfo> getAllAprs();

  double getTvl();

  void close();
}

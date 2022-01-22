package io.coin.ccbc.kai.infra;

public interface KaiProtocolCrawler {

  double getApr();

  double getTotalTvl();

  double getTvl();

  void close();
}

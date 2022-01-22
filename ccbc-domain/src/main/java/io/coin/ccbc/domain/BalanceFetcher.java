package io.coin.ccbc.domain;

import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.BlockchainClient;
import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.CoinBalance;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class BalanceFetcher {

  private final BlockchainClient blockchainClient;

  public BalanceFetcher(BlockchainClient blockchainClient) {
    this.blockchainClient = blockchainClient;
  }

  public List<CoinBalance> fetchCoinBalance(Address address, List<Coin> coins) {
    Map<Address, Coin> coinMap = coins.stream()
        .collect(Collectors.toMap(Coin::getAddress, coin -> coin));
    return this.blockchainClient
        .getCoinBalances(new ArrayList<>(coinMap.keySet()), address)
        .stream()
        .map(balance -> CoinBalance.of(coinMap.get(balance.getCoinAddress()), balance))
        .collect(Collectors.toList());
  }
}

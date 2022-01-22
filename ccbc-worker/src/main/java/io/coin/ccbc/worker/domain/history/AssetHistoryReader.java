package io.coin.ccbc.worker.domain.history;

import io.coin.ccbc.domain.Account;
import io.coin.ccbc.domain.AccountRepository;
import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.CoinRepository;
import io.coin.ccbc.worker.domain.Reader;
import io.coin.ccbc.worker.domain.WorkerExecutionContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class AssetHistoryReader implements Reader<Account> {

  private final AccountRepository accountRepository;
  private final CoinRepository coinRepository;

  public AssetHistoryReader(
      AccountRepository accountRepository,
      CoinRepository coinRepository
  ) {
    this.accountRepository = accountRepository;
    this.coinRepository = coinRepository;
  }

  @Override
  public List<Account> read(WorkerExecutionContext context) {
    // TODO: optimize or use batch
    List<Account> accounts = this.accountRepository.findAll();
    // TODO: apply cache
    List<Coin> coins = new ArrayList<>(this.coinRepository.getAllCoinMap().values());
    context.put("coins", coins);
//    List<KlayswapPool> klayswapPools = this.klayswapPoolRepository.findAllComplete();
//    context.put("pools", klayswapPools);
    return accounts;
  }
}
package io.coin.ccbc.api.application;

import io.coin.ccbc.api.application.dto.AddressAssetHistoryDto;
import io.coin.ccbc.api.application.dto.AddressAssetView;
import io.coin.ccbc.api.application.dto.CoinAssetDto;
import io.coin.ccbc.api.application.dto.RewardDto;
import io.coin.ccbc.api.application.dto.SummaryDto;
import io.coin.ccbc.api.domain.AssetHistoryAssembler;
import io.coin.ccbc.api.domain.AssetHistoryPeriod;
import io.coin.ccbc.api.domain.TotalAssetSnapshot;
import io.coin.ccbc.domain.Account;
import io.coin.ccbc.domain.AccountRepository;
import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.AddressCoinAssetFetcher;
import io.coin.ccbc.domain.Asset;
import io.coin.ccbc.domain.BalanceFetcher;
import io.coin.ccbc.domain.BlockchainClient;
import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.CoinRepository;
import io.coin.ccbc.domain.Defi;
import io.coin.ccbc.domain.PriceHistoryRepository;
import io.coin.ccbc.domain.Reward;
import io.coin.ccbc.domain.Time;
import io.coin.ccbc.domain.Value;
import io.coin.ccbc.klayswap.KlayswapPoolRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AddressApplicationService {

  private final CoinRepository coinRepository;
  private final AccountRepository accountRepository;
  private final AssetHistoryAssembler assetHistoryAssembler;
  private final List<Defi> defis;
  private final BalanceFetcher balanceFetcher;
  private final AddressCoinAssetFetcher addressCoinAssetFetcher;

  public AddressApplicationService(
      BlockchainClient blockchainClient,
      CoinRepository coinRepository,
      KlayswapPoolRepository klayswapPoolRepository,
      AccountRepository accountRepository,
      PriceHistoryRepository priceHistoryRepository,
      AssetHistoryAssembler assetHistoryAssembler,
      List<Defi> defis,
      BalanceFetcher balanceFetcher,
      AddressCoinAssetFetcher addressCoinAssetFetcher
  ) {
    this.coinRepository = coinRepository;
    this.accountRepository = accountRepository;
    this.assetHistoryAssembler = assetHistoryAssembler;
    this.defis = defis;
    this.balanceFetcher = balanceFetcher;
    this.addressCoinAssetFetcher = addressCoinAssetFetcher;
  }

  @Transactional(readOnly = true)
  public SummaryDto getSummary(String addr) {
    Address address = Address.of(addr);

    List<Coin> allCoins = new ArrayList<>(this.coinRepository.getAllCoinMap().values());

    Value depositableCoinValue = this.balanceFetcher.fetchCoinBalance(address,
            allCoins)
        .stream()
        .map(coinBalance -> Value.of(coinBalance.getAmount(), coinBalance.getCoin().getPrice(),
            coinBalance.getCoin().getDecimals()))
        .reduce(Value.zero(), Value::add);

    Value depositedValue = this.defis.stream()
        .flatMap(defi -> defi.getAllInvestmentAssets(address).stream())
        .flatMap(investmentAsset -> investmentAsset.getAssets().stream())
        .map(Asset::getValue)
        .reduce(Value.zero(), Value::add);

    return SummaryDto.of(
        depositableCoinValue.add(depositedValue),
        depositedValue,
        depositableCoinValue,
        Value.of(
            this.defis
                .stream()
                .flatMap(defi -> defi.getRewards(address).stream())
                .map(Reward::getReceivableInterestValue)
                .reduce(0.0, Double::sum)
        )
    );

  }

  @Transactional(readOnly = true)
  public List<AddressAssetHistoryDto> getAssetHistories(String address, String period) {
    Account account = this.accountRepository.findByAddress(Address.of(address)).orElse(null);
    if (account == null) {
      return Collections.emptyList();
    }

    AssetHistoryPeriod assetHistoryPeriod = AssetHistoryPeriod.of(period);
    LocalDateTime now = Time.now();

    List<TotalAssetSnapshot> totalAssetSnapshots = this.assetHistoryAssembler
        .assembleTotalAssetHistory(account.getAddress(), now, assetHistoryPeriod);

    boolean hasAsset = false;
    List<AddressAssetHistoryDto> addressAssetHistoryDtos = new ArrayList<>();
    for (TotalAssetSnapshot snapshot : totalAssetSnapshots) {
      if (snapshot.getTotalAsset().getValue().compareTo(BigDecimal.ZERO) > 0) {
        hasAsset = true;
      }

      if (hasAsset || !account.getCreatedAt().isAfter(snapshot.getTime())) {
        addressAssetHistoryDtos.add(new AddressAssetHistoryDto(
            snapshot.getTime(),
            snapshot.getTotalAsset().getValue().doubleValue()
        ));
      }
    }
    return addressAssetHistoryDtos;
  }

  @Transactional(readOnly = true)
  public List<AddressAssetView> getAddressAssets(String address) {
    List<AddressAssetView> investmentAssets = this.defis
        .stream()
        .map(defi -> CompletableFuture.supplyAsync(
            () -> defi.getAllInvestmentAssets(Address.of(address))
                .stream().map(investmentAsset -> AddressAssetView.fromCommodityAsset(
                    defi.getProtocolName(),
                    investmentAsset.getCommodity().getName(),
                    investmentAsset.getTotalAssetValue(),
                    investmentAsset.getCommodity().getRelatedCoin()
                ))))
        .collect(Collectors.toList())
        .stream()
        .flatMap(CompletableFuture::join)
        .collect(Collectors.toList());
    List<AddressAssetView> coinAssets = addressCoinAssetFetcher.getCoinAssets(Address.of(address))
        .stream()
        .map(coinAsset -> AddressAssetView.fromCoinAsset(
            coinAsset.getCoin(),
            coinAsset.getDepositableAsset().getAmount()
        )).collect(Collectors.toList());
    return Stream.concat(
            investmentAssets.stream(),
            coinAssets.stream())
        .filter(AddressAssetView::hasValue)
        .sorted(Comparator.comparing(AddressAssetView::getValue).reversed())
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<CoinAssetDto> getCoinAsset(String addr) {
    Address address = Address.of(addr);
    return addressCoinAssetFetcher.getCoinAssets(address)
        .stream()
        .map(coinAsset -> CoinAssetDto.of(
            coinAsset.getCoin(),
            null,
            coinAsset.getDepositableAsset().getAmount(),
            coinAsset.getDepositableAsset().getValue(),
            coinAsset.getTotalAsset().getAmount(),
            coinAsset.getTotalAsset().getValue()
        ))
        .filter(dto -> !dto.getTotalAssetAmount().equals(BigDecimal.ZERO))
        .sorted(Comparator.comparing(CoinAssetDto::getTotalAssetValue).reversed())
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<RewardDto> getRewards(String address) {
    return this.defis.stream()
        .map(defi -> CompletableFuture.supplyAsync(() -> {
          List<Reward> rewards = defi.getRewards(Address.of(address));
          return rewards.stream().map(reward -> RewardDto.from(
              defi.getProtocolName(),
              reward
          ));
        })).collect(Collectors.toList())
        .stream()
        .flatMap(CompletableFuture::join)
        .filter(rewardDto -> rewardDto.getReceivableInterestValue() > 1)
        .collect(Collectors.toList());
  }
}

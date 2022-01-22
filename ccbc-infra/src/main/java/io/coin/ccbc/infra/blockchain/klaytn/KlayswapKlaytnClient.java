package io.coin.ccbc.infra.blockchain.klaytn;

import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Amount;
import io.coin.ccbc.domain.exceptions.BlockchainClientException;
import io.coin.ccbc.infra.blockchain.contracts.CCBCKlayswapViewer;
import io.coin.ccbc.klayswap.KlayswapClient;
import io.coin.ccbc.klayswap.PoolInfo;
import io.coin.ccbc.klayswap.WalletPoolBalance;
import io.coin.ccbc.klayswap.WalletPoolInterest;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.web3j.tuples.generated.Tuple7;

@Slf4j
public class KlayswapKlaytnClient implements KlayswapClient {

  private final CCBCKlayswapViewer ccbcKlayswapViewerContract;
  public static final Address KSP_ADDRESS = Address
      .of("0xc6a2ad8cc6e4a7e08fc37cc5954be07d499e7654");
  private final ForkJoinPool forkJoinPool;

  public KlayswapKlaytnClient(
      CCBCKlayswapViewer ccbcKlayswapViewerContract
  ) {
    this.ccbcKlayswapViewerContract = ccbcKlayswapViewerContract;
    this.forkJoinPool = new ForkJoinPool(50);
  }

  @Override
  public List<WalletPoolBalance> getPoolBalances(
      List<Address> poolAddresses,
      Address walletAddress
  ) {
    //(uint[] memory token0Balances, uint[] memory token1Balances,
    //        uint[] memory receivableKspAmount, uint[] memory receivedKspAmounts,
    //        address[] memory airdropAddresses, uint[] memory airdropReceivedAmounts, uint[] memory airdropReceivableAmounts
    //    )
    Tuple7<List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<String>, List<BigInteger>, List<BigInteger>> poolInterestRes;

    try {
      poolInterestRes = this.ccbcKlayswapViewerContract
          .getPoolInterests(this.addressToValues(poolAddresses), walletAddress.getValue())
          .send();

      return IntStream
          .range(0, poolInterestRes.component1().size())
          .mapToObj(idx -> CompletableFuture.supplyAsync(() -> {
            List<WalletPoolInterest> walletPoolInterests = new ArrayList<>();
            walletPoolInterests.add(WalletPoolInterest.of(
                poolAddresses.get(idx),
                KSP_ADDRESS,
                Amount.of(poolInterestRes.component3().get(idx)),
                Amount.of(poolInterestRes.component4().get(idx))
            ));
            if (!poolInterestRes.component5().get(idx)
                .equals("0x0000000000000000000000000000000000000000")) {
              walletPoolInterests.add(WalletPoolInterest.of(
                  poolAddresses.get(idx),
                  Address.of(poolInterestRes.component5().get(idx)),
                  Amount.of(poolInterestRes.component7().get(idx)),
                  Amount.of(poolInterestRes.component6().get(idx))
              ));
            }
            return WalletPoolBalance.of(
                poolAddresses.get(idx),
                walletAddress,
                BigInteger.ZERO,
                poolInterestRes.component1().get(idx),
                poolInterestRes.component2().get(idx),
                walletPoolInterests
            );
          }, forkJoinPool))
          .collect(Collectors.toList())
          .stream()
          .map(CompletableFuture::join)
          .collect(Collectors.toList());


    } catch (Exception e) {
      throw new BlockchainClientException("error while node call. '%s'", e.getMessage());
    }
  }

  @Override
  public List<PoolInfo> getPoolInfos(List<Address> poolAddresses) {
//    address[] memory poolAddresses, uint[] memory lpAmounts, uint[] memory token0Reserves,
//    uint[] memory token1Reserves, uint[] memory kspPerBlockAmounts, address[] memory airdropAddresses,
//    uint[] memory airdropPerBlockAmounts
    Tuple7<List<String>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<String>, List<BigInteger>> res;
    try {
      res = this.ccbcKlayswapViewerContract.getPoolInfos(this.addressToValues(poolAddresses))
          .send();

      return IntStream
          .range(0, res.component1().size())
          .mapToObj(idx -> {
            String poolAddressStr = res.component1().get(idx);
            Address poolAddress = Address.of(poolAddressStr);
            Map<Address, Amount> interestAmount = new HashMap<>();
            // TODO :: 일반풀 대상으로 60%만 제공한다고함 이부분 추후 조절해야함
            interestAmount.put(
                Address.of("0xc6a2ad8cc6e4a7e08fc37cc5954be07d499e7654"),
                Amount.of(
                    res.component5().get(idx)
//                        .multiply(BigInteger.valueOf(60))
//                        .divide(BigInteger.valueOf(100)
//                        )
                )
            );
            if (res.component7().get(idx).signum() > 0) {
              interestAmount.put(
                  Address.of(res.component6().get(idx)),
                  Amount.of(
                      res.component7().get(idx)
                  )
              );
            }
            return PoolInfo.of(
                poolAddress,
                res.component2().get(idx),
                res.component3().get(idx),
                res.component4().get(idx),
                interestAmount
            );
          }).collect(Collectors.toList());
    } catch (Exception e) {
      throw new BlockchainClientException("error while node call. '%s'", e.getMessage());
    }

  }

  private List<String> addressToValues(List<Address> addresses) {
    return addresses.stream().map(Address::getValue).collect(Collectors.toList());
  }
}

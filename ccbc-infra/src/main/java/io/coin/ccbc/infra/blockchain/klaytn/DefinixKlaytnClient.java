package io.coin.ccbc.infra.blockchain.klaytn;

import io.coin.ccbc.definix.DefinixClient;
import io.coin.ccbc.definix.FarmInfo;
import io.coin.ccbc.definix.WalletFarmBalance;
import io.coin.ccbc.definix.WalletFarmInterest;
import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Amount;
import io.coin.ccbc.domain.exceptions.BlockchainClientException;
import io.coin.ccbc.infra.blockchain.contracts.CCBCDefinixViewer;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.web3j.tuples.generated.Tuple6;

public class DefinixKlaytnClient implements DefinixClient {

  private final CCBCDefinixViewer ccbcDefinixViewerContract;
  public static final Address FINIX_ADDRESS = Address
      .of("0xD51C337147c8033a43F3B5ce0023382320C113Aa");

  public DefinixKlaytnClient(
      CCBCDefinixViewer ccbcDefinixViewerContract
  ) {
    this.ccbcDefinixViewerContract = ccbcDefinixViewerContract;

  }

  @Override
  public List<FarmInfo> getAllFarmInfos(List<Integer> pids) {
    Tuple6<List<String>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>> farmInfoRes;
    try {

      farmInfoRes = ccbcDefinixViewerContract.getAllFarmInfos(
          pids.stream().map(BigInteger::valueOf).collect(Collectors.toList())).send();
    } catch (Exception e) {
      throw new BlockchainClientException("error while node call. '%s'", e.getMessage());
    }
    return IntStream.range(0, farmInfoRes.component1().size())
        .mapToObj(idx -> {
          Map<Address, Amount> interestPerBlock = new HashMap<>();
          // TODO :: 일단 finix만됌. KLAY추적좀 ㅠ
          interestPerBlock.put(FINIX_ADDRESS, Amount.of(farmInfoRes.component6().get(idx)));
          return FarmInfo.of(
              Address.of(farmInfoRes.component1().get(idx)),
              farmInfoRes.component2().get(idx),
              farmInfoRes.component3().get(idx),
              farmInfoRes.component4().get(idx),
              farmInfoRes.component5().get(idx),
              interestPerBlock
          );
        }).collect(Collectors.toList());
  }

  @Override
  public List<WalletFarmBalance> getFarmBalances(Address walletAddress, List<Integer> pids) {
    Tuple6<List<String>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>> walletFarmInfos;
    try {
      walletFarmInfos = ccbcDefinixViewerContract.getWalletFarmInfos(walletAddress.getValue(),
              pids.stream().map(BigInteger::valueOf).collect(Collectors.toList()))
          .send();
    } catch (Exception e) {
      throw new BlockchainClientException("error while node call. '%s'", e.getMessage());
    }

    return IntStream.range(0, walletFarmInfos.component1().size())
        .mapToObj(idx -> {
          List<WalletFarmInterest> walletFarmInterests = new ArrayList<>();
          // TODO :: 일단 finix만됌. KLAY추적좀 ㅠ
          walletFarmInterests.add(WalletFarmInterest.of(
              Address.of(walletFarmInfos.component1().get(idx)),
              FINIX_ADDRESS,
              Amount.of(walletFarmInfos.component5().get(idx)),
              Amount.of(walletFarmInfos.component6().get(idx))
          ));
          return WalletFarmBalance.of(
              Address.of(walletFarmInfos.component1().get(idx)),
              walletAddress,
              walletFarmInfos.component2().get(idx),
              walletFarmInfos.component3().get(idx),
              walletFarmInfos.component4().get(idx),
              walletFarmInterests
          );
        }).collect(Collectors.toList());
  }
}

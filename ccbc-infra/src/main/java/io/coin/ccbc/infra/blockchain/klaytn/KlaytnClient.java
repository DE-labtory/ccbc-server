package io.coin.ccbc.infra.blockchain.klaytn;

import com.klaytn.caver.Caver;
import com.klaytn.caver.methods.request.CallObject;
import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.BlockchainClient;
import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.CoinApproval;
import io.coin.ccbc.domain.DexPair;
import io.coin.ccbc.domain.Path;
import io.coin.ccbc.domain.Trade;
import io.coin.ccbc.domain.WalletCoinBalance;
import io.coin.ccbc.domain.exceptions.BlockchainClientException;
import io.coin.ccbc.infra.blockchain.contracts.ICCBCViewer;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.Utils;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.BatchRequest;
import org.web3j.protocol.core.BatchResponse;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.tuples.generated.Tuple2;


@Slf4j
public class KlaytnClient implements BlockchainClient {
//
//  private static final Address klaySwapAddress = Address
//      .of("0xC6a2Ad8cC6e4A7E08FC37cC5954be07d499E7654");
//  private static final Address definixAddress = Address
//      .of("0x4E61743278Ed45975e3038BEDcaA537816b66b5B");
  private final ICCBCViewer ccbcViewerContract;
  private final Caver caver;
  private final String swapByKlayswapImplAddress;
  private final String swapByDefinixImplAddress;


  public KlaytnClient(
      Caver caver,
      ICCBCViewer ccbcViewerContract,
      String swapByKlayswapImplAddress,
      String swapByDefinixImplAddress
  ) {
    this.caver = caver;
    this.ccbcViewerContract = ccbcViewerContract;
    this.swapByKlayswapImplAddress = swapByKlayswapImplAddress;
    this.swapByDefinixImplAddress = swapByDefinixImplAddress;
  }

  @Override
  public List<WalletCoinBalance> getCoinBalances(List<Address> coinAddress, Address walletAddress) {
    Tuple2<List<String>, List<BigInteger>> res;
    try {
      res = this.ccbcViewerContract
          .getCoinBalances(this.addressToValues(coinAddress), walletAddress.getValue())
          .send();
    } catch (Exception e) {
      throw new BlockchainClientException("error while node call. '%s'", e.getMessage());
    }

    return IntStream
        .range(0, res.component1().size())
        .mapToObj(idx -> WalletCoinBalance.of(
            Address.of(res.component1().get(idx)),
            walletAddress,
            res.component2().get(idx)
        )).collect(Collectors.toList());
  }
  @Override
  public List<Trade> estimateAmountOutFromAmountIn(BigInteger amountIn,
      List<List<Path>> exchangePaths) {
    BatchRequest batchRequest = this.caver.getRpc().newBatch();
    List<BigInteger> estimateRes = new ArrayList<>();
    exchangePaths.forEach(paths -> {
      if (paths.size() < 1) {
        throw new IllegalArgumentException("input path size is less than 1");
      }
      Path firstPath = paths.get(0);
      Coin fromCoin = firstPath.getFromCoin();
      Coin toCoin = firstPath.getToCoin();
      DexPair.Type firstPathDex = firstPath.getTargetDexPair().getType();
      List<Address> coinAddresses = new ArrayList<>();
      List<Address> swapImplAddresses = new ArrayList<>();
      coinAddresses.add(fromCoin.getAddress());
      coinAddresses.add(toCoin.getAddress());
      if (firstPathDex.equals(DexPair.Type.KLAYSWAP)) {
        swapImplAddresses.add(
            Address.of(this.swapByKlayswapImplAddress)
        );
      } else if (firstPathDex.equals(DexPair.Type.DEFINIX)) {
        swapImplAddresses.add(
            Address.of(this.swapByDefinixImplAddress)
        );
      } else {
        throw new IllegalArgumentException(
            String.format("'%s' is not supported dex in klaytn", firstPathDex.name()));
      }

      for (int i = 1; i < paths.size(); i++) {
        Path targetPath = paths.get(i);
        DexPair.Type targetDex = targetPath.getTargetDexPair().getType();
        coinAddresses.add(targetPath.getToCoin().getAddress());

        if (targetDex.equals(DexPair.Type.KLAYSWAP)) {
          swapImplAddresses.add(
              Address.of(this.swapByKlayswapImplAddress)
          );
        } else if (targetDex.equals(DexPair.Type.DEFINIX)) {
          swapImplAddresses.add(
              Address.of(this.swapByDefinixImplAddress)
          );
        } else {
          throw new IllegalArgumentException(
              String.format("'%s' is not supported dex in klaytn", firstPathDex.name()));
        }
      }
      // 여기부터
      Function function = new Function("estimateExactIn",
          Arrays.asList(
              new Uint256(amountIn),
              new DynamicArray<>(
                  org.web3j.abi.datatypes.Address.class,
                  org.web3j.abi.Utils.typeMap(
                      coinAddresses.stream().map(Address::getValue).collect(Collectors.toList()),
                      org.web3j.abi.datatypes.Address.class)),
              new DynamicArray<>(
                  org.web3j.abi.datatypes.Address.class,
                  org.web3j.abi.Utils.typeMap(
                      swapImplAddresses.stream().map(Address::getValue)
                          .collect(Collectors.toList()),
                      org.web3j.abi.datatypes.Address.class))),
          Collections.singletonList(new TypeReference<Uint256>() {
          })
      );

      String encodedFunction = FunctionEncoder.encode(function);
      batchRequest.add(this.caver.getRpc().getKlay().call(new CallObject(
              null,
              this.ccbcViewerContract.getContractAddress(),
              null,
              null,
              null,
              encodedFunction
          ),
          DefaultBlockParameterName.LATEST));
      // 여기까지는 오토젠 된것보고 고쳐야함

    });

    BatchResponse batchResponse;
    try {
      batchResponse = batchRequest.send();
    } catch (IOException e) {
      throw new BlockchainClientException("error while node call. '%s'", e.getMessage());
    }
    batchResponse.getResponses().forEach(
        res -> {
          try {
            List<Type> value = FunctionReturnDecoder.decode(
                (String) res.getResult(), Utils.convert(List.of(new TypeReference<Uint256>() {
                })));
            estimateRes.add((BigInteger) value.get(0).getValue());
          } catch (Exception e) {
            log.debug("error while converting estimate res : " + e.getMessage());
            estimateRes.add(BigInteger.ZERO);
          }
        }
    );
    return IntStream.range(0, estimateRes.size())
        .mapToObj(i ->
        {
          List<Path> pathList = exchangePaths.get(i);
          if (pathList.size() < 1) {
            throw new IllegalArgumentException("result path size is less than 1");
          }
          return Trade.of(
              pathList,
              pathList.get(0).getFromCoin(),
              amountIn,
              pathList.get(pathList.size() - 1).getToCoin(),
              estimateRes.get(i),
              0.0 // TODO - change to real price impact
          );
        }).collect(Collectors.toList());
  }

  @Override
  public List<Trade> estimateAmountInFromAmountOut(BigInteger amountOut,
      List<List<Path>> exchangePaths) {
    BatchRequest batchRequest = this.caver.getRpc().newBatch();
    List<BigInteger> estimateRes = new ArrayList<>();
    exchangePaths.forEach(paths -> {
      if (paths.size() < 1) {
        throw new IllegalArgumentException("input path size is less than 1");
      }
      Path firstPath = paths.get(0);
      Coin fromCoin = firstPath.getFromCoin();
      Coin toCoin = firstPath.getToCoin();
      DexPair.Type firstPathDex = firstPath.getTargetDexPair().getType();
      List<Address> coinAddresses = new ArrayList<>();
      List<Address> swapImplAddresses = new ArrayList<>();
      coinAddresses.add(fromCoin.getAddress());
      coinAddresses.add(toCoin.getAddress());
      if (firstPathDex.equals(DexPair.Type.KLAYSWAP)) {
        swapImplAddresses.add(
            Address.of(this.swapByKlayswapImplAddress)
        );
      } else if (firstPathDex.equals(DexPair.Type.DEFINIX)) {
        swapImplAddresses.add(
            Address.of(this.swapByDefinixImplAddress)
        );
      } else {
        throw new IllegalArgumentException(
            String.format("'%s' is not supported dex in klaytn", firstPathDex.name()));
      }

      for (int i = 1; i < paths.size(); i++) {
        Path targetPath = paths.get(i);
        DexPair.Type targetDex = targetPath.getTargetDexPair().getType();
        coinAddresses.add(targetPath.getToCoin().getAddress());

        if (targetDex.equals(DexPair.Type.KLAYSWAP)) {
          swapImplAddresses.add(
              Address.of(this.swapByKlayswapImplAddress)
          );
        } else if (targetDex.equals(DexPair.Type.DEFINIX)) {
          swapImplAddresses.add(
              Address.of(this.swapByDefinixImplAddress)
          );
        } else {
          throw new IllegalArgumentException(
              String.format("'%s' is not supported dex in klaytn", firstPathDex.name()));
        }
      }
      // 여기부터
      Function function = new Function("estimateExactOut",
          Arrays.asList(
              new Uint256(amountOut),
              new DynamicArray<>(
                  org.web3j.abi.datatypes.Address.class,
                  org.web3j.abi.Utils.typeMap(
                      coinAddresses.stream().map(Address::getValue).collect(Collectors.toList()),
                      org.web3j.abi.datatypes.Address.class)),
              new DynamicArray<>(
                  org.web3j.abi.datatypes.Address.class,
                  org.web3j.abi.Utils.typeMap(
                      swapImplAddresses.stream().map(Address::getValue)
                          .collect(Collectors.toList()),
                      org.web3j.abi.datatypes.Address.class))),
          Collections.singletonList(new TypeReference<Uint256>() {
          })
      );

      String encodedFunction = FunctionEncoder.encode(function);
      batchRequest.add(this.caver.getRpc().getKlay().call(new CallObject(
              this.ccbcViewerContract.getContractAddress(),
              this.ccbcViewerContract.getContractAddress(),
              null, null, null, encodedFunction),
          DefaultBlockParameterName.LATEST));
      // 여기까지는 오토젠 된것보고 고쳐야함

    });

    BatchResponse batchResponse;
    try {
      batchResponse = batchRequest.send();
    } catch (IOException e) {
      throw new BlockchainClientException("error while node call. '%s'", e.getMessage());
    }
    batchResponse.getResponses().forEach(
        res -> {
          try {
            List<Type> value = FunctionReturnDecoder.decode(
                (String) res.getResult(), Utils.convert(List.of(new TypeReference<Uint256>() {
                })));
            estimateRes.add((BigInteger) value.get(0).getValue());
          } catch (Exception e) {
            log.debug("error while converting estimate res : " + e.getMessage());
            estimateRes.add(BigInteger.ZERO);
          }
        }
    );
    return IntStream.range(0, estimateRes.size())
        .mapToObj(i ->
        {
          List<Path> pathList = exchangePaths.get(i);
          if (pathList.size() < 1) {
            throw new IllegalArgumentException("result path size is less than 1");
          }
          return Trade.of(
              pathList,
              pathList.get(0).getFromCoin(),
              amountOut,
              pathList.get(pathList.size() - 1).getToCoin(),
              estimateRes.get(i),
              0.0 // TODO - change to real price impact
          );
        }).collect(Collectors.toList());
  }

  @Override
  public List<CoinApproval> isApprovedToCcbc(Address targetAddress, List<Address> coinAddresses) {
//    List<Boolean> approvals;
//    try {
//      approvals = this.ccbcViewerContract.checkApprovals(
//          targetAddress.getValue(),
//          this.ccbcViewerContract.getContractAddress(),
//          coinAddresses.stream()
//              .map(Address::getValue)
//              .collect(Collectors.toList())
//      ).send();
//    } catch (Exception e) {
//      throw new BlockchainClientException("error while node call. '%s'", e.getMessage());
//    }

    // TODO: 로이야 고쳐줘~!
    return IntStream.range(0, coinAddresses.size()).boxed()
        .map(index -> CoinApproval.of(coinAddresses.get(index), true))
        .collect(Collectors.toList());
  }
  private List<String> addressToValues(List<Address> addresses) {
    return addresses.stream().map(Address::getValue).collect(Collectors.toList());
  }

}

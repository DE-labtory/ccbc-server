package io.coin.ccbc.api.application;

import io.coin.ccbc.api.application.dto.ApproveCallDataRequestParams;
import io.coin.ccbc.api.application.dto.ContractCallDataDto;
import io.coin.ccbc.api.application.dto.SwapCallDataRequestParams;
import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.CoinRepository;
import io.coin.ccbc.infra.blockchain.config.properties.KlaytnProperties;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ContractApplicationService {

  private final static String MAX_UINT256 = "0xffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff";
  private final static String ZERO_ADDRESS = "0x0000000000000000000000000000000000000000";
  private final CoinRepository coinRepository;
  private final String ccbcViewerAddress;
  private final String kip7ApproveAbi;

  public ContractApplicationService(
      CoinRepository coinRepository,
      KlaytnProperties klaytnProperties,
      @Qualifier("kip7ApproveAbi") String kip7ApproveAbi
  ) {
    this.coinRepository = coinRepository;
    this.ccbcViewerAddress = klaytnProperties.getContracts().getViewerAddress();
    this.kip7ApproveAbi = kip7ApproveAbi;
  }

  public ContractCallDataDto getApproveCallData(
      ApproveCallDataRequestParams request) {
    Address address = null;
    if (request.getCoinId() == null && request.getPoolId() == null) {
      throw new IllegalArgumentException("both coinId and poolId are null");
    }

    if (request.getCoinId() != null) {
      address = this.coinRepository.findByIdOrElseThrow(request.getCoinId()).getAddress();
    } else if (request.getPoolId() != null) {
//      address = this.klayswapPoolRepository.findByIdOrElseThrow(request.getPoolId()).getAddress();
    }

    return new ContractCallDataDto(
        address.toString(),
        BigInteger.ZERO,
        this.kip7ApproveAbi,
        Arrays.asList(
            this.ccbcViewerAddress,
            MAX_UINT256
        )
    );
  }

  public ContractCallDataDto getSwapCallData(
      SwapCallDataRequestParams request) {
    Coin fromCoin = this.coinRepository.findByIdOrElseThrow(request.getFromCoinId());
    Coin toCoin = this.coinRepository.findByIdOrElseThrow(request.getToCoinId());
    BigInteger fromAmount = BigDecimal.valueOf(request.getFromAmount())
        .multiply(new BigDecimal(10).pow(fromCoin.getDecimals())).toBigInteger();
    BigInteger toAmount = BigDecimal.valueOf(request.getToAmount())
        .multiply(new BigDecimal(10).pow(toCoin.getDecimals())).toBigInteger();

    return new ContractCallDataDto(
        this.ccbcViewerAddress,
        fromCoin.getAddress().equals(ZERO_ADDRESS) ? fromAmount : BigInteger.ZERO,
        "TODO: get new ccbc abi",
        null // TODO: set params as new ccbc contract
    );
  }
}

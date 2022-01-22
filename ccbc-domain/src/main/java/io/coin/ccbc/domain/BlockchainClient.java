package io.coin.ccbc.domain;

import java.math.BigInteger;
import java.util.List;

public interface BlockchainClient {

  List<WalletCoinBalance> getCoinBalances(List<Address> coinAddress, Address walletAddress);

  List<Trade> estimateAmountOutFromAmountIn(BigInteger amountIn, List<List<Path>> exchangePaths);

  List<Trade> estimateAmountInFromAmountOut(BigInteger amountOut, List<List<Path>> exchangePaths);

  List<CoinApproval> isApprovedToCcbc(Address targetAddress, List<Address> coinAddresses);
}

package io.coin.ccbc.klayswap;

import io.coin.ccbc.domain.Address;
import java.util.List;

public interface KlayswapClient {

  List<PoolInfo> getPoolInfos(List<Address> poolAddresses);

  List<WalletPoolBalance> getPoolBalances(List<Address> poolAddresses, Address walletAddress);

}

package io.coin.ccbc.definix;

import io.coin.ccbc.domain.Address;
import java.util.List;

public interface DefinixClient {

  List<FarmInfo> getAllFarmInfos(List<Integer> pids);

  List<WalletFarmBalance> getFarmBalances(Address walletAddress, List<Integer> pids);
}

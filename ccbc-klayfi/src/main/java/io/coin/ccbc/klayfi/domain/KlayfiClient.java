package io.coin.ccbc.klayfi.domain;

import io.coin.ccbc.domain.Address;

public interface KlayfiClient {

  StakingInfo getCorePoolInfo(Address stakingAddress, Address targetAddress);

  VaultInfo getCoreVaultInfo(Address vaultAddress, Address targetAddress);

  VaultInfo getVaultInfo(Address vaultAddress, Address targetAddress);
}

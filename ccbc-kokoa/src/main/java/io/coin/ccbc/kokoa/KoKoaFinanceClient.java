package io.coin.ccbc.kokoa;

import io.coin.ccbc.domain.Address;

public interface KoKoaFinanceClient {

  KokoaStatus getStatus();

  KokoaBalance getBalance(Address address);
}

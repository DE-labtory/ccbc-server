package io.coin.ccbc.kronos;

import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Amount;

public interface KronosClient {
    double getTotalTvl();

    double getApy();

    Amount getStakedBalance(Address address);
}

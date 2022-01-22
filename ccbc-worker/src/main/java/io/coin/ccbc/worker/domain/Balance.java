package io.coin.ccbc.worker.domain;


import io.coin.ccbc.domain.Amount;
import lombok.Getter;

@Getter
public abstract class Balance {

  protected Amount amount;
}

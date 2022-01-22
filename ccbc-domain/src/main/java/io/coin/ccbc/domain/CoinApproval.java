package io.coin.ccbc.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Bomi
 * @date 2021/08/24
 */

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CoinApproval {

  Address coinAddress;
  boolean isApproved;

  public static CoinApproval of(
      Address coinAddress,
      boolean isApproved
  ) {
    return new CoinApproval(coinAddress, isApproved);
  }

}

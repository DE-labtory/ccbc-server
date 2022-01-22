package io.coin.ccbc.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Reward {
  private String protocol;
  private Commodity commodity;
  private double receivableInterestValue;
}

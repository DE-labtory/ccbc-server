package io.coin.ccbc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DefiInformation {

  private String name;
  private String symbolUrl;
  private String color; // hex representation
  private double tvl;
}

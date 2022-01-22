package io.coin.ccbc.infra.exchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Bomi
 * @date 2021/08/07
 */

@Getter
@NoArgsConstructor
public class KorbitTickerDto {

  private long timestamp; // 	Unix timestamp in milliseconds of the last filled order.
  private String last;  // Price of the last filled order
}

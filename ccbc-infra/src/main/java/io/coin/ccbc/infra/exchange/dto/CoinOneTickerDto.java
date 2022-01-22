package io.coin.ccbc.infra.exchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Bomi
 * @date 2021/07/18
 */

@ToString
@Getter
@NoArgsConstructor
public class CoinOneTickerDto {

  private String result; // Request's result
  private Integer errorCode; // Error Code
  private Integer timestamp; // TimeStamp
  private String currency; // Currency
  private Double high; // Highest price in 24 hours
  private Double low; // Lowest price in 24 hours
  private Double first; // First price in 24 hours
  private Double last; //  io.coin.ccbc.domain.Price at request
  private Double volume; // Coin volume of completed orders in 24 hours
  private Double yesterdayHigh; // Highest price during for 24 ~ 48 hours
  private Double yesterdayLow; // Lowest price during for 24 ~ 48 hours
  private Double yesterdayFist; // First price during for 24 ~ 48 hours
  private Double yesterdayLast; // Price at request before 24 hours
  private Double yesterdayVolume; // Coin volume of completed orders during for 24 ~ 48 hours
}

package io.coin.ccbc.api.application.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Bomi
 * @date 2021/08/20
 */

@Getter
@Setter
public class SwapInformationRequestParams {

  private String address;
  private String fromCoinId;
  private String toCoinId;
  private Double value;
  private Double amount;
}

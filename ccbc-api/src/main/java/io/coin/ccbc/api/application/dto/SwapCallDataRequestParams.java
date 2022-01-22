package io.coin.ccbc.api.application.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SwapCallDataRequestParams {

  private String fromCoinId;
  private Double fromAmount;
  private String toCoinId;
  private Double toAmount;
  private List<String> path;
}

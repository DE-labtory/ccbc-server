package io.coin.ccbc.api.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApproveCallDataRequestParams {

  private String coinId;
  private String poolId;
}

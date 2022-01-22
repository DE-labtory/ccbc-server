package io.coin.ccbc.api.application.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.coin.ccbc.support.BigDecimalToStringSerializer;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PoolInfoDto {

  @JsonSerialize(using = BigDecimalToStringSerializer.class)
  private final BigDecimal apr;
  private final String name;
  private final String address;

}

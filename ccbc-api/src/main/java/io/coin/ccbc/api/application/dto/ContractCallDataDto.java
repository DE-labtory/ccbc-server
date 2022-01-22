package io.coin.ccbc.api.application.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.coin.ccbc.support.BigIntegerToStringSerializer;
import java.math.BigInteger;
import java.util.List;
import lombok.Getter;

@Getter
public class ContractCallDataDto {

  private final String to;
  @JsonSerialize(using = BigIntegerToStringSerializer.class)
  private final BigInteger value;
  private final String abi;
  private final List<Object> params;

  public ContractCallDataDto(
      String to,
      BigInteger value,
      String abi,
      List<Object> params
  ) {
    this.to = to;
    this.value = value;
    this.abi = abi;
    this.params = params;
  }
}

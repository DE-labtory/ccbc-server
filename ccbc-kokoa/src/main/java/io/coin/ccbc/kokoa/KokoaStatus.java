package io.coin.ccbc.kokoa;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KokoaStatus {

  @JsonDeserialize(using = DecimalAppliedValueDeserializer.class)
  private double TVL;
  private Earn earn;
  private Borrow borrow;
  private Govern govern;
  private List<Farm> farms;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Earn {

    @JsonDeserialize(using = DecimalAppliedValueDeserializer.class)
    private double volume;
    @JsonDeserialize(using = APRDeserializer.class)
    private double APR;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Borrow {

    @JsonDeserialize(using = DecimalAppliedValueDeserializer.class)
    private double volume;
    @JsonDeserialize(using = APRDeserializer.class)
    private double APR;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Govern {

    @JsonDeserialize(using = DecimalAppliedValueDeserializer.class)
    private double volume;
    @JsonDeserialize(using = APRDeserializer.class)
    private double APR;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Farm {

    private String name;
    private String address;
    private String tokenA;
    private String tokenB;
    @JsonDeserialize(using = DecimalAppliedValueDeserializer.class)
    private double lpTokenTotalSupply;
    private double lpTokenPrice;
    @JsonDeserialize(using = APRDeserializer.class)
    private double APR;
  }
}

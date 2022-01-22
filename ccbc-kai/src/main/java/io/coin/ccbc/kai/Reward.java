package io.coin.ccbc.kai;

import io.coin.ccbc.domain.Amount;
import java.math.BigInteger;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Reward {

  private Amount vkaiAmount;
  private Amount kaiAmount;

  private Reward(Amount vkaiAmount, Amount kaiAmount) {
    this.vkaiAmount = vkaiAmount;
    this.kaiAmount = kaiAmount;
  }

  public static Reward fromCallResult(String result) {
    return new Reward(
        Amount.of(new BigInteger(result.substring(result.length() - 64, result.length()), 16)),
        Amount.of(new BigInteger(result.substring(result.length() - 128, result.length() - 64), 16))
    );
  }

  @Override
  public String toString() {
    return "Reward{" +
        "kaiAmount=" + this.kaiAmount +
        ", vkaiAmount=" + vkaiAmount +
        '}';
  }
}

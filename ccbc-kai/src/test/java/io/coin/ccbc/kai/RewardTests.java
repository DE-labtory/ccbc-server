package io.coin.ccbc.kai;

import static org.assertj.core.api.Assertions.assertThat;

import io.coin.ccbc.domain.Amount;
import java.math.BigInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RewardTests {

  @Test
  @DisplayName("[fromCallResult] call result로 부터 reward를 생성한다")
  void fromCallResult() {
    final String callResult = "0x000000000000000000000000000000000000000000000000000000000000004000000000000000000000000000000000000000000000000000000000000000a00000000000000000000000000000000000000000000000000000000000000002000000000000000000000000e950bdcfa4d1e45472e76cf967db93dbfc51ba3e00000000000000000000000044efe1ec288470276e29ac3adb632bff990e2e1f000000000000000000000000000000000000000000000000000000000000000200000000000000000000000000000000000000000000000003e5cc7c8fd01303000000000000000000000000000000000000000000000000009b84fee36eed0e";
    Reward reward = Reward.fromCallResult(callResult);
    assertThat(reward.getVkaiAmount()).isEqualTo(Amount.of(new BigInteger("43774851662408974")));
    assertThat(reward.getKaiAmount()).isEqualTo(Amount.of(new BigInteger("280855387141313283")));
  }
}

package io.coin.ccbc.api.application.dto;

import io.coin.ccbc.domain.DexPair.Type;
import io.coin.ccbc.domain.Path;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Bomi
 * @date 2021/08/19
 */
@Getter
@AllArgsConstructor
public class SwapPathDto {

  private final CoinDto fromCoin;

  private final CoinDto toCoin;

  private final String dex;

  private final Double feePercentage;

  public static SwapPathDto from(Path path) {
    return new SwapPathDto(
        CoinDto.from(path.getFromCoin()),
        CoinDto.from(path.getToCoin()),
        path.getTargetDexPair().getType().name(),
        path.getTargetDexPair().getType() == Type.KLAYSWAP ? 0.3 : 0.2
    );
  }

}

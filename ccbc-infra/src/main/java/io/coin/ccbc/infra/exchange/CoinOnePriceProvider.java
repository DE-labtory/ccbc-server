package io.coin.ccbc.infra.exchange;

import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.Price;
import io.coin.ccbc.domain.PriceProvider;
import io.coin.ccbc.domain.exceptions.ExchangeServerException;
import io.coin.ccbc.infra.exchange.dto.CoinOneTickerDto;
import java.io.IOException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import retrofit2.Response;

/**
 * @author Bomi
 * @date 2021/07/22
 */

@Order(1)
@Component
public class CoinOnePriceProvider implements PriceProvider {

  private final CoinOneHttpApis coinOneHttpApis;

  public CoinOnePriceProvider(final CoinOneHttpApis coinOneHttpApis) {
    this.coinOneHttpApis = coinOneHttpApis;
  }

  @Override
  public Price getPrice(Coin coin) throws IllegalStateException {
    CoinOneTickerDto payload;
    try {
      Response<CoinOneTickerDto> response = this.coinOneHttpApis
          .getKRWTickerByCurrency(coin.getOriginalSymbol().toLowerCase())
          .execute();
      this.validateResponseStatus(response);

      payload = response.body();
      if (payload == null) {
        throw new IllegalStateException("coinone response body is null");
      }
      if (payload.getCurrency() == null) {
        throw new IllegalArgumentException(
            String.format("This coin could not be found at coinone. this coin original symbol: %s"
                , coin.getOriginalSymbol()
            ));
      }
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
    return Price.from(payload.getLast());
  }

  private void validateResponseStatus(Response<?> response) {
    if (!response.isSuccessful()) {
      throw new ExchangeServerException("Http status code is " + response.code());
    }
  }
}

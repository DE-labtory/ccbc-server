package io.coin.ccbc.infra.exchange;

import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.Price;
import io.coin.ccbc.domain.PriceProvider;
import io.coin.ccbc.domain.exceptions.ExchangeServerException;
import io.coin.ccbc.infra.exchange.dto.KorbitTickerDto;
import java.io.IOException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import retrofit2.Response;

/**
 * @author Bomi
 * @date 2021/08/07
 */

@Order(2)
@Component
public class KorbitPriceProvider implements PriceProvider {

  private static final String MARKET_CURRENCY = "krw";

  private final KorbitHttpApis korbitHttpApis;

  public KorbitPriceProvider(final KorbitHttpApis korbitHttpApis) {
    this.korbitHttpApis = korbitHttpApis;
  }

  @Override
  public Price getPrice(Coin coin) throws IllegalStateException {
    double price;
    String pair = String.format("%s_%s",
        coin.getOriginalSymbol().toLowerCase(), MARKET_CURRENCY);

    try {
      Response<KorbitTickerDto> response = this.korbitHttpApis
          .getTickerByCoinSymbolMarketSymbolPair(pair)
          .execute();

      if (!response.isSuccessful()) {
        if (response.code() == 400) {
          throw new IllegalArgumentException(
              String.format("This coin could not be found at korbit. this coin original symbol: %s"
                  , coin.getOriginalSymbol()
              ));
        }
        throw new ExchangeServerException("Http status code is " + response.code());
      }

      KorbitTickerDto payload = response.body();
      if (payload == null || payload.getLast() == null) {
        throw new IllegalStateException("korbit response body is null");
      }

      price = Double.parseDouble(payload.getLast());
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
    return Price.from(price);
  }
}

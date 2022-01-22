package io.coin.ccbc.infra.exchange;

import io.coin.ccbc.infra.exchange.dto.KorbitTickerDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author Bomi
 * @date 2021/08/07
 */

public interface KorbitHttpApis {

  @GET("ticker")
  Call<KorbitTickerDto> getTickerByCoinSymbolMarketSymbolPair(
      @Query("currency_pair") String coinAndMarketPair // default: btc_krw
  );

}

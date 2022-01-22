package io.coin.ccbc.infra.exchange;


import io.coin.ccbc.infra.exchange.dto.CoinOneTickerDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author Bomi
 * @date 2021/07/18
 */

public interface CoinOneHttpApis {

  @GET("/ticker")
  Call<CoinOneTickerDto> getKRWTickerByCurrency(@Query("currency") String currency);

}

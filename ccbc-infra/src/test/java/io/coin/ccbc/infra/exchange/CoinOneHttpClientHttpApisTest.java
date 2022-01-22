package io.coin.ccbc.infra.exchange;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.coin.ccbc.infra.exchange.config.OkHttpClientConfig;
import io.coin.ccbc.infra.exchange.dto.CoinOneTickerDto;
import io.coin.ccbc.infra.exchange.factory.ObjectMapperFactory;
import java.io.IOException;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * @author Bomi
 * @date 2021/07/22
 */

class CoinOneHttpClientHttpApisTest {

  private OkHttpClient okHttpClient;

  private ObjectMapper objectMapper;

  private CoinOneHttpApis coinOneHttpApis;

  @BeforeEach
  public void setUp() {
    okHttpClient = new OkHttpClientConfig().okHttpClient();
    objectMapper = ObjectMapperFactory.snakeCaseObjectMapper();
    coinOneHttpApis = this.mockCoinOneHttpApis();
  }

  @Test
  @DisplayName("코인원에 존재하는 코인의 원화 시세 요청")
  public void request_krw_ticker_when_no_problem() throws IOException {
    // given
    String expectedCurrency = "klay";
    String expectedResult = "success";

    // when
    Response<CoinOneTickerDto> actual
        = coinOneHttpApis.getKRWTickerByCurrency(expectedCurrency).execute();
    CoinOneTickerDto payload = actual.body();

    // then
    assertAll(
        () -> assertTrue(actual.isSuccessful()),
        () -> assertNull(actual.errorBody()),
        () -> assertEquals(expectedResult, payload.getResult()),
        () -> assertNull(payload.getErrorCode()),
        () -> assertEquals(expectedCurrency, payload.getCurrency()),
        () -> assertNotNull(payload.getLast())
    );
  }

  @Test
  @DisplayName("코인원에 존재하지 않는 코인의 원화 시세 요청")
  public void request_krw_ticker_when_a_coin_does_not_exist_in_coinone() throws IOException {
    // given
    String currency = "coin that doesn't exist";
    String expectedResult = "success";

    // when
    Response<CoinOneTickerDto> actual
        = coinOneHttpApis.getKRWTickerByCurrency(currency).execute();
    CoinOneTickerDto payload = actual.body();

    // then
    assertAll(
        () -> assertTrue(actual.isSuccessful()),
        () -> assertNull(actual.errorBody()),
        () -> assertEquals(expectedResult, payload.getResult()),
        () -> assertNull(payload.getErrorCode()),
        () -> assertNull(payload.getCurrency()),
        () -> assertNull(payload.getLast())
    );
  }

  private CoinOneHttpApis mockCoinOneHttpApis() {
    String host = "https://api.coinone.co.kr";
    Retrofit coinOneRetrofit = new Retrofit.Builder()
        .baseUrl(host)
        .addConverterFactory(JacksonConverterFactory.create(objectMapper))
        .client(okHttpClient)
        .build();

    return coinOneRetrofit.create(CoinOneHttpApis.class);
  }
}
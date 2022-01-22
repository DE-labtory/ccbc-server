package io.coin.ccbc.infra.exchange;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.coin.ccbc.infra.exchange.config.OkHttpClientConfig;
import io.coin.ccbc.infra.exchange.dto.KorbitTickerDto;
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
 * @date 2021/08/07
 */

class KorbitHttpApisTest {

  private OkHttpClient okHttpClient;

  private ObjectMapper objectMapper;

  private KorbitHttpApis korbitHttpApis;

  @BeforeEach
  public void setUp() {
    okHttpClient = new OkHttpClientConfig().okHttpClient();
    objectMapper = ObjectMapperFactory.snakeCaseObjectMapper();
    korbitHttpApis = this.mockHttpApis();
  }

  @Test
  @DisplayName("코빗에 존재하는 코인의 원화 시세 요청")
  public void request_krw_ticker_when_no_problem() throws IOException {
    // given
    String parameter = "klay_krw";

    // when
    Response<KorbitTickerDto> actual
        = this.korbitHttpApis.getTickerByCoinSymbolMarketSymbolPair(parameter)
        .execute();
    KorbitTickerDto payload = actual.body();

    // then
    assertAll(
        () -> assertTrue(actual.isSuccessful()),
        () -> assertNull(actual.errorBody()),
        () -> assertNotNull(payload),
        () -> assertNotNull(payload.getLast())
    );
  }

  @Test
  @DisplayName("코빗에 존재하지 않는 코인의 원화 시세 요청")
  public void request_krw_ticker_when_a_coin_does_not_exist_in_korbit() throws IOException {
    // given
    String parameter = "coin that doesn't exist";

    // when
    Response<KorbitTickerDto> actual
        = this.korbitHttpApis.getTickerByCoinSymbolMarketSymbolPair(parameter)
        .execute();
    KorbitTickerDto payload = actual.body();

    // then
    assertAll(
        () -> assertFalse(actual.isSuccessful()),
        () -> assertEquals(400, actual.code()),
        () -> assertNotNull(actual.errorBody()),
        () -> assertNull(payload)
    );
  }

  private KorbitHttpApis mockHttpApis() {
    String host = "https://api.korbit.co.kr/v1/";
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(host)
        .addConverterFactory(JacksonConverterFactory.create(objectMapper))
        .client(okHttpClient)
        .build();

    return retrofit.create(KorbitHttpApis.class);
  }


}
package io.coin.ccbc.api.web;

import io.coin.ccbc.api.application.CoinApplicationService;
import io.coin.ccbc.api.application.dto.AddressAssetHistoryDto;
import io.coin.ccbc.api.application.dto.CoinPriceDto;
import io.coin.ccbc.api.application.dto.CreateCoinRequest;
import io.coin.ccbc.api.domain.AssetHistoryPeriod;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bomi
 * @date 2021/08/17
 */

@RestController
@RequestMapping("api/v1/coins")
public class CoinController {

  private final CoinApplicationService coinApplicationService;

  public CoinController(
      final CoinApplicationService coinApplicationService
  ) {
    this.coinApplicationService = coinApplicationService;
  }

  @GetMapping("")
  @ResponseStatus(value = HttpStatus.OK)
  public List<CoinPriceDto> getCoins() {
    return this.coinApplicationService.getAllCoins();
  }

  @GetMapping("/{coinId}")
  @ResponseStatus(value = HttpStatus.OK)
  public CoinPriceDto getCoinDetail(
      @PathVariable final String coinId
  ) {
    return this.coinApplicationService.getCoin(coinId);
  }

  @GetMapping("/{coinId}/price-histories")
  @ResponseStatus(value = HttpStatus.OK)
  public List<AddressAssetHistoryDto> getCoinPriceHistory(
      @PathVariable final String coinId,
      @RequestParam(name = "period") String period
  ) {
    return this.coinApplicationService.getCoinPriceHistory(coinId, AssetHistoryPeriod.of(period));
  }

  @PostMapping
  @ResponseStatus(value = HttpStatus.CREATED)
  public void createCoin(
      @RequestParam(name = "clientId") String clientId,
      @RequestBody CreateCoinRequest request

  ) {
    this.coinApplicationService.createCoin(request);
  }
}

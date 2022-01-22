package io.coin.ccbc.api.web;

import io.coin.ccbc.api.application.AddressApplicationService;
import io.coin.ccbc.api.application.dto.AddressAssetHistoryDto;
import io.coin.ccbc.api.application.dto.CoinAssetDto;
import io.coin.ccbc.api.application.dto.AddressAssetView;
import io.coin.ccbc.api.application.dto.RewardDto;
import io.coin.ccbc.api.application.dto.SummaryDto;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/addresses")
public class AddressController {

  private final AddressApplicationService addressApplicationService;

  public AddressController(AddressApplicationService addressApplicationService) {
    this.addressApplicationService = addressApplicationService;
  }

  @GetMapping("/{address}/summary")
  @ResponseStatus(value = HttpStatus.OK)
  public SummaryDto getSummary(@PathVariable final String address) {
    return this.addressApplicationService.getSummary(address);
  }

  @GetMapping("/{address}/assets")
  @ResponseStatus(value = HttpStatus.OK)
  public List<AddressAssetView> getInvestmentAsset(@PathVariable final String address) {
    return this.addressApplicationService.getAddressAssets(address);
  }

  @GetMapping("/{address}/coins")
  @ResponseStatus(value = HttpStatus.OK)
  public List<CoinAssetDto> getCoinAssetV2(@PathVariable final String address) {
    return this.addressApplicationService.getCoinAsset(address);
  }


  @GetMapping("/{address}/asset-histories")
  @ResponseStatus(value = HttpStatus.OK)
  public List<AddressAssetHistoryDto> getAssetHistories(
      @PathVariable final String address,
      @RequestParam(name = "period") String period
  ) {
    return this.addressApplicationService.getAssetHistories(address, period);
  }

  @GetMapping("/{address}/rewards")
  @ResponseStatus(value = HttpStatus.OK)
  public List<RewardDto> getRewards(
      @PathVariable final String address
  ) {
    return this.addressApplicationService.getRewards(address);
  }
}

package io.coin.ccbc.api.web;

import io.coin.ccbc.api.application.DefiQueryService;
import io.coin.ccbc.api.application.dto.CommodityDto;
import io.coin.ccbc.api.application.dto.DefiDto;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/defis")
@RestController
public class DefiController {

  private DefiQueryService defiQueryService;

  public DefiController(DefiQueryService defiQueryService) {
    this.defiQueryService = defiQueryService;
  }

  @GetMapping
  @ResponseStatus(value = HttpStatus.OK)
  public List<DefiDto> getDefis() {
    return this.defiQueryService.getAllDefis();
  }

  @GetMapping("/{defiName}")
  @ResponseStatus(value = HttpStatus.OK)
  public DefiDto getDefiByName(
      @PathVariable final String defiName
  ) {
    return this.defiQueryService.getDefiById(defiName);
  }

  @GetMapping("/{defiName}/commodities")
  @ResponseStatus(value = HttpStatus.OK)
  public List<CommodityDto> getAllCommodities(
      @PathVariable final String defiName
  ) {
    return this.defiQueryService.getAllCommodities(defiName);
  }
}

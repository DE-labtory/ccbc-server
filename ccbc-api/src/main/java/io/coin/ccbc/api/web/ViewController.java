package io.coin.ccbc.api.web;

import io.coin.ccbc.api.application.SwapApplicationService;
import io.coin.ccbc.api.application.dto.SwapInformationRequestParams;
import io.coin.ccbc.api.application.dto.SwapViewDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bomi
 * @date 2021/08/18
 */

@RestController
@RequestMapping("api/v1/view")
public class ViewController {

  private final SwapApplicationService swapApplicationService;

  public ViewController(final SwapApplicationService swapApplicationService) {
    this.swapApplicationService = swapApplicationService;
  }

  @GetMapping("/swap/swap-from")
  @ResponseStatus(value = HttpStatus.OK)
  public SwapViewDto getSwapInformationWithFrom(
      @ModelAttribute SwapInformationRequestParams requestParams
  ) {
    return this.swapApplicationService.getSwapInformationWithFrom(requestParams);
  }

  @GetMapping("/swap/swap-to")
  @ResponseStatus(value = HttpStatus.OK)
  public SwapViewDto getSwapInformationWithTo(
      @ModelAttribute SwapInformationRequestParams requestParams
  ) {
    return this.swapApplicationService.getSwapInformationWithTo(requestParams);
  }

}

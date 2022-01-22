package io.coin.ccbc.api.web;

import io.coin.ccbc.api.application.ContractApplicationService;
import io.coin.ccbc.api.application.dto.ApproveCallDataRequestParams;
import io.coin.ccbc.api.application.dto.ContractCallDataDto;
import io.coin.ccbc.api.application.dto.SwapCallDataRequestParams;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/contracts")
public class ContractController {

  private final ContractApplicationService contractApplicationService;

  public ContractController(ContractApplicationService contractApplicationService) {
    this.contractApplicationService = contractApplicationService;
  }

  @GetMapping("/call-data/approve")
  @ResponseStatus(value = HttpStatus.OK)
  public ContractCallDataDto getApproveCallData(
      @ModelAttribute ApproveCallDataRequestParams requestParams
  ) {
    return contractApplicationService.getApproveCallData(requestParams);
  }

  @GetMapping("/call-data/swap")
  @ResponseStatus(value = HttpStatus.OK)
  public ContractCallDataDto getSwapCallData(
      @ModelAttribute SwapCallDataRequestParams requestParams
  ) {
    return contractApplicationService.getSwapCallData(requestParams);
  }
}

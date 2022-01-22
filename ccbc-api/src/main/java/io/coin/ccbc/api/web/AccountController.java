package io.coin.ccbc.api.web;

import io.coin.ccbc.api.application.AccountApplicationService;
import io.coin.ccbc.api.application.dto.CreateAccountRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bomi
 * @date 2021/07/29
 */

@RequestMapping("api/v1/accounts")
@RestController
public class AccountController {

  private final AccountApplicationService accountApplicationService;

  public AccountController(final AccountApplicationService accountApplicationService) {
    this.accountApplicationService = accountApplicationService;
  }

  @PostMapping("")
  @ResponseStatus(value = HttpStatus.CREATED)
  public void createAccount(@RequestBody CreateAccountRequest request) {
    this.accountApplicationService.create(request.getAddress());
  }
}

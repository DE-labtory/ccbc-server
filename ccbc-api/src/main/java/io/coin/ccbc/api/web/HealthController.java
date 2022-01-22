package io.coin.ccbc.api.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HealthController {

  @GetMapping("/healthz")
  @ResponseStatus(value = HttpStatus.OK)
  public void healthz() {
  }
}

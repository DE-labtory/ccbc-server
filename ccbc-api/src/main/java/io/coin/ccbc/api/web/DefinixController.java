package io.coin.ccbc.api.web;

import io.coin.ccbc.api.application.dto.CreateDefinixFarmRequest;
import io.coin.ccbc.api.application.dto.CreateKlayswapPoolRequest;
import io.coin.ccbc.definix.DefinixFarm;
import io.coin.ccbc.definix.DefinixFarmRepository;
import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.CoinRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/operation/definix")
public class DefinixController {

  private final DefinixFarmRepository definixFarmRepository;
  private final CoinRepository coinRepository;

  public DefinixController(
      DefinixFarmRepository definixFarmRepository,
      CoinRepository coinRepository
  ) {
    this.definixFarmRepository = definixFarmRepository;
    this.coinRepository = coinRepository;
  }

  @PostMapping
  @ResponseStatus(value = HttpStatus.CREATED)
  public void creatFarm(
      @RequestParam(name = "clientId") String clientId,
      @RequestBody CreateDefinixFarmRequest request
  ) {
    this.definixFarmRepository.findByAddress(Address.of(request.getAddress())).ifPresent(
        pool -> {
          throw new IllegalArgumentException(
              String.format("%s is already exist", pool.getAddress().getValue())
          );
        });

    Coin coin0 = this.coinRepository.findByIdOrElseThrow(request.getCoin0Id());
    Coin coin1 = this.coinRepository.findByIdOrElseThrow(request.getCoin1Id());

    definixFarmRepository.save(
        new DefinixFarm(
            Address.of(request.getAddress()),
            18,
            request.getPid(),
            coin0,
            coin1
        )
    );
  }
}

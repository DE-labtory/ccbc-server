package io.coin.ccbc.api.web;

import io.coin.ccbc.api.application.dto.CreateDefinixFarmRequest;
import io.coin.ccbc.api.application.dto.CreateDexPairRequest;
import io.coin.ccbc.definix.DefinixFarm;
import io.coin.ccbc.definix.DefinixFarmRepository;
import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.CoinRepository;
import io.coin.ccbc.domain.DexPair;
import io.coin.ccbc.domain.DexPairRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/operation/dex")
public class DexController {

  private final DexPairRepository dexPairRepository;
  private final CoinRepository coinRepository;

  public DexController(
      DexPairRepository dexPairRepository,
      CoinRepository coinRepository
  ) {
    this.dexPairRepository = dexPairRepository;
    this.coinRepository = coinRepository;
  }

  @PostMapping("/pair")
  @ResponseStatus(value = HttpStatus.CREATED)
  public void creatDexPair(
      @RequestParam(name = "clientId") String clientId,
      @RequestBody CreateDexPairRequest request
  ) {
    this.dexPairRepository.findByAddress(Address.of(request.getAddress())).ifPresent(
        pool -> {
          throw new IllegalArgumentException(
              String.format("%s is already exist", pool.getAddress().getValue())
          );
        });

    Coin coin0 = this.coinRepository.findByIdOrElseThrow(request.getCoin0Id());
    Coin coin1 = this.coinRepository.findByIdOrElseThrow(request.getCoin1Id());

    dexPairRepository.save(
        new DexPair(
            Address.of(request.getAddress()),
            18,
            coin0,
            coin1,
            request.getType()
        )
    );
  }
}

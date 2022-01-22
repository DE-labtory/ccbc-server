package io.coin.ccbc.api.web;

import io.coin.ccbc.api.application.dto.CreateKlayfiVaultRequest;
import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.CoinRepository;
import io.coin.ccbc.klayfi.domain.KlayfiCommodity;
import io.coin.ccbc.klayfi.domain.KlayfiCommodityRepository;
import io.coin.ccbc.klayswap.KlayswapPool;
import io.coin.ccbc.klayswap.KlayswapPoolRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/operation/klayfi")
public class KlayfiController {

  private final KlayswapPoolRepository klayswapPoolRepository;
  private final KlayfiCommodityRepository klayfiCommodityRepository;
  private final CoinRepository coinRepository;

  public KlayfiController(
      KlayswapPoolRepository klayswapPoolRepository,
      KlayfiCommodityRepository klayfiCommodityRepository,
      CoinRepository coinRepository
  ) {
    this.klayswapPoolRepository = klayswapPoolRepository;
    this.klayfiCommodityRepository = klayfiCommodityRepository;
    this.coinRepository = coinRepository;
  }

  @PostMapping
  @ResponseStatus(value = HttpStatus.CREATED)
  public void createVault(
      @RequestParam(name = "clientId") String clientId,
      @RequestBody CreateKlayfiVaultRequest request
  ) {
    KlayswapPool pool = this.klayswapPoolRepository.findByAddress(Address.of(request.getPoolAddress()))
        .orElseThrow(() -> new IllegalArgumentException(
            String.format("pool '%s' does not exist", request.getPoolAddress())
        ));

    this.klayfiCommodityRepository.findByAddress(Address.of(request.getVaultAddress())).ifPresent(
        klayfiCommodity -> {
          throw new IllegalArgumentException(String.format("'%s' is already exist", klayfiCommodity.getAddress()));
        }
    );

    KlayfiCommodity.Type type = KlayfiCommodity.Type.of(request.getType());
    Coin kfi = this.coinRepository.findBySymbolOrElseThrow("KFI");

    this.klayfiCommodityRepository.save(
        new KlayfiCommodity(
            pool,
            kfi,
            Address.of(request.getVaultAddress()),
            18,
            type
        )
    );
  }
}

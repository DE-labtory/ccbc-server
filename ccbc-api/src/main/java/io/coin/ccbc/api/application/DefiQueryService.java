package io.coin.ccbc.api.application;

import io.coin.ccbc.api.application.dto.CommodityDto;
import io.coin.ccbc.api.application.dto.DefiDto;
import io.coin.ccbc.domain.Defi;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class DefiQueryService {

  private final List<Defi> defis;
  private final Executor executor;

  public DefiQueryService(List<Defi> defis) {
    this.defis = defis;
    this.executor = Executors.newFixedThreadPool(defis.size());
  }

  public List<DefiDto> getAllDefis() {
    List<CompletableFuture<DefiDto>> completableFutures = this.defis
        .stream()
        .map(defi -> CompletableFuture.supplyAsync(() -> DefiDto.from(defi), this.executor))
        .collect(Collectors.toList());

    return completableFutures.stream()
        .map(CompletableFuture::join)
        .collect(Collectors.toList());
  }

  public DefiDto getDefiById(String defiName) {
    return this.defis
        .stream()
        .filter(defi -> defi.getProtocolName().equalsIgnoreCase(defiName))
        .findFirst()
        .map(DefiDto::from)
        .orElseThrow(() -> {
          throw new IllegalArgumentException(String.format("defi '%s' does not exist", defiName));
        });
  }

  public List<CommodityDto> getAllCommodities(String defiName) {
    return this.defis
        .stream()
        .filter(defi -> defi.getProtocolName().equalsIgnoreCase(defiName))
        .findFirst()
        .orElseThrow(() -> {
          throw new IllegalArgumentException(String.format("defi '%s' does not exist", defiName));
        })
        .getAllCommodities()
        .stream()
        .map(CommodityDto::from)
        .collect(Collectors.toList());
  }
}

package io.coin.ccbc.klayfi.infra;

import io.coin.ccbc.klayfi.domain.KlayfiCommodityInfo;
import io.coin.ccbc.klayfi.domain.KlayfiCrawler;
import io.coin.ccbc.support.CacheProvider;
import io.coin.ccbc.support.CacheValueProvider;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.cache.annotation.Cacheable;

@Slf4j
public class SeleniumKlayfiCrawler implements KlayfiCrawler, CacheProvider {

  private final WebDriver browser;
  private final ForkJoinPool forkJoinPool;

  public SeleniumKlayfiCrawler(long loadingDelay) throws InterruptedException {
    forkJoinPool = new ForkJoinPool(30);
    WebDriverManager.chromedriver().setup();
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless");
    options.addArguments("--window-size=1920,1080");
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
    this.browser = new ChromeDriver(options);
    browser.get("https://klayfi.finance");
    Thread.sleep(loadingDelay);
  }

  @Override
  @Cacheable(value = "klayfi_aprs")
  public List<KlayfiCommodityInfo> getAllAprs() {
    return refreshableGetAllAprs();
  }

  private List<KlayfiCommodityInfo> refreshableGetAllAprs() {
    List<WebElement> titleElements = this.browser.findElements(By.className("PoolListItem__title"));
    List<WebElement> aprElements = this.browser.findElements(By.className("PoolListItem__apr"));
    List<WebElement> tvlElements = this.browser.findElements(By.className("PoolListItem__tvl"));
    if (titleElements.size() != aprElements.size()) {
      return this.getFallbackAprs();
    }

    List<CompletableFuture<KlayfiCommodityInfo>> completableFutures = IntStream.range(0,
            titleElements.size())
        .mapToObj(index -> CompletableFuture.supplyAsync(() -> {
          WebElement titleElement = titleElements.get(index);
          WebElement aprTextElement = aprElements.get(index);
          WebElement tvlTextElement = tvlElements.get(index);
          return new PackedInfo(titleElement, aprTextElement,
              tvlTextElement).toKlayfiCommodityInfo();
        }, forkJoinPool))
        .collect(Collectors.toList());

    List<KlayfiCommodityInfo> klayfiCommodityInfos = completableFutures.stream()
        .map(CompletableFuture::join)
        .collect(Collectors.toList());
    return klayfiCommodityInfos;
  }

  private List<KlayfiCommodityInfo> getFallbackAprs() {
    return browser.findElements(By.className("PoolListItem"))
        .stream()
        .map(item -> {
          WebElement tileAndTvlElement = item
              .findElement(By.className("PoolListItem__titleAndTVL"));
          WebElement titleElement = tileAndTvlElement.findElement(
              By.className("PoolListItem__title"));
          WebElement aprTextElement = item.findElement(By.className("PoolListItem__apyAndApr"))
              .findElement(By.className("PoolListItem__apr"));
          WebElement tvlTextElement = tileAndTvlElement.findElement(
              By.className("PoolListItem__tvl"));
          return new PackedInfo(titleElement, aprTextElement,
              tvlTextElement).toKlayfiCommodityInfo();
        })
        .collect(Collectors.toList());
  }

  @Override
  @Cacheable(value = "klayfi_tvl")
  public double getTvl() {
    return refreshableGetTvl();
  }

  private double refreshableGetTvl() {
    return Double.parseDouble(
        this.browser.findElement(By.className("TVL__value")).getText().substring(1)
            .replace(",", ""));
  }

  @Override
  public void close() {
    WebDriverManager.chromedriver().quit();
    this.browser.close();
  }

  @Override
  public List<CacheInformation> getCacheInformation() {
    return List.of(
        new CacheInformation() {
          @Override
          public CacheValueProvider getCacheValueProvider() {
            return key -> refreshableGetTvl();
          }

          @Override
          public String getCacheName() {
            return "klayfi_tvl";
          }

          @Override
          public Duration refreshTime() {
            return Duration.ofSeconds(10);
          }
        },
        new CacheInformation() {
          @Override
          public CacheValueProvider getCacheValueProvider() {
            return key -> refreshableGetAllAprs();
          }

          @Override
          public String getCacheName() {
            return "klayfi_aprs";
          }

          @Override
          public Duration refreshTime() {
            return Duration.ofSeconds(60);
          }
        }
    );
  }

  private static class PackedInfo {

    private String coin0Symbol;
    private String coin1Symbol;
    private double apr;
    private double tvl;

    public PackedInfo(WebElement titleElement, WebElement aprElement, WebElement tvlElement) {
      List<CompletableFuture<String>> completableFutures = Arrays.asList(
          CompletableFuture.supplyAsync(titleElement::getText),
          CompletableFuture.supplyAsync(aprElement::getText),
          CompletableFuture.supplyAsync(tvlElement::getText)
      );

      List<String> strings = completableFutures.stream()
          .map(CompletableFuture::join)
          .collect(Collectors.toList());

      List<String> symbols = extractSymbols(strings.get(0));
      Double apr = extractApr(strings.get(1));
      Double tvl = extractTvl(strings.get(2));
      String coin0Symbol = symbols.get(0);
      String coin1Symbol = symbols.size() == 1
          ? null
          : symbols.get(1);
      this.coin0Symbol = coin0Symbol;
      this.coin1Symbol = coin1Symbol;
      this.apr = apr;
      this.tvl = tvl;
    }

    public KlayfiCommodityInfo toKlayfiCommodityInfo() {
      if (this.coin1Symbol == null) {
        return KlayfiCommodityInfo.Staking(this.coin0Symbol, this.apr, this.tvl);
      }

      return KlayfiCommodityInfo.Vault(
          this.coin0Symbol,
          this.coin1Symbol,
          this.apr,
          this.tvl
      );
    }

    /*
    example:
    KFI Staking Pool
    Symbol-Symbol LP
     */
    private static List<String> extractSymbols(String title) {
      if (title.contains("-")) {
        return Arrays.asList(title.split(" ")[0].split("-"));
      }

      return Collections.singletonList(title.split(" ")[0]);
    }

    /*
    example:
    TVL $10,414,749.8
    */
    private static Double extractTvl(String tvlText) {
      return Double.parseDouble(tvlText.split(" ")[1].replace("$", "").replace(",", ""));
    }

    /*
    example:
    APR 10%
     */
    private static Double extractApr(String aprText) {
      return Arrays.stream(
              aprText.split(" ")[1].replace("\n", "").replace("%", "").replace(",", "").split("\\+"))
          .map(Double::parseDouble)
          .reduce(Double::sum)
          .orElse(0.0);
    }
  }
}

package io.coin.ccbc.kai.infra.selenium;

import io.coin.ccbc.kai.infra.KaiProtocolCrawler;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

@Slf4j
public class SeleniumKaiProtocolCrawler implements KaiProtocolCrawler {

  private final WebDriver browser;

  public SeleniumKaiProtocolCrawler(long loadingDelay) throws InterruptedException {
    WebDriverManager.chromedriver().setup();
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless");
    options.addArguments("--window-size=1920,1080");
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
    this.browser = new ChromeDriver(options);
    browser.get("https://kaiprotocol.fi");
    Thread.sleep(loadingDelay);
  }

  @Override
  public double getApr() {
    try {
      WebElement totalInfoItem = browser.findElement(By.className("total-info-item"));
      WebElement apr = totalInfoItem.findElement(By.tagName("dd"));
      return Double.parseDouble(apr.getText().substring(0, apr.getText().length() - 1));
    } catch (Exception e) {
      SeleniumKaiProtocolCrawler.log.error("failed to crawl kai-protocol apr", e);
      return 0;
    }
  }

  @Override
  public double getTotalTvl() {
    try {
      WebElement summaryItem = browser.findElement(By.className("summary-item"));
      WebElement totalTvl = summaryItem.findElement(By.className("value"))
          .findElement(By.tagName("strong"));
      return Double.parseDouble(totalTvl.getText().replace("$", "").replace(",", ""));
    } catch (Exception e) {
      SeleniumKaiProtocolCrawler.log.error("failed to crawl kai-protocol total tvl", e);
      return 0;
    }
  }

  @Override
  public double getTvl() {
    try {
      WebElement infoItem = browser.findElement(By.className("info-item"));
      WebElement tvl = infoItem.findElement(By.tagName("dd"));
      String tvlText = tvl.getText().replace("$", "");
      String unit = tvlText.substring(tvlText.length() - 1);
      double value = Double.parseDouble(tvlText.substring(0, tvlText.length() - 1));
      switch (unit) {
        case "k":
          return value * 1000;
        case "M":
          return value * 1000000;
        case "B":
          return value * 1000000000;
        default:
          throw new IllegalStateException(String.format("can not handle %s unit", unit));
      }
    } catch (Exception e) {
      SeleniumKaiProtocolCrawler.log.error("failed to crawl kai-protocol staking tvl", e);
      return 0;
    }
  }


  public void close() {
    WebDriverManager.chromedriver().quit();
    this.browser.close();
  }
}

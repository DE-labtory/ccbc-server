package io.coin.ccbc.kronos.infra.selenium;

import io.coin.ccbc.kronos.infra.KronosCrawler;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

@Slf4j
public class SeleniumKronosCrawler implements KronosCrawler {
    private final WebDriver browser;

    public SeleniumKronosCrawler(long loadingDelay) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        this.browser = new ChromeDriver(options);
        browser.get("https://app.kronosdao.finance/#/stake");
        Thread.sleep(loadingDelay);
    }

    @Override
    public double getApy() {
        try {
            WebElement stakeCardApy = browser.findElement(By.className("stake-card-apy"));
            String apy = stakeCardApy
                    .findElement(By.className("stake-card-metrics-value"))
                    .getText()
                    .replace(",", "");
            return Double.parseDouble(apy.substring(0, apy.length() - 1));
        } catch (Exception e) {
            SeleniumKronosCrawler.log.error("failed to crawl kronos apy", e);
            return 0;
        }
    }

    @Override
    public double getTotalTvl() {
        try {
            WebElement stakeCardTvl = browser.findElement(By.className("stake-card-tvl"));
            WebElement totalTvl = stakeCardTvl.findElement(By.className("stake-card-metrics-value"));
            return Double.parseDouble(totalTvl.getText().replace("$", "").replace(",", ""));
        } catch (Exception e) {
            SeleniumKronosCrawler.log.error("failed to crawl kronos total tvl", e);
            return 0;
        }
    }

    public void close() {
        WebDriverManager.chromedriver().quit();
        this.browser.close();
    }
}

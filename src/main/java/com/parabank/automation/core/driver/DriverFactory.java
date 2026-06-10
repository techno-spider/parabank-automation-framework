package com.parabank.automation.core.driver;

import com.parabank.automation.utils.LoggingUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * Factory class responsible for creating WebDriver instances.
 * Each browser gets its own options configuration.
 */
public final class DriverFactory {
    private static final LoggingUtil log = LoggingUtil.getLogger(DriverFactory.class);

    private DriverFactory() {}

    public static WebDriver createDriver(BrowserType browserType,
                                         boolean headless,
                                         int implicitWait,
                                         int pageLoadTimeout) {
        log.info("Creating WebDriver: browser={}, headless={}", browserType.getValue(), headless);

        WebDriver driver = switch (browserType) {
            case CHROME -> createChromeDriver(headless);
            case FIREFOX -> createFirefoxDriver(headless);
            case EDGE -> createEdgeDriver(headless);
        };
        configureTimeouts(driver, implicitWait, pageLoadTimeout);
        log.info("WebDriver created successfully");
        return driver;
    }

    private static WebDriver createChromeDriver(boolean headless) {
        WebDriverManager.chromedriver()
                        .setup();
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver(boolean headless) {
        WebDriverManager.firefoxdriver()
                        .setup();
        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments("--headless");
        }
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        return new FirefoxDriver(options);
    }

    private static WebDriver createEdgeDriver(boolean headless) {
        WebDriverManager.edgedriver()
                        .setup();
        EdgeOptions options = new EdgeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--window-size=1920,1080");
        return new EdgeDriver(options);
    }

    private static void configureTimeouts(WebDriver driver, int implicitWait, int pageLoadTimeout) {
        driver.manage()
              .timeouts()
              .implicitlyWait(Duration.ofSeconds(implicitWait));
        driver.manage()
              .timeouts()
              .pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
        driver.manage()
              .window()
              .maximize();
    }
}

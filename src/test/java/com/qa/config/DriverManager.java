package com.qa.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DriverManager {

    private static final Logger log = LogManager.getLogger(DriverManager.class);

    //One webdriver per thread for parallel execution
    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
    private static final ConfigReader config = ConfigReader.getInstance();

    private DriverManager() {}

    /**
     * Initialise driver using browser and grid URL from config files.
     * Called in Hooks.java @Before.
     */
    public static void initDriver() {
        String browser = config.getBrowser();
        initDriver(browser);
    }

    /**
     * Initialise driver with a specific browser.
     * The grid URL is still read from config/Maven profile.
     */
    public static void initDriver(String browser) {
        if (config.isGridExecution()) {
            log.info("Starting REMOTE driver | browser={} | grid={} | thread={}",
                    browser, config.getGridUrl(), Thread.currentThread().getId());
            driverThread.set(createRemoteDriver(browser, config.getGridUrl()));
        } else {
            log.info("Starting LOCAL driver | browser={} | thread={}",
                    browser, Thread.currentThread().getId());
            driverThread.set(createLocalDriver(browser));
        }
        configureTimeouts();
    }

    /**
     * Get the WebDriver for the current thread.
     * Throws a clear error if called before initDriver().
     */
    public static WebDriver getDriver() {
        WebDriver driver = driverThread.get();
        if (driver == null) {
            throw new IllegalStateException(
                    "WebDriver is null for thread " + Thread.currentThread().getId() +
                            " — make sure initDriver() is called in your @Before hook."
            );
        }
        return driver;
    }

    /**
     * Quit the driver and remove from ThreadLocal.
     * CRITICAL — forgetting this causes memory leaks in parallel runs.
     */
    public static void quitDriver() {
        WebDriver driver = driverThread.get();
        if (driver != null) {
            log.info("Quitting driver | thread={}", Thread.currentThread().getId());
            try {
                driver.quit();
            } catch (Exception e) {
                log.warn("Exception while quitting driver: {}", e.getMessage());
            } finally {
                // Always remove from ThreadLocal even if quit() throws
                driverThread.remove();
            }
        }
    }

    private static WebDriver createLocalDriver(String browser) {
        return switch (browser.toLowerCase().trim()) {
            case "chrome" -> {
                WebDriverManager.chromedriver().setup();
                yield new ChromeDriver(getChromeOptions());
            }
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                yield new FirefoxDriver(getFirefoxOptions());
            }
            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                yield new EdgeDriver(getEdgeOptions());
            }
            default -> throw new IllegalArgumentException(
                    "Browser not supported: '" + browser + "' — use chrome, firefox, or edge"
            );
        };
    }

    private static WebDriver createRemoteDriver(String browser, String gridUrl) {
        try {
            URL url = new URL(gridUrl);
            return switch (browser.toLowerCase().trim()) {
                case "chrome"  -> new RemoteWebDriver(url, getChromeOptions());
                case "firefox" -> new RemoteWebDriver(url, getFirefoxOptions());
                case "edge"    -> new RemoteWebDriver(url, getEdgeOptions());
                default -> throw new IllegalArgumentException(
                        "Browser not supported: '" + browser + "'"
                );
            };
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Grid URL: " + gridUrl, e);
        }
    }

    // ── Browser Options ─────────────────────────────────────

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--start-maximized",
                "--disable-extensions",
                "--disable-popup-blocking",
                "--disable-infobars",
                "--no-sandbox",            // Required inside Docker
                "--disable-dev-shm-usage", // Prevents Chrome crashes in Docker
                "--remote-allow-origins=*"
        );
        if (isHeadless()) {
            options.addArguments("--headless=new");
            log.info("Chrome running headless");
        }
        return options;
    }

    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--width=1920", "--height=1080");
        if (isHeadless()) {
            options.addArguments("-headless");
        }
        return options;
    }

    private static EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments(
                "--start-maximized",
                "--no-sandbox",
                "--disable-dev-shm-usage"
        );
        return options;
    }

    private static boolean isHeadless() {
        // Enable via Maven: -Dheadless=true
        return Boolean.parseBoolean(System.getProperty("headless", "false"));
    }

    // ── Timeouts ────────────────────────────────────────────

    private static void configureTimeouts() {
        WebDriver driver = driverThread.get();
        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(config.getExplicitWait()));
        driver.manage().timeouts()
                .pageLoadTimeout(Duration.ofSeconds(config.getPageLoadTimeout()));
        log.debug("Timeouts set | implicit={}s | pageLoad={}s",
                config.getExplicitWait(), config.getPageLoadTimeout());
    }


}

package com.qa.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ConfigReader {

    private static final Logger log = LogManager.getLogger(String.valueOf(ConfigReader.class));
    private static ConfigReader instance;
    private final Properties properties = new Properties();

    // ── Private constructor — called once only ──────────────

    private ConfigReader() {
        loadProperties();
    }

    // ── Singleton access point ──────────────────────────────

    public static ConfigReader getInstance(){
        if(instance == null){
            synchronized (ConfigReader.class){
                if (instance == null){
                    instance = new ConfigReader();
                }
            }
        }
        return instance;
    }

    // ── Public Getters ──────────────────────────────────────

    public String getBaseUrl(){
        return get("base.url");
    }

    public String getBrowser(){
        return System.getProperty("browser", get("browser"));
    }

    public String getGridUrl() {
        return System.getProperty("grid.url", get("grid.url"));
    }

    public boolean isGridExecution() {
        String gridUrl = getGridUrl();
        return gridUrl != null && !gridUrl.equalsIgnoreCase("local");
    }

    public String getEnvironment() {
        return System.getProperty("env", get("env"));
    }

    public int getExplicitWait() {
        return Integer.parseInt(get("explicit.wait"));
    }

    public int getPageLoadTimeout() {
        return Integer.parseInt(get("page.load.timeout"));
    }

    public String getScreenshotPath() {
        return get("screenshot.path");
    }

    public String getSlackWebhookUrl() {
        return get("slack.webhook.url");
    }

    public String getApiBaseUrl() {
        return get("api.base.url");
    }

    public String getApiKey() {
        return get("api.key");
    }

    public String get(String key){
        String value = System.getProperty(key, properties.getProperty(key));
        if (value == null){
            log.warn("Config key not found: please check your property files", key);
        }
        return value;
    }

    //Private load logic ---------------

    private void loadProperties(){
        //Load defaults first
        loadFile("config/default.properties");

        //Load environment specific overrides

        String env = System.getProperty("env","stagging");
        loadFile("config/" + env + ".properties");

        log.info("Config loaded | env={}", env);

    }
    private void loadFile(String filePath) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(filePath)) {
            if (is != null) {
                properties.load(is);
                log.debug("Loaded: {}", filePath);
            } else {
                log.warn("File not found on classpath: {} — skipping", filePath);
            }
        } catch (IOException e) {
            log.error("Failed to load {}: {}", filePath, e.getMessage());
        }
    }

}

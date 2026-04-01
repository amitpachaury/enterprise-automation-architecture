package com.qa.stepdefs;

import com.qa.config.ConfigReader;
import com.qa.config.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {
    private static final Logger log = LogManager.getLogger(Hooks.class);
    private final ConfigReader config = ConfigReader.getInstance();



    @Before
    public void setUp(Scenario scenario){
        log.info("Starting scenario: {}", scenario.getName());
        log.info("Tags: {}", scenario.getSourceTagNames());
        if (!scenario.getSourceTagNames().contains("@api")) {
            DriverManager.initDriver();
        }
    }
    @After
    public void tearDown(Scenario scenario){
        if (scenario.isFailed()) {
            // Only take screenshot if driver was initialised
            if (!scenario.getSourceTagNames().contains("@api")) {
                byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver())
                        .getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", scenario.getName());
                log.warn("Scenario FAILED — screenshot attached: {}", scenario.getName());
            }
        }
        log.info("Scenario status: {}", scenario.getStatus());
        log.info("Scenario status: {}", scenario.getStatus());

        if (!scenario.getSourceTagNames().contains("@api")) {
            DriverManager.quitDriver();
        }

    }
}

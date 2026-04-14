package com.qa.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;


@CucumberOptions
        (
                features = "src/test/resources/features",
                glue = {"com.qa.stepdefs", "com.qa.stepdefs.api"},
                tags = "@ui",
                plugin = {
                        "pretty",
                        "html:target/cucumber-reports/parallel-report.html",
                        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
                },
                monochrome = true
        )
public class ParallelRunner extends AbstractTestNGCucumberTests {
    public static final Logger log = LogManager.getLogger(ParallelRunner.class);

    @Parameters("browser")
    @BeforeClass(alwaysRun = true)
    public void setBrowser(@Optional("chrome") String browser) {
        System.setProperty("browser", browser);
        log.info("Running on Browser: " + browser);
    }
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}

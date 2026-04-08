package com.qa.retry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private static final Logger log =   LogManager.getLogger(RetryAnalyzer.class);
    private int count = 0;
    private static  final int maxAttempts = 2;

    @Override
    public boolean retry(ITestResult result) {
        if (count < maxAttempts) {
            count++;
            log.warn("Retrying test: {} | Attempt: {}/{}",
                    result.getName(), count, maxAttempts);
            return true;
        } else  {
            return false;
        }
    }

}

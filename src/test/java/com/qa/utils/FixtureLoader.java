package com.qa.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.io.InputStream;

public class FixtureLoader {
    private static final Logger log = LogManager.getLogger(FixtureLoader.class);
    private static final String FIXTURE_PATH = "fixtures/";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String loadAsString(String filePath){
        String fullPath = FIXTURE_PATH + filePath;
        log.info("Loading fixture: {}", fullPath);

        InputStream is = FixtureLoader.class.getClassLoader().getResourceAsStream(fullPath);
        if (is == null) {
            throw new RuntimeException("Fixture file not found: " + fullPath);
        }
        try {
            return new String(is.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read fixture: " + fullPath, e);
        }

    }

    public static <T> T loadAs(String filePath, Class<T> clazz){
        String fullPath = FIXTURE_PATH + filePath;
        log.info("Loading fixture as {}: {}", clazz.getSimpleName(), fullPath);

        InputStream is = FixtureLoader.class.getClassLoader().getResourceAsStream(fullPath);

            if (is == null) {
                throw new RuntimeException("Fixture file not found: " + fullPath);
            }
        try {
            return objectMapper.readValue(is, clazz);
        }catch (IOException e) {
            throw new RuntimeException("Failed to map fixture to "
                    + clazz.getSimpleName() + ": " + fullPath, e);
        }
    }
}

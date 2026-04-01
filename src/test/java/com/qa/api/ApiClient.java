package com.qa.api;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ApiClient {
    private static final Logger log = LogManager.getLogger(ApiClient.class);

    public static Response get(String endpoint){
        log.info("GET → {}", endpoint);
        return ApiRequestBuilder.buildRequestSpec()
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }
    public static Response post(String endpoint, Object body){
        log.info("POST → {}", endpoint);
        return ApiRequestBuilder.buildRequestSpec()
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }
    public static Response put(String endpoint, Object body){
        log.info("PUT → {}", endpoint);
        return ApiRequestBuilder.buildRequestSpec()
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .extract()
                .response();
    }
    public static Response delete(String endpoint){
        log.info("DELETE → {}", endpoint);
        return ApiRequestBuilder.buildRequestSpec()
                .when()
                .delete(endpoint)
                .then()
                .extract()
                .response();
    }
    public static Response patch(String endpoint, Object body){
        log.info("PATCH → {}", endpoint);
        return ApiRequestBuilder.buildRequestSpec()
                .body(body)
                .when()
                .patch(endpoint)
                .then()
                .extract()
                .response();
    }

}

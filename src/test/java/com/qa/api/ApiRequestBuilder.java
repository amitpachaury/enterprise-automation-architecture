package com.qa.api;

import com.qa.config.ConfigReader;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class ApiRequestBuilder {

    private static final ConfigReader config =  ConfigReader.getInstance();

    public static RequestSpecification buildRequestSpec() {

        return RestAssured.given()
                .spec(new RequestSpecBuilder()
                        .setBaseUri(config.getApiBaseUrl())
                        .setContentType(ContentType.JSON)
                        .addHeader("Accept", "application/json")
                        .addHeader("x-api-key", config.getApiKey())
                        .addFilter(new AllureRestAssured())
                        .addFilter(new RequestLoggingFilter())
                        .addFilter((new ResponseLoggingFilter()))
                        .build()
                );


    }

}

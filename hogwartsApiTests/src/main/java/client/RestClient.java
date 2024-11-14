package client;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import static config.ConfigUtils.cfg;
import static io.restassured.RestAssured.given;

public abstract class RestClient {
    protected final String baseUrl = cfg.BaseUrl();
    public RequestSpecification request() {
        return  given().spec(new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .addFilter(new AllureRestAssured())
                .addFilter(new ResponseLoggingFilter())
                .build())
                .log().all();
    }
}

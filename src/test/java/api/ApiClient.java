package api;

import config.Config;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class ApiClient {

    private static final RequestSpecification BASE_SPEC = new RequestSpecBuilder()
            .setBaseUri(Config.API_URL)
            .addFilter(new AllureRestAssured())
            .log(LogDetail.URI)
            .build();

    protected RequestSpecification request() {
        return given().spec(BASE_SPEC);
    }

    protected RequestSpecification authorized(String token) {
        return request().header("Authorization", "Bearer " + token);
    }
}

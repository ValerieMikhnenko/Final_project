package api;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.User;

import java.util.Map;

public class UserApi extends ApiClient {

    @Step("API: зарегистрировать пользователя {user.email}")
    public User register(User user) {
        Response response = request()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "email", user.getEmail(),
                        "password", user.getPassword(),
                        "submitPassword", user.getPassword()))
                .post("/signup");

        response.then().statusCode(201);
        user.setToken(response.path("access_token.access_token"));
        user.setId(response.path("user.id"));
        return user;
    }

    @Step("API: войти пользователем {user.email}")
    public String login(User user) {
        return request()
                .contentType(ContentType.JSON)
                .body(Map.of("email", user.getEmail(), "password", user.getPassword()))
                .post("/signin")
                .then()
                .statusCode(201)
                .extract()
                .path("token.access_token");
    }
}

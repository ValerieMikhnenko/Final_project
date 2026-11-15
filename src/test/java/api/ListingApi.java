package api;

import io.qameta.allure.Step;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.specification.MultiPartSpecification;
import models.Listing;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class ListingApi extends ApiClient {

    @Step("API: создать объявление «{listing.name}»")
    public Listing create(String token, Listing listing) {
        Integer id = authorized(token)
                .contentType("multipart/form-data")
                .multiPart(part("name", listing.getName()))
                .multiPart(part("category", listing.getCategory()))
                .multiPart(part("condition", listing.getCondition()))
                .multiPart(part("city", listing.getCity()))
                .multiPart(part("description", listing.getDescription()))
                .multiPart(part("price", String.valueOf(listing.getPrice())))
                .post("/create-listing")
                .then()
                .statusCode(201)
                .extract()
                .path("id");
        listing.setId(id);
        return listing;
    }

    @Step("API: получить названия своих объявлений")
    public List<String> ownListingNames(String token) {
        return authorized(token)
                .get("/profile/listings/1")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("offers.name", String.class);
    }

    @Step("API: удалить объявление {id}")
    public void delete(String token, int id) {
        authorized(token).delete("/listings/" + id);
    }

    @Step("API: удалить все объявления пользователя")
    public void deleteAll(String token) {
        List<Integer> ids = authorized(token)
                .get("/profile/listings/1")
                .then()
                .extract()
                .jsonPath()
                .getList("offers.id", Integer.class);
        if (ids != null) {
            ids.forEach(id -> delete(token, id));
        }
    }

    private static MultiPartSpecification part(String name, String value) {
        return new MultiPartSpecBuilder(value)
                .controlName(name)
                .charset(StandardCharsets.UTF_8)
                .build();
    }
}

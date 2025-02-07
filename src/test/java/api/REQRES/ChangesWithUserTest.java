package api.REQRES;

import models.changesWithUser.RequestChangesBody;
import models.changesWithUser.ResponseCreateBody;
import models.changesWithUser.ResponseUpdateBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.ChangesWithUserSpec.*;


@Tag("api_test")
public class ChangesWithUserTest extends BaseTest {

    RequestChangesBody requestBody = new RequestChangesBody();

    @Tag("smoke_test")
    @Test
    @DisplayName("Проверка создания пользователя")
    void createUserTest() {
        requestBody.setName("morpheus");
        requestBody.setJob("leader");

        ResponseCreateBody responseBody = step("make request", () ->
                given()
                        .spec(changesUserBodyRequestSpec)
                        .body(requestBody)
                        .when()
                        .post("/users")
                        .then()
                        .spec(changesUserResponse201Spec)
                        .extract().as(ResponseCreateBody.class));

        step("check response", () -> {
            Assertions.assertEquals("morpheus", responseBody.getName());
            Assertions.assertEquals("leader", responseBody.getJob());
        });
    }


    @Test
    @DisplayName("Проверка возможности создания пользователя с пустыми полями")
    void createEmptyUserTest() {
        requestBody.setName("");
        requestBody.setJob("");

        ResponseCreateBody responseBody = step("make request", () ->
                given()
                        .spec(changesUserBodyRequestSpec)
                        .body(requestBody)
                        .when()
                        .post("/users")
                        .then()
                        .spec(changesUserResponse201Spec)
                        .extract().as(ResponseCreateBody.class));

        step("check response", () -> {
            Assertions.assertEquals("", responseBody.getName());
            Assertions.assertEquals("", responseBody.getJob());
        });
    }


    @Test
    @DisplayName("Проверка изменения поля job у существующего пользователя")
    void updateUserTest() {

        requestBody.setName("morpheus");
        requestBody.setJob("zion resident");

        ResponseUpdateBody responseBody = step("make request", () ->
                given()
                        .spec(changesUserBodyRequestSpec)
                        .body(requestBody)
                        .when()
                        .put("/users/2")
                        .then()
                        .spec(changesUserResponse200Spec)
                        .extract().as(ResponseUpdateBody.class));

        step("check response", () -> {
            Assertions.assertEquals("morpheus", responseBody.getName());
            Assertions.assertEquals("zion resident", responseBody.getJob());
        });
    }

    @Test
    @DisplayName("Проверка удаления существующего пользователя")
    void deleteUserTest() {

        step("make request and check status code", () -> {
            given()
                    .spec(changesUserNoBodyRequestSpec)
                    .when()
                    .delete("/users/2")
                    .then()
                    .spec(changesUserResponse204Spec);
        });
    }
}

package api.REQRES;

import io.qameta.allure.restassured.AllureRestAssured;
import models.changesWithUser.RequestChangesBody;
import models.changesWithUser.ResponseCreateBody;
import models.changesWithUser.ResponseUpdateBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class ChangesWithUserTest extends BaseTest {

    RequestChangesBody requestBody = new RequestChangesBody();

    @Test
    @DisplayName("Проверка создания пользователя")
    void createUserTest() {
        requestBody.setName("morpheus");
        requestBody.setJob("leader");

        ResponseCreateBody responseBody = given()
                .filter(withCustomTemplates())
                .body(requestBody)
                .contentType(JSON)
                .log().uri()
                .log().headers()
                .log().body()
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .extract().as(ResponseCreateBody.class);

        Assertions.assertEquals("morpheus", responseBody.getName());
        Assertions.assertEquals("leader", responseBody.getJob());
    }


    @Test
    @DisplayName("Проверка возможности создания пользователя с пустыми полями")
    void createEmptyUserTest() {
        requestBody.setName("");
        requestBody.setJob("");

        ResponseCreateBody responseBody = given()
                .filter(withCustomTemplates())
                .body(requestBody)
                .contentType(JSON)
                .log().uri()
                .log().headers()
                .log().body()
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .extract().as(ResponseCreateBody.class);

        Assertions.assertEquals("", responseBody.getName());
        Assertions.assertEquals("", responseBody.getJob());
    }


    @Test
    @DisplayName("Проверка изменения поля job у существующего пользователя")
    void updateUserTest() {

        requestBody.setName("morpheus");
        requestBody.setJob("zion resident");

        ResponseUpdateBody responseBody = given()
                .filter(withCustomTemplates())
                .body(requestBody)
                .contentType(JSON)
                .log().uri()
                .log().headers()
                .log().body()
                .when()
                .put("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(ResponseUpdateBody.class);

        Assertions.assertEquals("morpheus", responseBody.getName());
        Assertions.assertEquals("zion resident", responseBody.getJob());
    }

    @Test
    @DisplayName("Проверка удаления существующего пользователя")
    void deleteUserTest() {
        given()
                .filter(withCustomTemplates())
                .contentType(JSON)
                .log().uri()
                .log().headers()
                .when()
                .delete("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(204);
    }
}

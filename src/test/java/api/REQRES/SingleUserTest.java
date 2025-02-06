package api.REQRES;

import io.qameta.allure.restassured.AllureRestAssured;
import models.singleUser.ResponseSingleUserBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;


public class SingleUserTest extends BaseTest{

    @Test
    @DisplayName("Проверка пользователя с id = 2")
    void statusCode200Test() {

        ResponseSingleUserBody responseBody=given()
                .filter(withCustomTemplates())
                .contentType(JSON)
                .log().uri()
                .log().headers()
                .when()
                .get("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(ResponseSingleUserBody.class);

        Assertions.assertEquals(2, responseBody.getData().getId());
        Assertions.assertEquals("Janet", responseBody.getData().getFirstName());
        Assertions.assertEquals("Weaver", responseBody.getData().getLastName());
    }

    @Test
    @DisplayName("Проверка отсутствия пользователя с id = 2222")
    void dataIDTest() {

        given()
                .filter(withCustomTemplates())
                .contentType(JSON)
                .log().uri()
                .log().headers()
                .when()
                .get("/users/222")
                .then()
                .log().status()
                .statusCode(404);
    }

    @Test
    @DisplayName("Проверка соответствия номера в суфиксе в имени поля avatar со значением поля id")
    void dataAvatarSuffixEqualIdTest() {

        ResponseSingleUserBody responseBody=given()
                .filter(withCustomTemplates())
                .contentType(JSON)
                .log().uri()
                .log().headers()
                .when()
                .get("/users/2")
                .then()
                .log().status()
                .log().body()
                .extract().as(ResponseSingleUserBody.class);

        Assertions.assertEquals(2, responseBody.getData().getId());
        Assertions.assertTrue(responseBody.getData().getAvatar().contains("2"));
    }
}

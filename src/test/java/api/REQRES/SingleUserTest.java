package api.REQRES;

import models.singleUser.ResponseSingleUserBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.SingleUserSpec.*;

@Tag("api_test")
public class SingleUserTest extends BaseTest {

    @Tag("smoke_test")
    @Test
    @DisplayName("Проверка пользователя с id = 2")
    void statusCode200Test() {

        ResponseSingleUserBody responseBody = step("make request", () ->
                given()
                        .spec(singleUserRequestSpec)
                        .when()
                        .get("/users/2")
                        .then()
                        .spec(singleUserResponse200Spec)
                        .extract().as(ResponseSingleUserBody.class));

        step("check response", () -> {
            Assertions.assertEquals(2, responseBody.getData().getId());
            Assertions.assertEquals("Janet", responseBody.getData().getFirstName());
            Assertions.assertEquals("Weaver", responseBody.getData().getLastName());
        });
    }

    @Test
    @DisplayName("Проверка отсутствия пользователя с id = 2222")
    void dataIDTest() {

        step("make request and check status code", () -> {
            given()
                    .spec(singleUserRequestSpec)
                    .when()
                    .get("/users/222")
                    .then()
                    .spec(singleUserResponse404Spec);
        });
    }

    @Test
    @DisplayName("Проверка соответствия номера в суфиксе в имени поля avatar со значением поля id")
    void dataAvatarSuffixEqualIdTest() {

        ResponseSingleUserBody responseBody = step("make request", () ->
                given()
                        .spec(singleUserRequestSpec)
                        .when()
                        .get("/users/2")
                        .then()
                        .spec(singleUserResponse200Spec)
                        .extract().as(ResponseSingleUserBody.class));

        step("check response", () -> {
            Assertions.assertEquals(2, responseBody.getData().getId());
            Assertions.assertTrue(responseBody.getData().getAvatar().contains("2"));
        });
    }
}

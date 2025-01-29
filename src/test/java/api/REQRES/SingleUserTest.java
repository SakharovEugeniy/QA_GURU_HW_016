package api.REQRES;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;


public class SingleUserTest extends BaseTest{

    @Test
    @DisplayName("Проверка пользователя с id = 2")
    void statusCode200Test() {

        given()
                .baseUri(reqresUri)
                .basePath("/api/users/2")
                .contentType(JSON)
                .log().uri()
                .when()
                .get()
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", equalTo(2))
                .body("data.first_name", equalTo("Janet"))
                .body("data.last_name", equalTo("Weaver"));
    }

    @Test
    @DisplayName("Проверка отсутствия пользователя с id = 2222")
    void dataIDTest() {

        given()
                .baseUri(reqresUri)
                .basePath("/api/users/2222")
                .contentType(JSON)
                .log().uri()
                .when()
                .get()
                .then()
                .log().status()
                .statusCode(404);
    }

    @Test
    @DisplayName("Проверка соответствия номера в суфиксе в имени поля avatar со значением поля id")
    void dataAvatarSuffixEqualIdTest() {

        given()
                .baseUri(reqresUri)
                .basePath("/api/users/2")
                .contentType(JSON)
                .log().uri()
                .when()
                .get()
                .then()
                .log().status()
                .log().body()
                .body("data.id", equalTo(2))
                .body("data.avatar", containsString("2"));
    }
}

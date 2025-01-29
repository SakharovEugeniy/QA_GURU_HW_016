package api.REQRES;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;

public class CreateAndUpdateUserTest extends BaseTest {

    @Test
    @DisplayName("Проверка создания пользователя")
    void createUserTest() {
        given()
                .baseUri(reqresUri)
                .basePath("/api/users")
                .body("{\"name\": \"morpheus\", \"job\": \"leader\"}")
                .contentType(JSON)
                .log().uri()
                .when()
                .post()
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("leader"));
    }

    @Test
    @DisplayName("Проверка возможности создания пользователя с пустыми полями")
    void createEmptyUserTest() {
        given()
                .baseUri(reqresUri)
                .basePath("/api/users")
                .body("{\"name\": \"\", \"job\": \"\"}")
                .contentType(JSON)
                .log().uri()
                .when()
                .post()
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", equalTo(""))
                .body("job", equalTo(""));
    }

    @Test
    @DisplayName("Проверка изменения поля job у существующего пользователя")
    void updateUserTest() {
        given()
                .baseUri(reqresUri)
                .basePath("/api/users/2")
                .body("{\"name\": \"morpheus\", \"job\": \"zion resident\"}")
                .contentType(JSON)
                .log().uri()
                .when()
                .put()
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("zion resident"));
    }
}

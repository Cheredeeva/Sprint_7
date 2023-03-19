import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TestCreateCourier {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @After
    public void tearDown() {
        deleteCourier("ninja1234", "1234");
    }

    @Test
    public void createNewCourierTest() {
        CreateCourierRequestBody requestBody = new CreateCourierRequestBody(
                "ninja1234",
                "1234",
                "sasuke"
        );
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(requestBody)
                        .when()
                        .post("/api/v1/courier");
        response.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);
    }

    @Test
    public void createTwoSameCouriersTest() {
        CreateCourierRequestBody requestBody = new CreateCourierRequestBody(
                "ninja1234",
                "1234",
                "sasuke"
        );
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(requestBody)
                        .when()
                        .post("/api/v1/courier");
        response.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);

        response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(requestBody)
                        .when()
                        .post("/api/v1/courier");
        response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
    }

    private void deleteCourier(String login, String password) {
        LoginCourierRequestBody requestBody = new LoginCourierRequestBody(
                login,
                password
        );
        var responseBody = given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("/api/v1/courier/login")
                .as(LoginCourierResponseBody.class);

        given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .pathParam("id", responseBody.getId())
                .delete("/api/v1/courier/{id}");
    }
}


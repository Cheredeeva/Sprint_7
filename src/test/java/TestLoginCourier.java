import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TestLoginCourier {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";

        CreateCourierRequestBody requestBody = new CreateCourierRequestBody(
                "ninja1299r",
                "1234",
                "Ilya"
        );
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(requestBody)
                        .when()
                        .post("/api/v1/courier");
    }

    @After
    public void tearDown() {
        deleteCourier("ninja1234", "1234");
    }

    @Test
    public void loginCourierTest() {
        LoginCourierRequestBody requestBody = new LoginCourierRequestBody(
                "ninja1299r",
                "1234"
        );
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(requestBody)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    public void wrongPasswordTest() {
        LoginCourierRequestBody requestBody = new LoginCourierRequestBody(
                "ninja1299r",
                "12345"
        );
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(requestBody)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    public void wrongLoginTest() {
        LoginCourierRequestBody requestBody = new LoginCourierRequestBody(
                "ninja12uyhf",
                "1234"
        );
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(requestBody)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
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

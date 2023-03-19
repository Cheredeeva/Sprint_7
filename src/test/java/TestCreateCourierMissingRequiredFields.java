import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class TestCreateCourierMissingRequiredFields {
    private final String login;
    private final String password;
    private final String firstName;

    public TestCreateCourierMissingRequiredFields(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0}, {1}, {2}")
    public static Object[][] getData() {
        return new Object[][] {
                { null, "12345", "sasuke"},
                { "ninja12ert12", null, "sasuke"},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void createNewCourierMissingRequiredFieldsTest() {
        CreateCourierRequestBody requestBody = new CreateCourierRequestBody(login, password, firstName);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(requestBody)
                        .when()
                        .post("/api/v1/courier");
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }
}

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class TestLoginCourierMissingRequiredFields {
    private final String login;
    private final String password;

    public TestLoginCourierMissingRequiredFields(String login, String password) {
        this.login = login;
        this.password = password;
    }
        @Parameterized.Parameters(name = "Тестовые данные: {0}, {1}")
        public static Object[][] getData() {
            return new Object[][]{
                    {null, "12345"},
//                    {"ninja12", null},
            };
        }

        @Before
        public void setUp() {
            RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        }

        @Test
        public void loginCourierMissingRequiredFieldsTest() {
            LoginCourierRequestBody requestBody = new LoginCourierRequestBody(login, password);
            Response response =
                    given()
                            .header("Content-type", "application/json")
                            .and()
                            .body(requestBody)
                            .when()
                            .post("/api/v1/courier/login");
            response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                    .and()
                    .statusCode(400);
        }
}

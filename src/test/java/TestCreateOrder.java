import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class TestCreateOrder {
    private final List<String> color;

    public TestCreateOrder(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0}")
    public static Object[][] getData() {
        return new Object[][] {
                { List.of("BLACK") },
                { List.of("WHITE") },
                { List.of("BLACK", "WHITE") },
                { List.of() },
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void createOrder() {
        CreateOrderRequestBody requestBody = new CreateOrderRequestBody(
                "Naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                "pam",
                "+7 800 355 35 35",
                5,
                "2020-06-06",
                "Saske, come back to Konoha",
                color
        );
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(requestBody)
                        .when()
                        .post("/api/v1/orders");
        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);
    }
}


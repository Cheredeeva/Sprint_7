import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TestGetOrdersList {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void getOrdersListTest() {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .when()
                        .get("/api/v1/orders?limit=10&page=0");
        response.then().assertThat().body("orders", notNullValue())
                .and()
                .statusCode(200);
    }
}

package api;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.BeforeClass;
import test.org.listeners.CustomTpl;
import ui.BaseSelenideTest;

public class BaseApiTest extends BaseSelenideTest {
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://example.com/api";
        RestAssured.basePath = "/v1";

        RestAssured.requestSpecification = RestAssured.given()
                .contentType("application/json")
                .accept("application/json");

        RestAssured.filters(new ResponseLoggingFilter(), new RequestLoggingFilter(),
                CustomTpl.customLogFilter().withCustomTemplate());
    }
}

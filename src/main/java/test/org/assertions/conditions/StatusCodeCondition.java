package test.org.assertions.conditions;

import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;
import org.testng.Assert;

import java.util.Optional;

@RequiredArgsConstructor
public class StatusCodeCondition implements Condition {
    private final Integer statusCode;

    @Override
    public void check(ValidatableResponse response) {
        int actualStatusCode = response.extract().statusCode();
        Assert.assertEquals(Optional.ofNullable(statusCode), actualStatusCode);
    }
}

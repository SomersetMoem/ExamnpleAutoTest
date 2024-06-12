package test.org.assertions.conditions;

import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;
import org.testng.Assert;
import test.org.models.swagger.Info;

@RequiredArgsConstructor
public class MessageCondition implements Condition {
    private final String expectedMessage;

    @Override
    public void check(ValidatableResponse response) {
        Info info = response.extract().jsonPath().getObject("info", Info.class);
        Assert.assertEquals(expectedMessage, info.getMessage());
    }
}

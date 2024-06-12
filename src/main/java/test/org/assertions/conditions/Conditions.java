package test.org.assertions.conditions;


import test.org.assertions.conditions.Condition;
import test.org.assertions.conditions.MessageCondition;
import test.org.assertions.conditions.StatusCodeCondition;

public class Conditions {
    public static MessageCondition hasMessage(String expectedMessage) {
        return new MessageCondition(expectedMessage);
    }

    public static Condition hasStatusCode(Integer expectedStatusCode) {
        return new StatusCodeCondition(expectedStatusCode);
    }
}

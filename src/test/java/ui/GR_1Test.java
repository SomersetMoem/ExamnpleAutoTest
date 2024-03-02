package ui;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Epic("UI")
@Feature("Main")
public class GR_1Test extends BaseSelenideTest {
    private SoftAssert soft = new SoftAssert();

    @TmsLink("GR-1")
    @Test(description = "Открытие главной страницы")
    public void test() {


        soft.assertAll();
    }

    @Step("Шаг 1. Открыть главную страницу")
    private void step_1() {

    }
}

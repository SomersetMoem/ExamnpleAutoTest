package ui;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ui.pages.Header;
import ui.pages.MainPage;

@Epic("UI")
@Feature("Main")
public class GR_1Test extends BaseSelenideTest {
    private SoftAssert soft = new SoftAssert();
    private MainPage mainPage;
    private Header header;

    @TmsLink("GR-1")
    @Test(description = "Открытие страницы ЛК с главной страницы")
    public void test() {
        step_1();
        step_2();

        soft.assertAll();
    }

    @Step("Шаг 1. Открыть главную страницу")
    private void step_1() {
        mainPage = new MainPage(true);
        header = new Header();
    }

    @Step("Шаг 2. Нажать кнопку в хэдере 'Войти'")
    private void step_2() {
        header.clickLoginBtn(3);
    }
}

package ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.asserts.SoftAssert;
import ru.yandex.qatools.htmlelements.annotations.Name;
import test.org.ErrorMessage;

import static com.codeborne.selenide.Selenide.*;
import static test.org.utils.BrowserUtils.getDriver;

public class EmpBasketPage extends FieldWorkerPage {
    private final static Logger LOG = LogManager.getLogger(EmpBasketPage.class);
    private final ErrorMessage message = new ErrorMessage(EmpBasketPage.class);
    @Name("Заголовок пустой корзины")
    private final SelenideElement titleEmptyBasket = $x("//h1[text()='В корзине пока пусто']");
    @Name("Кнопка 'Вернуться на главную'")
    private final SelenideElement backMainBtn = $("[class='basket-empty__btn btn-main']");

    public EmpBasketPage(SoftAssert soft) {
        checkElementInPageVisibility(titleEmptyBasket, "Пустая корзина");
        soft.assertTrue(getDriver().getCurrentUrl().contains("basket"), "Страница имеет не верный URL");
    }

    @Step("Проверить, что корзина пустая - [{0}]")
    public EmpBasketPage checkBasketIsEmpty(boolean isEmpty, SoftAssert soft) {
        LOG.info("Проверить, что корзина пустая - " + isEmpty);
        soft.assertEquals(titleEmptyBasket.isDisplayed(), isEmpty, message.isVisibleElement("titleEmptyBasket", isEmpty));
        return this;
    }


    @Step("Нажать кнопку 'Вернуться на главную'")
    public MainPage clickBackMainBtn(int waitLoader) {
        LOG.info("Нажать кнопку 'Вернуться на главную'");
        clickElement(backMainBtn, waitLoader);
        return page(new MainPage(false));
    }
}

package ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.asserts.SoftAssert;
import ru.yandex.qatools.htmlelements.annotations.Name;
import test.org.ErrorMessage;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.*;
import static test.org.utils.BrowserUtils.getDriver;

public class BasketPage extends FieldWorkerPage {
    private final static Logger LOG = LogManager.getLogger(BasketPage.class);
    private final ErrorMessage message = new ErrorMessage(BasketPage.class);
    @Name("Заголовок пустой корзины")
    private final SelenideElement titleEmptyBasket = $x("//h1[text()='В корзине пока пусто']");
    @Name("Кнопка 'Вернуться на главную'")
    private final SelenideElement backMainBtn = $("[class='basket-empty__btn btn-main']");
    @Name("Карточки продуктов в корзине")
    private final ElementsCollection cardsProducts = $$(".list-item__wrap");

    public BasketPage(SoftAssert soft) {
        soft.assertTrue(getDriver().getCurrentUrl().contains("basket"), "Страница имеет не верный URL");
    }

    @Step("Проверить, что корзина пустая - [{0}]")
    public BasketPage checkBasketIsEmpty(boolean isEmpty, SoftAssert soft) {
        LOG.info("Проверить, что корзина пустая - " + isEmpty);
        soft.assertEquals(titleEmptyBasket.isDisplayed(), isEmpty, message.isVisibleElement("titleEmptyBasket", isEmpty));
        return this;
    }

    @Step("Нажать кнопку 'Вернуться на главную'")
    public MainPage clickBackMainBtnBtn(int waitLoader) {
        LOG.info("Нажать кнопку 'Вернуться на главную'");
        clickElement(backMainBtn, waitLoader);
        return page(new MainPage(false));
    }

    @Step("Проврка видимости карточки продукта [{0}] в корзине")
    public BasketPage checkVisibleCardProduct( String productName, SoftAssert soft) {
        LOG.info("Проврка видимости карточки продукта " + productName + " в корзине");
        soft.assertTrue(cardsProducts.find(attribute("title", productName))
                .isDisplayed(), message.isVisibleElement("cardsProducts", true));
        return this;
    }
}

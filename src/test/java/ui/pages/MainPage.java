package ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.asserts.SoftAssert;
import ru.yandex.qatools.htmlelements.annotations.Name;
import test.org.ErrorMessage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage extends FieldWorkerPage {
    private final static Logger LOG = LogManager.getLogger(MainPage.class);
    private final ErrorMessage message = new ErrorMessage(MainPage.class);
    @Name("Первая карточка продукта")
    private final SelenideElement firstCardProduct = $("[data-card-index='0']");
    @Name("Кнопка 'Добавить в корзину'")
    private String addToBasketBtn = "//*[contains(@aria-label,'%s')]/..//a[contains(@class, 'product-card__add-basket')]";

    public MainPage(boolean open) {
        if (open) {
            LOG.info("---------------open browser");
            Selenide.open("");
        }
        checkElementInPageVisibility(firstCardProduct, "Главная");
    }

    @Step("Получить название первого продукта")
    public String getNameFirstProduct() {
        return firstCardProduct.$x(".//a").getAttribute("aria-label");
    }

    @Step("Нажать кнопку 'Добавить в корзину' у продукта - [{0}]")
    public MainPage clickAddToBasketBtn(String productName, int waitLoader) {
        LOG.info("Нажать кнопку 'Добавить в корзину' у продукта - " + productName);
        SelenideElement element = $x(String.format(addToBasketBtn, productName));
        clickElement(element, waitLoader);
        return this;
    }

    @Step("Проверка, что текст кнопки изменился на - [{0}]")
    public MainPage checkTextAddToBasketBtn(String text, String productName, SoftAssert soft) {
        LOG.info("Проверка, что текст кнопки изменился на - " + text);
        SelenideElement element = $x(String.format(addToBasketBtn, productName));
        soft.assertEquals(element.getText(), text, message.isIncorrectValue("addToBasketBtn", text));
        return this;
    }
}

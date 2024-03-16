package ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.asserts.SoftAssert;
import ru.yandex.qatools.htmlelements.annotations.Name;
import test.org.ErrorMessage;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$$;
import static test.org.utils.BrowserUtils.getDriver;

public class BasketPage extends FieldWorkerPage {
    private final static Logger LOG = LogManager.getLogger(BasketPage.class);
    private final ErrorMessage message = new ErrorMessage(BasketPage.class);

    @Name("Карточки продуктов в корзине")
    private final ElementsCollection cardsProducts = $$(".list-item__wrap");

    public BasketPage(SoftAssert soft) {
        checkFirstElementInPageVisibility(cardsProducts, "Корзина с товарами");
        soft.assertTrue(getDriver().getCurrentUrl().contains("basket"), "Страница имеет не верный URL");
    }

    @Step("Проврка видимости карточки продукта [{0}] в корзине")
    public BasketPage checkVisibleCardProduct(String productName, SoftAssert soft) {
        LOG.info("Проврка видимости карточки продукта " + productName + " в корзине");
        List<SelenideElement> matchingElements = cardsProducts.stream()
                .filter(a -> a.text().contains(productName))
                .collect(Collectors.toList());
        if (!matchingElements.isEmpty()) {
            SelenideElement element = matchingElements.get(0);
            soft.assertTrue(element.isDisplayed(), message.isVisibleElement("cardsProducts", true));
        } else {
            soft.fail("Карточка продукта " + productName + " не найдена в корзине");
        }
        return this;
    }
}

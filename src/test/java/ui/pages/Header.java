package ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.asserts.SoftAssert;
import ru.yandex.qatools.htmlelements.annotations.Name;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class Header extends FieldWorkerPage {
    private final static Logger LOG = LogManager.getLogger(Header.class);
    @Name("Поле 'Поиск'")
    private final SelenideElement searchField = $("#searchInput");
    @Name("Навигационное меню 'Адреса'")
    private final SelenideElement addressBtn = $("[data-wba-header-name='Pick_up_points']");
    @Name("Навигационное меню 'Войти'")
    private final SelenideElement loginBtn = $("[data-wba-header-name='Login']");
    @Name("Навигационное меню 'Корзина'")
    private final SelenideElement basketBtn = $("[data-wba-header-name='Cart']");

    public Header() {
        checkElementInPageVisibility(searchField, "Хэдер");
    }

    @Step("Нажать кнопку 'Войти'")
    public LoginPage clickLoginBtn(int waitLoader) {
        LOG.info("Нажать кнопку 'Войти'");
        clickElement(loginBtn, waitLoader);
        return page(new LoginPage());
    }

    @Step("Нажать кнопку 'Корзина'")
    public BasketPage clickBasketBtn(int waitLoader, SoftAssert soft) {
        LOG.info("Нажать кнопку 'Корзина'");
        clickElement(basketBtn, waitLoader);
        return page(new BasketPage(soft));
    }
}

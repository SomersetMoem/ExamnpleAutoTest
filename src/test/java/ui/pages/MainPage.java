package ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.yandex.qatools.htmlelements.annotations.Name;

import static com.codeborne.selenide.Selenide.$;

public class MainPage extends FieldWorkerPage {
    private final static Logger LOG = LogManager.getLogger(MainPage.class);
    @Name("Первая карточка продукта")
    private final SelenideElement firstCardProduct = $("[data-card-index='0']");

    public MainPage(boolean open) {
        if (open) {
            LOG.info("---------------open browser");
            Selenide.open("");
        }
        checkElementInPageVisibility(firstCardProduct, "Главная");
    }
}

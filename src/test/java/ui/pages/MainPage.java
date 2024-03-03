package ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import ru.yandex.qatools.htmlelements.annotations.Name;

import static com.codeborne.selenide.Selenide.$;

public class MainPage extends FieldWorkerPage {
    @Name("Первая карточка продукта")
    private final SelenideElement firstCardProduct = $("[data-card-index='0']");

    public MainPage(boolean open) {
        if (open) {
            System.out.println("---------------open browser");
            Selenide.open("");
        }
        checkPageVisibility(firstCardProduct, "Главная");
    }
}

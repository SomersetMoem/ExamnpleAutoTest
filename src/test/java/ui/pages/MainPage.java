package ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.yandex.qatools.htmlelements.annotations.Name;

import static com.codeborne.selenide.Selenide.$;

public class MainPage extends FieldWorkerPage {
    @Name("Поле 'Поиск'")
    private final SelenideElement searchField = $("#searchInput");
    @Name("Навигационное меню 'Адреса'")
    private final SelenideElement address = $("[data-wba-header-name='Pick_up_points']");

    public MainPage() {
        checkPageVisibility(searchField, "Главная");
    }

    public MainPage(boolean open) {
        if (open) {
            System.out.println("---------------open browser");
            Selenide.open("");
        }
        checkPageVisibility(searchField, "Главная");
    }
}

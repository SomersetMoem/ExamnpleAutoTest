package ui.pages;

import com.codeborne.selenide.SelenideElement;
import ru.yandex.qatools.htmlelements.annotations.Name;

import static com.codeborne.selenide.Selenide.$x;

public class LoginPage extends FieldWorkerPage {
    @Name("Заголовок страницы")
    private final SelenideElement title = $x("//h2[contains(text(), 'Войти или создать профиль')]");

    public LoginPage() {
        checkElementInPageVisibility(title, "Войти или создать профиль");
        logUrl();
    }
}

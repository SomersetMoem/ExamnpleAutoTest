package ui;

import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class Test_1 extends BaseSelenideTest {

    @Test
    public void test() {
        open("https://hh.ru/");
        $("[data-qa='login']").click();
    }
}

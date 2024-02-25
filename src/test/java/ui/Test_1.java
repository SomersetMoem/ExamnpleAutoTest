package ui;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class Test_1 {

    @BeforeClass
    public static void setUp() {
        Configuration.pageLoadTimeout = 300000;
    }

    @Test
    public void test() {
        open("https://hh.ru/");
        $("[data-qa='login']").click();
    }
}

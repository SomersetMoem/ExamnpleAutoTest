package UI;

import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;

public class Test_1 {

    @Test
    public void test() {
        Selenide.open("https://www.google.com/");
    }
}

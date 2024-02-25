package ui;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import test.org.BaseTestClass;
import test.org.listeners.TestSuiteListener;

import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Configuration.browser;
import static java.lang.System.getProperty;

@Listeners({TestSuiteListener.class})
public class BaseSelenideTest extends BaseTestClass {

    public BaseSelenideTest() {
        baseUrl = config.getWebUrl();
        timeout = 20_000;
        pageLoadTimeout = 60_000;

        if (getProperty("browser") != null)
            browser = getProperty("browser", "chrome").toLowerCase();
    }

    @BeforeTest(alwaysRun = true)
    public void globalTestLogs(final ITestContext iTestContext) {
        LOG.info("Тест-набор: " + iTestContext.getCurrentXmlTest().getName());
        LOG.info("Запускаем тест: " + iTestContext.getCurrentXmlTest().getName());
    }

    protected void openURL(String url) {
        Selenide.open(url);
        LOG.info("Открыли страницу по адресу: " + getDriver().getCurrentUrl());
    }

    public WebDriver getDriver() {
        try {
            return WebDriverRunner.getWebDriver();
        } catch (IllegalStateException i) {
            LOG.info("Не удалось получить доступ к веб драйверу: " + i);
            return null;
        }
    }
}

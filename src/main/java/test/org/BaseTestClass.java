package test.org;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.testng.ScreenShooter;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Listeners;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import test.org.listeners.TestSuiteListener;

@Listeners({TestSuiteListener.class})
public class BaseTestClass {
    protected final static  Logger LOG = LogManager.getLogger(BaseTestClass.class);
    protected Config config = Config.getInstance();

    public BaseTestClass() {
        Configuration.browser = config.getBrowser();
        ScreenShooter.captureSuccessfulTests = true;
        Configuration.savePageSource = false;
        Configuration.screenshots = true;
        Configuration.browserSize = "1920x1080";
    }

    public WebDriver getDriver() {
        if (WebDriverRunner.hasWebDriverStarted())
            return WebDriverRunner.getWebDriver();
        return null;
    }

    @AfterClass(alwaysRun = true)
    public void refreshBrowser() {
        if (getDriver() != null) {
            LOG.info("Закрываю браузер (BaseTestClass)");
            getDriver().quit();
        }
    }
}


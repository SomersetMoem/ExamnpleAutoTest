package test.org;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.testng.ScreenShooter;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Listeners;
import test.org.listeners.TestSuiteListener;
import test.org.utils.AppConfig;

@Listeners({TestSuiteListener.class})
public class BaseTestClass {
    protected final static Logger LOG = LogManager.getLogger(BaseTestClass.class);
    protected AppConfig appConfig = ConfigFactory.create(AppConfig.class);

    public BaseTestClass() {
        Configuration.browser = appConfig.browser();
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


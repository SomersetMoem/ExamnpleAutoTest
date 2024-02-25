package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.testng.Assert;

import static com.codeborne.selenide.Selenide.*;

public class FieldWorkerPage {
    protected final static Logger LOG = LogManager.getLogger(FieldWorkerPage.class);
    private String loaderLocator = "Locator";
    private static final int LOADER_TIMEOUT_SECONDS = 60;

    protected FieldWorkerPage() {
        logUrl(this);
    }

    private void logUrl(Object object) {
        if (isPage(object) && WebDriverRunner.hasWebDriverStarted())
            LOG.info("Перешли на страницу с URL: " + WebDriverRunner.getWebDriver().getCurrentUrl());
    }

    private boolean isPage(Object object) {
        return object.getClass().getSuperclass() == FieldWorkerPage.class;
    }
    public void getLoader(int waitLoaderSeconds) {
        getLoader(waitLoaderSeconds, LOADER_TIMEOUT_SECONDS);
    }

    public void getLoader(int waitLoaderSeconds, int loaderTimeoutSeconds) {
        long end = System.currentTimeMillis() + waitLoaderSeconds * 1_000;
        while (System.currentTimeMillis() < end) {
            if ($$(loaderLocator).filter(Condition.visible).size() > 0) {
                long loaderTimeEnd = System.currentTimeMillis() + loaderTimeoutSeconds * 1_000;
                while (($$(loaderLocator).filter(Condition.visible).size() > 0) && System.currentTimeMillis() < loaderTimeEnd) {
                    sleep(300);
                }
            } else {
                sleep(300);
            }
        }
        Assert.assertFalse(($$(loaderLocator).filter(Condition.visible).size() > 0), "Лоадер не пропал после " + loaderTimeoutSeconds + " секунд");
    }

    protected void clickElement(SelenideElement element, int waitLoaderSeconds) {
        $(element).shouldBe(Condition.and("Требуемый элемент отсутсвует на экране",
                        Condition.visible,
                        Condition.exist))
                        .click();
        getLoader(waitLoaderSeconds);
    }

    protected void setValue(SelenideElement element, String value, int waitLoaderSeconds) {
        $(element).shouldBe(Condition.visible.because("Требуемый элемент отсутствует на экране")).click();
        $(element).shouldBe(Condition.enabled.because("Требуемый элемент недоступен для редактирования")).setValue(value);
        $(element).sendKeys(Keys.TAB);
        getLoader(waitLoaderSeconds);
    }

    protected void setTriesValue(SelenideElement element, String value, int waitLoaderSeconds, String expectedValue, int tries) {
        setValue(element, value, waitLoaderSeconds);
        if (expectedValue != null) {
            boolean done = false;
            while (tries > 0 && !done) {
                if ($(element).getValue().equals(expectedValue)) {
                    done = true;
                }
                else {
                    setValue(element, value, waitLoaderSeconds);
                    tries--;
                }
            }
            Assert.assertTrue(done, "Не удалось установить значение поля: " + expectedValue);
        }
    }

    protected boolean waitUntilVisible(ElementsCollection element, int count, int validationTimeoutSeconds) {
        long end = System.currentTimeMillis() + validationTimeoutSeconds*1_000;
        while (System.currentTimeMillis() < end) {
            if ($$(element).filter(Condition.visible).size() != count) {
                sleep(300);
            } else {
                return true;
            }
        }
        return false;
    }





}

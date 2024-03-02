package test.org.utils;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.util.HashSet;
import java.util.Set;

import static com.codeborne.selenide.Selenide.sleep;

public class BrowserUtils {

    public static WebDriver getDriver() {
        return WebDriverRunner.getWebDriver();
    }

    /**
     * переключиться на новое окно, закрыть его и вернуться назад
     */
    public static void closeNewPage(WebDriver driver) {
        String currentHandle = driver.getWindowHandle();
        switchToPageAndClose(driver, nextWindowHandle(driver), currentHandle);
    }

    /**
     * переключиться на новое окно
     */
    public static void switchToNewPage(WebDriver driver) {
        if (isOpenMoreThanOnePage(driver)) {
            driver.switchTo().window(nextWindowHandle(driver));
            sleep(1000);
        }
    }

    /**
     * Переключает на новое окно, открытое с помощью WebDriver, в течение указанного времени.
     *
     * @param driver  Экземпляр WebDriver для управления окнами.
     * @param windows Множество, содержащее идентификаторы окон перед открытием нового окна.
     * @param waitMS  Максимальное время ожидания появления нового окна, в миллисекундах.
     * @return true, если удалось успешно переключиться на новое окно, false в противном случае (истекло время ожидания или новое окно не найдено).
     */
    public static boolean switchToNewWindow(WebDriver driver, Set<String> windows, long waitMS) {
        long endTime = System.currentTimeMillis() + waitMS;
        while (System.currentTimeMillis() < endTime) {
            Set<String> newWindows = driver.getWindowHandles();
            if (newWindows.size() > windows.size()) {
                newWindows.removeAll(windows);
                getDriver().switchTo().window(newWindows.stream().findFirst().get());
                return true;
            }
            sleep(500);
        }
        return false;
    }

    /**
     * Проверяет, открыто ли более одной страницы в текущем окне браузера с помощью WebDriver.
     *
     * @param driver Экземпляр WebDriver для получения списка идентификаторов окон.
     * @return true, если открыто более одной страницы, false в противном случае.
     */
    public static boolean isOpenMoreThanOnePage(WebDriver driver) {
        return driver.getWindowHandles().size() > 1;
    }

    /**
     * @param removableHandle - закрываемое окно
     */
    public static void closePage(WebDriver driver, String removableHandle) {
        switchToPageAndClose(driver, removableHandle, "");
    }

    /**
     * Переключается на страницу и закрывает окно браузера с заданным идентификатором, а затем возвращает на предыдущую страницу.
     *
     * @param driver          Экземпляр WebDriver для управления окнами.
     * @param removableHandle Идентификатор окна, которое нужно закрыть.
     * @param backPageHandle  Идентификатор окна, на которое нужно вернуться после закрытия.
     */
    private static void switchToPageAndClose(WebDriver driver, String removableHandle, String backPageHandle) {
        Set<String> windowHandles = new HashSet<>(driver.getWindowHandles());

        if (windowHandles.size() > 1) {
            if (!driver.getWindowHandle().equals(removableHandle)) {
                switchToPage(driver, removableHandle);
                Selenide.sleep(1000);
            }
            driver.close();
            windowHandles.remove(removableHandle);
            if (!backPageHandle.equals(""))
                switchToPage(driver, backPageHandle);
            else
                switchToPage(driver, windowHandles.iterator().next());
            Selenide.sleep(1000);
        }
    }

    /**
     * получить заголовок нового окна
     */
    private static String nextWindowHandle(WebDriver driver) {
        String windowHandle = driver.getWindowHandle();
        Set<String> windowHandles = new HashSet<>(driver.getWindowHandles());
        windowHandles.remove(windowHandle);
        return windowHandles.iterator().next();
    }

    /**
     * Открывает новую пустую страницу в браузере.
     *
     * @param driver Экземпляр WebDriver для управления окнами браузера.
     */
    public static void openNewBlankPage(WebDriver driver) {
        Selenide.executeJavaScript("window.open('','_blank');");
        switchToNewPage(driver);
    }

    /**
     * Открывает новую вкладку в браузере и переходит на указанную страницу.
     *
     * @param driver Экземпляр WebDriver для управления окнами браузера.
     * @param url    URL-адрес страницы для открытия в новой вкладке.
     * @return Идентификатор новой вкладки.
     */
    public static String openPageInNewTab(WebDriver driver, String url) {
        Set<String> windowHandles = driver.getWindowHandles();
        Selenide.executeJavaScript("window.open('','_blank');");
        Set<String> newWindowHandles = driver.getWindowHandles();
        newWindowHandles.removeAll(windowHandles);
        String newWindow = newWindowHandles.stream().findFirst().get();
        driver.switchTo().window(newWindow);
        Selenide.open(url);
        return newWindow;
    }

    /**
     * Переключается на страницу с указанным идентификатором.
     *
     * @param driver     Экземпляр WebDriver для управления окнами браузера.
     * @param pageHandle Идентификатор страницы, на которую нужно переключиться.
     */
    public static void switchToPage(WebDriver driver, String pageHandle) {
        driver.switchTo().window(pageHandle);
    }

    /**
     * Получает куки текущей страницы в виде строки.
     *
     * @param driver Экземпляр WebDriver для управления куками браузера.
     * @return Строка, содержащая куки текущей страницы.
     */
    public static String getPageCookiesAsString(WebDriver driver) {
        String res = "";
        for (Cookie c : driver.manage().getCookies()) {
            res += c.getName() + "=" + c.getValue() + "; ";
        }
        return res.trim();
    }

    /**
     * Ждет применения изменений и очищает все данные браузера.
     *
     * @param count        Количество повторений процесса очистки.
     * @param timeForSleep Время ожидания между повторами очистки в миллисекундах.
     */
    public static void clearAllBrowserData(int count, int timeForSleep) {
        for (int i = 0; i < count; i++) {
            sleep(timeForSleep); // Ждем применения изменений, так как они не всегда срабатывают
            Selenide.clearBrowserCookies(); // Удаляем куки, чтобы изменения применились
            Selenide.clearBrowserLocalStorage();
            Selenide.refresh();
        }
    }
}

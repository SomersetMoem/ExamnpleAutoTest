package test.org;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.yandex.qatools.htmlelements.annotations.Name;

import java.lang.reflect.Field;

public class ErrorMessage {
    private Class type;
    private String name = "";
    protected final static Logger LOG = LogManager.getLogger(ErrorMessage.class);

    public ErrorMessage(Class type) {
        this.type = type;
    }

    public ErrorMessage(Class type, String name) {
        this.type = type;
        this.name = name;
    }

    private static final String PAGE_DOES_NOT_OPEN = "Страница %s не открылась";
    private static final String NOT_EMPTY_MESSAGE = "%s: Элемент %s должен быть заполнен";
    private static final String ELEMENT_SELECTED = "%s: Элемент %s должен быть: '%s'";
    private static final String VALUE_MESSAGE = "%s: Элемент %s должен иметь значение: '%s'";
    private static final String FIELD_DISABLED = "%s: Элемент %s должен быть: '%s'";

    public String getElementName(String fieldName) {
        Field field;
        try {
            field = type.getDeclaredField(fieldName);
        } catch (NoSuchFieldException ex) {
            try {
                field = type.getSuperclass().getDeclaredField(fieldName);
            } catch (NoSuchFieldException ex2) {
                LOG.warn("В классе " + type.getName() + " отсутствует поле " + fieldName);
                return null;
            }
        }
        if (field.isAnnotationPresent(Name.class))
            return field.getAnnotation(Name.class).value();
        return null;
    }

    public String pageNotOpen() {
        return String.format(PAGE_DOES_NOT_OPEN, this.name);
    }

    public String isNotEmpty(String fieldName) {
        return String.format(NOT_EMPTY_MESSAGE, this.name, getElementName(fieldName));
    }

    public String isSelectedElement(String fieldName, boolean expectedValue) {
        return String.format(ELEMENT_SELECTED, this.name, getElementName(fieldName), ((expectedValue ? "" : "не ") + "выбран"));
    }

    public String isVisibleElement(String fieldName, boolean expectedValue) {
        return String.format(ELEMENT_SELECTED, this.name, getElementName(fieldName), (expectedValue ? "видимым" : "скрыт"));
    }

    public String isIncorrectValue(String fieldName, String expectedValue) {
        return String.format(VALUE_MESSAGE, this.name, getElementName(fieldName), expectedValue);
    }

    public String isDisabledField(String fieldName, String expectedValue) {
        return String.format(FIELD_DISABLED, this.name, getElementName(fieldName), expectedValue);
    }

    public static String getFailedRequestMsg(String url, Throwable exception) {
        return "Произошла ошибка во время выполнения http-запроса " + url + ": " + exception.getMessage();
    }

    public static String getWrongResponseCodeMsg(String url) {
        return "Код ответа на запрос: " + url;
    }
}

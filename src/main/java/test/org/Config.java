package test.org;

import lombok.Getter;
import lombok.Setter;
import ru.yandex.qatools.properties.PropertyLoader;
import ru.yandex.qatools.properties.annotations.Property;
import ru.yandex.qatools.properties.annotations.Resource;

@Getter
@Setter
@Resource.Classpath("test.properties")
public class Config {
    private static Config config = new Config();

    @Property("browser")
    private static String browser;

    @Property("web.url")
    private static String webUrl;

    @Property("api.url")
    private static String apiUrl;

    @Property("user.email")
    private static String userLogin;

    @Property("user.password")
    private static String userPassword;

    private Config() {
        PropertyLoader.populate(this);
    }

    public static Config getInstance() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    public String getBrowser() {
        return System.getProperty("browser", browser);
    }

    public String getWebUrl() {
        return System.getProperty("url", webUrl);
    }

    public static String getApiUrl() {
        return System.getProperty("url", apiUrl);
    }

    public String getUserLogin() {
        return userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

}

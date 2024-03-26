package test.org.utils;


import org.aeonbits.owner.Config;

@org.aeonbits.owner.Config.Sources("classpath:test.properties")
public interface AppConfig extends Config {
    @Key("browser")
    String browser();

    @Key("web.url")
    String webUrl();

    @Key("api.url")
    String apiUrl();

    @Key("user.email")
    String userLogin();

    @Key("user.password")
    String userPassword();
}



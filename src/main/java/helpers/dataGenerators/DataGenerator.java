package helpers.dataGenerators;

import com.github.javafaker.Faker;

import java.util.Locale;

public class DataGenerator {
    Faker faker = new Faker(new Locale("ru"));

    public String generateFirstName() {
        return faker.name().firstName();
    }

    public String generateMiddleName() {
        return faker.name().nameWithMiddle();
    }

    public String generateLastName() {
        return faker.name().lastName();
    }

}

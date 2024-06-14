package test.org.converters;


public class NumberToTextConverter {
    private static final String[] units = {
            "", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять"
    };
    private static final String[] unitsFeminine = {
            "", "одна", "две", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять"
    };
    private static final String[] from10to19 = {"десять", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать", "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"};
    private static final String[] tens = {"", "", "двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят", "семьдесят", "восемьдесят", "девяносто"};
    private static final String[] hundreds = {"", "сто", "двести", "триста", "четыреста", "пятьсот", "шестьсот", "семьсот", "восемьсот", "девятьсот"};

    public static String convert(String numberStr) {
        try {
            int number = Integer.parseInt(numberStr.trim());
            String result = convertNumberToText(number);
            return capitalizeFirstLetter(result);
        } catch (NumberFormatException e) {
            return "Ошибка: введенная строка не является числом";
        }
    }

    private static String convertNumberToText(int number) {
        if (number == 0) return "ноль";

        String result = "";

        int thousands = number / 1000;
        int remainder = number % 1000;

        if (thousands > 0) {
            result += convertUnderThousand(thousands, true) + " тысяч" + getPluralForm(thousands);
            result += " ";
        }

        result += convertUnderThousand(remainder, false);

        return result.trim();
    }

    private static String convertUnderThousand(int number, boolean feminine) {
        String[] chosenUnits = feminine ? unitsFeminine : units;
        if (number >= 1000 || number < 0) {
            throw new IllegalArgumentException("Число " + number + " вне диапазона 0 - 999");
        }

        String part = "";

        if (number >= 100) {
            part += hundreds[number / 100] + " ";
            number %= 100;
        }

        if (number >= 20) {
            part += tens[number / 10] + " ";
            number %= 10;
        }

        if (number >= 10 && number < 20) {
            part += from10to19[number - 10] + " ";
        } else if (number > 0 || number == 0 && feminine) {
            part += chosenUnits[number] + " ";  // Сохраняем название числителя в зависимости от рода
        }

        return part.trim();
    }

    private static String getPluralForm(int number) {
        number %= 100;
        if (number >= 20) {
            number %= 10;
        }
        if (number == 1) {
            return "а";
        } else if (number >= 2 && number <= 4) {
            return "и";
        } else {
            return "";
        }
    }

    private static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}


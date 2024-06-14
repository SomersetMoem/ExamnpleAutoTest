package test.org.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DateUtils {
    private static LocalDate getLocalDateFromDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Получить строку с текущей датой и временем в формате HH:mm dd.MM.yyyy
     *
     * @return строка с датой в формате HH:mm dd.MM.yyyy
     */
    public static String getCurrentDateTimePoints() {
        ZonedDateTime today = ZonedDateTime.now(ZoneId.of("Europe/Moscow"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");
        return today.format(formatter);
    }

    /**
     * Получить строку с текущей датой и временем в формате dd.MM.yyyy
     *
     * @return строка с датой в формате dd.MM.yyyy
     */
    public static String getCurrentDate() {
        Date today = getToday();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        return dateFormat.format(today);
    }

    /**
     * Получить строку с текущей датой и временем в формате dd_MM_yyyy_HHmmss
     *
     * @return строка с датой в формате dd_MM_yyyy_HHmmss
     */
    public static String getTodayForScreen() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd_MM_yyyy_HHmmss", Locale.ENGLISH);
        return ZonedDateTime.now().format(formatter);
    }

    /**
     * Получить строку в формате mm'm' ss's' SSS'ms' для логирования времени выполнения
     *
     * @param timeStart  время начала
     * @param timeFinish время окончания
     * @return строка в формате mm'm' ss's' SSS'ms'
     */
    public static String getTimeForLog(long timeStart, long timeFinish) {
        Duration duration = Duration.ofMillis(timeFinish - timeStart);
        long minutes = duration.toMinutes();
        long seconds = duration.getSeconds() % 60;
        long milliseconds = duration.toMillis() % 1000;
        return String.format("%dm %ds %dms", minutes, seconds, milliseconds);
    }

    /**
     * Перевести строку в формате dd.MM.yyyy в дату
     *
     * @param dateString строка для конвертации
     * @return дата
     * Использовать {@link DateUtils#convertStringToDate(String)}
     */
    @Deprecated
    public static Date getDateFromString(String dateString) {
        return convertStringToDate(dateString);
    }

    /**
     * Перевести строку в формате dd.MM.yyyy в дату
     *
     * @param dateString строка для конвертации
     * @return дата
     */
    public static Date convertStringToDate(String dateString) {
        Date date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException("Дата не парсится: " + dateString);
        }
        return date;
    }

    /**
     * Получить текущую дату
     *
     * @return текущая дата
     */
    public static Date getToday() {
        LocalDate localDate = LocalDate.now();
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Получить начало дня для текущей даты
     *
     * @return текущая дата со временем 00:00:00
     */
    public static Date getTodayWithZeroTime() {
        LocalDate today = LocalDate.now();
        return Date.from(today.atStartOfDay(ZoneId.of("Europe/Moscow")).toInstant());
    }

    /**
     * Получить год из даты
     *
     * @param date дата
     * @return год даты в виде строки
     */
    public static String getYear(Date date) {
        LocalDate localDate = getLocalDateFromDate(date);
        return String.valueOf(localDate.getYear());
    }

    /**
     * Получить день из даты
     *
     * @param date дата
     * @return день даты в виде строки в формате dd
     */
    public static String getDay(Date date) {
        LocalDate localDate = getLocalDateFromDate(date);
        return String.format("%02d", localDate.getDayOfMonth());
    }

    /**
     * Получить день из даты
     *
     * @param date дата
     * @return день даты в виде строки в формате d
     */
    public static String getSimpleDay(Date date) {
        LocalDate localDate = getLocalDateFromDate(date);
        return Integer.toString(localDate.getDayOfMonth());
    }

    /**
     * Получить строковое представление месяца по его номеру
     *
     * @param monthIndex числовое представление месяца
     * @return строковое представление месяца
     */
    public static String getMonthByIndex(int monthIndex) {
        String[] monthNames = {
                "Январь", "Февраль", "Март", "Апрель",
                "Май", "Июнь", "Июль", "Август",
                "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
        };

        return (monthIndex >= 1 && monthIndex <= 12) ? monthNames[monthIndex - 1] : "";
    }

    /**
     * Получить числовое представление месяца по его названию
     *
     * @param monthName строковое представление месяца
     * @return числовое представление месяца
     */
    public static int convertMonthFromStringToNumber(String monthName) {
        Map<String, Integer> months = new HashMap<String, Integer>() {{
            put("январь", 1);
            put("февраль", 2);
            put("март", 3);
            put("апрель", 4);
            put("май", 5);
            put("июнь", 6);
            put("июль", 7);
            put("август", 8);
            put("сентябрь", 9);
            put("октябрь", 10);
            put("ноябрь", 11);
            put("декабрь", 12);
        }};
        return months.getOrDefault(monthName.toLowerCase(Locale.ROOT), 0);
    }

    /**
     * Получить строковое представление месяца из даты
     *
     * @param date дата
     * @return строковое представление месяца
     */
    public static String getMonthAsString(Date date) {
        LocalDate localDate = getLocalDateFromDate(date);
        int month = localDate.getMonthValue();
        return getMonthByIndex(month);
    }

    /**
     * Получить форматированную строку с датой для pdf ОСАГО полиса
     *
     * @param date дата
     * @return строка с датой в формате « dd » MMMM 20 yy г.
     */
    public static String getFormatDateForPdfOsagoPolicy(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("« dd » MMMM 20 yy г.", new Locale("ru"));
        return formatter.format(date);
    }

    /**
     * Отнять/прибавить к дате в виде строки годы, месяца и дни и получить новую дату в виде строки в формате dd.MM.yyyy.
     *
     * @param dateString дата в виде строки в формате dd.MM.yyyy
     * @param years      годы
     * @param months     месяца
     * @param days       дни
     * @return строка с датой в формате dd.MM.yyyy
     * Некорректно работает в високосный год. Использовать {@link DateUtils#datePlusDaysPlusMonthsPlusYears(String, int, int, int)}
     */
    @Deprecated
    public static String datePlusYearPlusMonthPlusDay(String dateString, int years, int months, int days) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            LocalDate initialDate = dateString.isEmpty() ? LocalDate.now() : LocalDate.parse(dateString, formatter);
            initialDate = initialDate
                    .plusYears(years)
                    .plusMonths(months)
                    .plusDays(days);
            return initialDate.format(formatter);
        } catch (DateTimeParseException exception) {
            return null;
        }
    }

    /**
     * Отнять/прибавить к дате в виде строки дни, месяца и годы и получить новую дату в виде строки в формате dd.MM.yyyy.
     * Добавляются/убавляются сначала дни, потом месяца и только потом годы. Необходимо для работы в високосный год
     *
     * @param dateString дата в виде строки в формате dd.MM.yyyy
     * @param years      годы
     * @param months     месяца
     * @param days       дни
     * @return строка с датой в формате dd.MM.yyyy
     */
    public static String datePlusDaysPlusMonthsPlusYears(String dateString, int years, int months, int days) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            LocalDate initialDate = dateString.isEmpty() ? LocalDate.now() : LocalDate.parse(dateString, formatter);
            initialDate = initialDate
                    .plusDays(days)
                    .plusMonths(months)
                    .plusYears(years);
            return initialDate.format(formatter);
        } catch (DateTimeParseException exception) {
            return null;
        }
    }

    /**
     * Отнять/прибавить к текущей дате дни и получить новую дату в виде строки в формате dd.MM.yyyy
     *
     * @param days дни
     * @return строка с датой в формате dd.MM.yyyy
     */
    public static String getCurrentDatePlusDays(int days) {
        return datePlusDaysPlusMonthsPlusYears("", 0, 0, days);
    }

    /**
     * Отнять/прибавить к текущей дате годы и дни получить новую дату в виде строки в формате dd.MM.yyyy.
     *
     * @param years годы
     * @param days  дни
     * @return строка с датой в формате dd.MM.yyyy
     * Некорректно работает в високосный год. Использовать {@link DateUtils#getCurrentDatePlusDaysPlusYears(int, int)}
     */
    @Deprecated
    public static String getCurrentDatePlusYearPlusDays(int years, int days) {
        return datePlusYearPlusMonthPlusDay("", years, 0, days);
    }

    /**
     * Отнять/прибавить к текущей дате дни и годы получить новую дату в виде строки в формате dd.MM.yyyy.
     * Добавляются/убавляются сначала дни, потом годы. Необходимо для работы в високосный год
     *
     * @param days  дни
     * @param years годы
     * @return строка с датой в формате dd.MM.yyyy
     */
    public static String getCurrentDatePlusDaysPlusYears(int years, int days) {
        return datePlusDaysPlusMonthsPlusYears("", years, 0, days);
    }

    /**
     * Отнять/прибавить минуты к текущей дате
     *
     * @param minutes минуты
     * @return дата
     */
    public static Date getTodayMinusMinutes(int minutes) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now().minusMinutes(minutes);
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * Добавить/отнять годы к текущей дате
     *
     * @param date  дата
     * @param years годы
     * @return дата
     */
    public static Date addYears(Date date, int years) {
        LocalDateTime localDateTime = getLocalDateFromDate(date)
                .atStartOfDay()
                .plusYears(years);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Получить текущую UTC дату в формате строки со смещением по времени.
     *
     * @param years   смещение по годам
     * @param months  смещение по месяцам
     * @param days    смещение по дням
     * @param hours   смещение по часам
     * @param minutes смещение по минутам
     * @param seconds смещение по секундам
     * @param nanos   смещение по наносекундам
     * @param offset  смещение по часовой зоне
     * @return UTC дата в формате строки
     * Некорректно работает в високосный год. Использовать {@link DateUtils#getShiftedCurrentUtcDateTime(int, int, int, int, int, int, int, String)}
     */
    @Deprecated
    public static String getCurrentUtcDateTimeWithShift(int years, int months, int days, int hours, int minutes, int seconds, int nanos, String offset) {
        ZoneOffset zoneOffset = ZoneOffset.of(offset);
        ZonedDateTime zonedDateTime = ZonedDateTime
                .now()
                .plusYears(years)
                .plusMonths(months)
                .plusDays(days)
                .plusHours(hours)
                .plusMinutes(minutes)
                .plusSeconds(seconds)
                .plusNanos(nanos)
                .withZoneSameInstant(zoneOffset);

        return zonedDateTime.toString();
    }

    /**
     * Получить текущую UTC дату в формате строки со смещением по времени.
     * Добавляются/убавляются сначала наносекунды, секунды, минуты, часы, дни, месяца и только потом годы.
     * Необходимо для работы в високосный год
     *
     * @param years   смещение по годам
     * @param months  смещение по месяцам
     * @param days    смещение по дням
     * @param hours   смещение по часам
     * @param minutes смещение по минутам
     * @param seconds смещение по секундам
     * @param nanos   смещение по наносекундам
     * @param offset  смещение по часовой зоне
     * @return UTC дата в формате строки
     */
    public static String getShiftedCurrentUtcDateTime(int years, int months, int days, int hours, int minutes, int seconds, int nanos, String offset) {
        ZoneOffset zoneOffset = ZoneOffset.of(offset);
        ZonedDateTime zonedDateTime = ZonedDateTime
                .now()
                .plusNanos(nanos)
                .plusSeconds(seconds)
                .plusMinutes(minutes)
                .plusHours(hours)
                .plusDays(days)
                .plusMonths(months)
                .plusYears(years)
                .withZoneSameInstant(zoneOffset);

        return zonedDateTime.toString();
    }

    /**
     * Получить текущую UTC дату в формате строки
     *
     * @param offset смещение по часовой зоне
     * @return UTC дата в формате строки
     */
    public static String getCurrentUtcDateTime(String offset) {
        return getShiftedCurrentUtcDateTime(0, 0, 0, 0, 0, 0, 0, offset);
    }


    /**
     * Получить начало дня для UTC даты в формате строки
     *
     * @param dateString строка в формате UTC даты
     * @return строка в формате UTC с началом дня
     */
    public static String getStartOfTheUtcDate(String dateString) {
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateString, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        OffsetDateTime startOfDay = offsetDateTime
                .truncatedTo(ChronoUnit.DAYS)
                .with(LocalTime.MIDNIGHT);

        return startOfDay.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    /**
     * Получить окончание дня для UTC даты в формате строки
     *
     * @param dateString строка в формате UTC даты
     * @return строка в формате UTC с окончанием дня
     */
    public static String getEndOfTheUtcDate(String dateString) {
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateString, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        OffsetDateTime endOfDay = offsetDateTime
                .truncatedTo(ChronoUnit.DAYS)
                .with(LocalTime.of(23, 59, 59));

        return endOfDay.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    /**
     * Извлечь дату и время из строки в формате UTC даты
     *
     * @param dateString строка в формате UTC даты
     * @return строка с датой и временем
     */
    public static String getDateWithoutUtcOffset(String dateString) {
        return dateString.substring(0, dateString.length() - 6);
    }

    /**
     * Форматировать строку в формате UTC даты к заданному формату
     *
     * @param date    строка в формате UTC даты или yyyy-MM-dd
     * @param pattern формат
     * @return строка в заданном формате
     */
    public static String formatDateToWellKnownFormat(String date, String pattern) {
        try {
            OffsetDateTime offsetDateTime = OffsetDateTime.parse(date, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            return offsetDateTime.format(DateTimeFormatter.ofPattern(pattern));
        } catch (DateTimeException exception) {
            try {
                OffsetDateTime offsetDateTime = OffsetDateTime.parse(date + "T00:00:00.00Z", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                return offsetDateTime.format(DateTimeFormatter.ofPattern(pattern));
            } catch (DateTimeException e) {
                throw new IllegalArgumentException("Не удалось преобразовать дату к " + pattern + ": " + date, e);
            }
        }
    }

    /**
     * Форматировать строку в формате UTC даты к формату dd.MM.yyyy
     *
     * @param date строка в формате UTC даты или yyyy-MM-dd
     * @return строка в формате dd.MM.yyyy
     */
    public static String formatDateToWellKnownFormat(String date) {
        return formatDateToWellKnownFormat(date, "dd.MM.yyyy");
    }
}

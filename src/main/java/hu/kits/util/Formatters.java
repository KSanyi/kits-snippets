package hu.kits.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.Temporal;
import java.util.Locale;

public class Formatters {

    public static final Locale HU_LOCALE = new Locale("HU");
    
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    private static final DateTimeFormatter LONG_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy. MMMM dd.", HU_LOCALE);
    private static final DateTimeFormatter MONTH_FORMAT = DateTimeFormatter.ofPattern("yyyy MMMM", HU_LOCALE);
    private static final DateTimeFormatter WEEK_FORMAT = DateTimeFormatter.ofPattern("yyyy ww", HU_LOCALE);
    private static final DateTimeFormatter MONTH_NO_YEAR_FORMAT = DateTimeFormatter.ofPattern("MMMM", HU_LOCALE);
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
    private static final DateTimeFormatter MONTH_WITH_DAY_FORMAT = DateTimeFormatter.ofPattern("MMMM d.", HU_LOCALE);
    private static final DecimalFormat DECIMAL_FORMAT;
    private static final DecimalFormat DECIMAL_FORMAT2;
    private static final DecimalFormat PERCENT_FORMAT;
    private static final DecimalFormat PERCENT_FORMAT2;
    
    static {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.US);
        decimalFormatSymbols.setGroupingSeparator(' ');
        DECIMAL_FORMAT = new DecimalFormat("###,###", decimalFormatSymbols);
        DECIMAL_FORMAT2 = new DecimalFormat("0.00", decimalFormatSymbols);
        PERCENT_FORMAT = new DecimalFormat("0.0%", decimalFormatSymbols);
        PERCENT_FORMAT2 = new DecimalFormat("0.00%", decimalFormatSymbols);
    }
    
    public static String formatDate(LocalDate date) {
        return DATE_FORMAT.format(date);
    }
    
    public static String formatDateLong(LocalDate date) {
        return LONG_DATE_FORMAT.format(date);
    }
    
    public static String formatMonthWithDay(LocalDate date) {
        return StringUtil.capitalize(MONTH_WITH_DAY_FORMAT.format(date));
    }
    
    public static String formatDateTime(LocalDateTime dateTime) {
        return DATE_TIME_FORMAT.format(dateTime);
    }
    
    public static String formatShortWeekDay(LocalDate day) {
        return (day.getDayOfWeek().getDisplayName(TextStyle.SHORT, HU_LOCALE));
    }

    public static String formatMonth(LocalDate dayOfMonth) {
        return MONTH_FORMAT.format(dayOfMonth);
    }
    
    public static String formatWeek(Temporal date) {
        return WEEK_FORMAT.format(date) + ". hét";
    }
    
    public static String formatMonthWithoutYear(LocalDate dayOfMonth) {
        return MONTH_NO_YEAR_FORMAT.format(dayOfMonth);
    }
    
    public static String formatDayOfWeek(LocalDate date) {
        return StringUtil.capitalize(date.getDayOfWeek().getDisplayName(TextStyle.FULL, HU_LOCALE));
    }
    
    public static String formatPercent(double value) {
        return PERCENT_FORMAT.format(value);
    }
    
    public static String formatPercent2(double value) {
        return PERCENT_FORMAT2.format(value);
    }
    
    public static String formatDecimal(int value) {
        return DECIMAL_FORMAT.format(value);
    }
    
    public static String formatDecimal2(double value) {
        return DECIMAL_FORMAT2.format(value);
    }

    public static String formatHalf(LocalDate date) {
        return date.getYear() + " " + (date.getMonthValue() < 7 ? "I." : "II.");
    }

    public static String formatQuarter(LocalDate date) {
        return date.getYear() + " " + getQuarter(date.getMonthValue());
    }
    
    private static String getQuarter(int month) {
        if(month < 4) return "Q1";
        if(month < 7) return "Q2";
        if(month < 10) return "Q3";
        return "Q4";
    }

}

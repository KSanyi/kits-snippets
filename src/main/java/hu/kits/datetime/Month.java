package hu.kits.datetime;

import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

import hu.kits.util.Formatters;

public record Month(int year, int monthNumber) {

    private LocalDate firstDay() {
        return LocalDate.of(year, monthNumber, 1);
    }

    public List<LocalDate> days() {
        LocalDate firstDay = firstDay();
        return firstDay.datesUntil(firstDay.plusMonths(1)).collect(toList());
    }
    
    public DateInterval interval() {
        LocalDate firstDay = firstDay();
        return new DateInterval(firstDay, firstDay.plusMonths(1).minusDays(1));
    }

    public Month prevMonth() {
        return of(firstDay().minusMonths(1));
    }
    
    public Month nextMonth() {
        return of(firstDay().plusMonths(1));
    }
    
    public Month add(int months) {
        return of(firstDay().plusMonths(months));
    }
    
    public LocalDate on(int dayOfMonth) {
        return firstDay().withDayOfMonth(dayOfMonth);
    }
    
    public static Month of(int year, int monthNumber) {
        return new Month(year, monthNumber);
    }
    
    public static Month of(LocalDate date) {
        return of(date.getYear(), date.getMonthValue());
    }
    
    public static List<Month> of(int year) {
        return IntStream.rangeClosed(1, 12).mapToObj(month -> of(year, month)).collect(toList());
    }
    
    @Override
    public String toString() {
        return Formatters.formatMonth(firstDay());
    }
    
    public String toStringWithoutYear() {
        return Formatters.formatMonthWithoutYear(firstDay());
    }
    
}

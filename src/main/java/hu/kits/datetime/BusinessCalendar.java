package hu.kits.datetime;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class BusinessCalendar {

    private static List<LocalDate> holidays = List.of(
            LocalDate.of(2019, 1, 1),
            LocalDate.of(2019, 3,15),
            LocalDate.of(2019, 4,19),
            LocalDate.of(2019, 4,22),
            LocalDate.of(2019, 5, 1),
            LocalDate.of(2019, 6,10),
            LocalDate.of(2019, 8,19),
            LocalDate.of(2019, 8,20),
            LocalDate.of(2019,10,23),
            LocalDate.of(2019,11, 1),
            LocalDate.of(2019,12,24),
            LocalDate.of(2019,12,25),
            LocalDate.of(2019,12,26),
            LocalDate.of(2019,12,27),
            
            LocalDate.of(2020, 1, 1),
            LocalDate.of(2020, 3,15),
            LocalDate.of(2020, 4,10),
            LocalDate.of(2020, 4,13),
            LocalDate.of(2020, 5, 1),
            LocalDate.of(2020, 6, 1),
            LocalDate.of(2020, 8,20),
            LocalDate.of(2020, 8,21),
            LocalDate.of(2020,10,23),
            LocalDate.of(2020,11, 1),
            LocalDate.of(2020,12,24),
            LocalDate.of(2020,12,25),
            LocalDate.of(2020,12,26)
            );
    
    private static List<LocalDate> workdaySaturdays = List.of(
            LocalDate.of(2019, 8,10),
            LocalDate.of(2019,12, 7),
            LocalDate.of(2019,12,14),
            
            LocalDate.of(2019, 8,29),
            LocalDate.of(2019,12,12)
            );
    
    public static boolean isBusinessDay(LocalDate date) {
        if(holidays.contains(date)) return false;
        if(workdaySaturdays.contains(date)) return true;
        return date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY;
    }
    
    public static int numberOfBusinessDays(DateInterval dateInterval) {
        return (int)dateInterval.daysStream().filter(BusinessCalendar::isBusinessDay).count();
    }
    
}

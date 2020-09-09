package hu.kits.datetime;

import static java.util.stream.Collectors.toList;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import hu.kits.util.Clock;

public record DateInterval(LocalDate from, LocalDate to) implements Comparable<DateInterval>, Iterable<LocalDate> {

    private static final LocalDate MIN = LocalDate.of(2000,1,1);
    private static final LocalDate MAX = LocalDate.of(2050,1,1);
    
    public static final DateInterval FULL = new DateInterval(null,  null);
    
    public static DateInterval of(Month month) {
        LocalDate from = Clock.today().withMonth(month.getValue()).withDayOfMonth(1);
        return new DateInterval(from, from.plusMonths(1).minusDays(1));
    }
    
    public static DateInterval of(int year) {
        LocalDate from = LocalDate.of(year, 1, 1);
        return new DateInterval(from, from.plusYears(1).minusDays(1));
    }
	
	public static DateInterval openUntilToday() {
        return new DateInterval(MIN, Clock.today());
    }
	
	public DateInterval {
		if(from == null) from = MIN;
		if(to == null) to = MAX;
		if(to.isBefore(from)) {
			throw new IllegalArgumentException("Invalid interval: " + from + " - " + to);
		}
	}
	
	public boolean contains(LocalDate value) {
		return !value.isBefore(from) && !value.isAfter(to);
	}
	
	public boolean contains(DateInterval other) {
        return !this.from.isAfter(other.from) && !this.to.isBefore(other.to);
    }
	
	public List<LocalDate> days() {
	    return daysStream().collect(toList());
	}
	
	public Stream<LocalDate> daysStream() {
        return from.datesUntil(to.plusDays(1));
    }
	
	public int numberOfDays() {
	    return (int)Duration.between(from.atStartOfDay(), to.atStartOfDay()).toDays();
	}
	
	@Override
    public Iterator<LocalDate> iterator() {
        return new Iterator<LocalDate>() {
            
            private LocalDate date = from.minusDays(1);
            
            @Override
            public boolean hasNext() {
                return !date.equals(to);
            }

            @Override
            public LocalDate next() {
                date = date.plusDays(1);
                return date;
            }
        };
    }
	
	public Optional<DateInterval> interSection(DateInterval other) {
        if(from.isAfter(other.to) || other.from.isAfter(to)) {
            return Optional.empty();
        } else {
            return Optional.of(new DateInterval(max(from, other.from), min(to, other.to)));
        }
    }
    
    private static LocalDate max(LocalDate date1, LocalDate date2) {
        return date1.isAfter(date2) ? date1 : date2;
    }
    
    private static LocalDate min(LocalDate date1, LocalDate date2) {
        return date1.isBefore(date2) ? date1 : date2;
    }
	
	@Override
    public String toString() {
		String fromString = from .equals(LocalDate.MIN) ? "" : from.toString();
		String toString = from .equals(MAX) ? "" : to.toString();
        return "[" + fromString + " - " + toString + "]";
    }

	@Override
	public int compareTo(DateInterval other) {
		return from.compareTo(other.from);
	}
	
}
	

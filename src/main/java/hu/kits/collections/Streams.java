package hu.kits.collections;

import static java.time.LocalDate.parse;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import hu.kits.datetime.WeekUtil;

public class Streams {

    public static void main(String[] args) {
        
        var trades = List.of(
                new Trade(parse("2018-01-03"), "APPL", 100),
                new Trade(parse("2018-01-03"), "GOOG", 200),
                new Trade(parse("2018-01-04"), "APPL", 100),
                new Trade(parse("2018-01-04"), "GOOG", -50),
                new Trade(parse("2018-01-07"), "APPL",  50));
        
        for(var date : WeekUtil.week(2018, 1)) {
            System.out.println("Positions on: " + date + ": " + calculatePositions(trades, date));
        }
        System.out.println();
        
        System.out.println("Trades by quantity:\n" + sortTradesByQuantity(trades));
        System.out.println();
        
        System.out.println("Latest trade by ticker:\n" + findLatestTradeByTicker(trades));
        System.out.println();
        
        System.out.println("Latest trade: " + findTheLatestTrade(trades));
        System.out.println();
        
        System.out.println("Latest trade date: " + findTheLatestTradeDate(trades));
    }
    
    private static Map<String, Integer> calculatePositions(List<Trade> trades, LocalDate date) {
        
        return trades.stream()
            .filter(trade -> !trade.date().isAfter(date))
            .collect(Collectors.groupingBy(Trade::ticker, TreeMap::new, Collectors.summingInt(Trade::quantity)));
          //.collect(Collectors.groupingBy(Trade::ticker, Collectors.summingInt(Trade::quantity)));
    }
    
    private static List<Trade> sortTradesByQuantity(List<Trade> trades) {
        
        return trades.stream()
                .sorted(Comparator.comparing(Trade::quantity).reversed())
                .collect(Collectors.toList());
    }
    
    private static Map<String, Trade> findLatestTradeByTicker(List<Trade> trades) {
        
        return trades.stream()
            .sorted(Comparator.comparing(Trade::date))
            .collect(Collectors.toMap(Trade::ticker, Function.identity(), (t1, t2) -> t2, TreeMap::new));
          //.collect(Collectors.toMap(Trade::ticker, Function.identity(), (t1, t2) -> t2));
    }
    
    private static Optional<LocalDate> findTheLatestTradeDate(List<Trade> trades) {
        return trades.stream()
                .map(Trade::date)
                .max(LocalDate::compareTo);
    }
    
    private static Optional<Trade> findTheLatestTrade(List<Trade> trades) {
        return trades.stream()
                .max(Comparator.comparing(Trade::date));
    }
    
    private static record Trade(LocalDate date, String ticker, int quantity) {}

}

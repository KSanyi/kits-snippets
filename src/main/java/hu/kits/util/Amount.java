package hu.kits.util;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.reducing;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public record Amount(Currency currency, BigDecimal value) {

    private static final double EPSILON = 0.02;
    
    private static final DecimalFormat FORMAT;
    private static final DecimalFormat FORMAT_00;
    private static final BigDecimal HUNDRED = new BigDecimal(100);
    
    static {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.US);
        decimalFormatSymbols.setGroupingSeparator(' ');
        FORMAT = new DecimalFormat("###,###", decimalFormatSymbols);
        FORMAT.setParseBigDecimal(true);
        FORMAT_00 = new DecimalFormat("###,##0.00", decimalFormatSymbols);
        FORMAT_00.setParseBigDecimal(true);
    }
    
    public Amount(Currency currency, int main, int sub) {
        this(currency, new BigDecimal(main + "." + (sub > 9 ? sub : "0" + sub)));
    }
    
    public Amount(Currency currency, int main) {
        this(currency, main, 0);
    }
    
    public Amount(Currency currency, String valueString) {
        try {
            this.currency = currency;
            value = (BigDecimal)FORMAT_00.parse(valueString.replaceAll("\\,", "\\."));
        } catch (ParseException e) {
            throw new RuntimeException("Could not format " + valueString);
        }
    }

    public int main() {
        return value.intValue();
    }

    public int sub() {
        return value.multiply(HUNDRED).remainder(HUNDRED).intValue();
    }

    public Amount add(Amount other) {
        currencyCheck(other);
        return new Amount(currency, value.add(other.value));
    }
    
    public Amount subtract(Amount other) {
        currencyCheck(other);
        return new Amount(currency, value.subtract(other.value));
    }
    
    public Amount multiply(int quantity) {
        return new Amount(currency, value.multiply(BigDecimal.valueOf(quantity)));
    }
    
    public Amount negate() {
        return new Amount(currency, value.negate());
    }

    public boolean isNegative() {
        return value.signum() == -1;
    }
    
    public boolean almostEquals(Amount other) {
        return this.subtract(other).value.abs().doubleValue() < EPSILON;
    }
    
    private void currencyCheck(Amount other) {
        if(currency  != other.currency) throw new IllegalArgumentException("Adding different currencies: " + currency + ", " + other.currency);
    }
    
    @Override
    public String toString() {
        return toStringNoCurrency()  + " " + currency.sign;
    }
    
    public String toStringNoCurrency() {
        DecimalFormat format = currency == Currency.HUF ? FORMAT : FORMAT_00;
        return format.format(value);   
    }
    
    public static Amount HUF(int main) {
        return new Amount(Currency.HUF, main);
    }
    
    public static Amount EUR(int main) {
        return new Amount(Currency.EUR, main);
    }
    
    public static Amount ZERO(Currency currency) {
        return new Amount(currency, BigDecimal.ZERO);
    }
    
    public static Map<Currency, Amount> sumAmountsByCurrency(Collection<Amount> amounts) {
        
        return amounts.stream()
            .collect(groupingBy(amount -> amount.currency, reducing(Amount::add)))
            .entrySet().stream().collect(Collectors.toMap(Entry::getKey, e -> e.getValue().get()));
    }
    
    public static String createSummary(Collection<Amount> amounts) {
        
        return sumAmountsByCurrency(amounts).values().stream()
                .map(Amount::toString)
                .collect(joining(", "));
    }
    
    public static Amount sum(Currency currency, Collection<Amount> amounts) {
        return amounts.stream().reduce(ZERO(currency), Amount::add);
    }

}

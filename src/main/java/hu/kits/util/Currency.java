package hu.kits.util;

import java.util.List;

public enum Currency {
    
    EUR("\u20AC"), HUF("Ft"), USD("$"), CHF("Fr"), GBP("\u00A3");
    
    public final String sign;
    
    private Currency(String sign) {
        this.sign = sign;
    }
    
    public static List<Currency> allValues = List.of(Currency.values());
    
}

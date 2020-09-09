package hu.kits.util;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class Country {

    public final String code;
    
    public final String name;

    private Country(String code, String name) {
        this.code = code;
        this.name = name;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    public static List<Country> all() {
        return allCountries;
    }
    
    public static Country of(String code) {
        return allCountries.stream().filter(c -> c.code.equals(code)).findFirst().orElseThrow(() -> new IllegalArgumentException("No country found with code: '" + code + "'"));
    }
    
    private final static List<Country> allCountries;
    
    static {
        try {
            allCountries = Files.lines(ResourceFileLoader.loadPath("countries.txt"), StandardCharsets.UTF_8)
                    .map(line -> new Country(line.substring(0, 2), line.substring(3)))
                    .sorted(comparing(c -> c.name))
                    .collect(toList());
        } catch (IOException ex) {
            throw new RuntimeException("Can not load countries", ex);
        }
    }
    
    public static final Country HU = of("HU");
    
}

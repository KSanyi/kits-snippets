package hu.kits.util;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClassPathUtil {

    public static String loadResourceFileAsString(String fileName, Class<?> clazz) {
        try {
            InputStream inputStream = clazz.getResourceAsStream(fileName);
            try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
                return scanner.useDelimiter("\\A").next();
            }
        } catch (Exception ex) {
            throw new RuntimeException("Could not load file " + fileName);
        }
    }
    
}

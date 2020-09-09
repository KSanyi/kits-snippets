package hu.kits.crypto;

import java.util.Optional;

public class PasswordValidator {

    public static Optional<String> validatePassword(String password) {
        
        if(password.length() < 5) {
            return Optional.of("Jelszó túl rövid");
        } else if(!password.matches(".*\\d+.*")) {
            return Optional.of("Jelszó nem tartalmaz számot");
        } else if(password.toLowerCase().equals(password)) {
            return Optional.of("Jelszó nem tartalmaz nagybetűt");
        } else if(password.toUpperCase().equals(password)) {
            return Optional.of("Jelszó nem tartalmaz kisbetűt");
        } else {
            return Optional.empty();
        }
    }
    
}

package hu.kits.util;

public record Pair<T, S>(T first, S second) {

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
    
}

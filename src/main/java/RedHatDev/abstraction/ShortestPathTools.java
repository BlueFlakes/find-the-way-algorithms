package RedHatDev.abstraction;

import java.util.function.BiPredicate;
import java.util.function.Function;

import static java.lang.Integer.parseInt;

public interface ShortestPathTools {
    int fromNameIndex = 0;
    int toNameIndex = 1;
    int priceIndex = 2;

    Function<String[], String> getFromName = array -> array[fromNameIndex];
    Function<String[], String> getToName = array -> array[toNameIndex];
    Function<String[], String> getPrice = array -> array[priceIndex];
    Function<String[], Integer> getPriceAsInt = array -> parseInt(getPrice.apply(array));
    BiPredicate<String, String> areEqual = String::equalsIgnoreCase;
}

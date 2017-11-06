package RedHatDev.models;

import java.util.ArrayList;
import java.util.List;


public class NumberFactorization {
    public List<Integer> getNumberFactors(final int upperBound) {

        int actualTop = upperBound;
        List<Integer> factors = new ArrayList<>();

        while (actualTop >= 2) {
            for (int i = 2; i <= upperBound; i++) {
                if (actualTop % i == 0) {
                    actualTop /= i;
                    factors.add(i);
                }
            }

            if (actualTop == 1) {
                factors.add(0, 1);
            }
        }

        return factors;
    }
}

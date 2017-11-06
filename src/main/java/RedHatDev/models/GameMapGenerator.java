package RedHatDev.models;

import java.util.Random;
import java.util.function.IntSupplier;

public class GameMapGenerator {

    private final char[] mapBuildParts = new char[] {'.', '*'};
    private final Random random = new Random();
    private final int partsAmount = 2;

    public char[][] generateMap(final int size) {

        IntSupplier randomNumber = () -> random.nextInt(partsAmount);
        char[][] map = new char[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = mapBuildParts[randomNumber.getAsInt()];
            }
        }

        return map;
    }
}

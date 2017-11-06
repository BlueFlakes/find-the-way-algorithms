package RedHatDev.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;

public class Point {
    private final static List<List<Integer>> winningPatterns = new ArrayList<>();
    private Integer x;
    private Integer y;

    static {
        winningPatterns.add(Arrays.asList(0, 0));
        winningPatterns.add(Arrays.asList(1, 0));
        winningPatterns.add(Arrays.asList(0, 1));
        winningPatterns.add(Arrays.asList(1, 1));
    }

    public Point(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer countBombsAround(char[][] map) {

        int mapSize = map.length;
        Integer bombsCounter = 0;

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {

                if (isPointValidToCount(map, i, j)) {
                    bombsCounter++;
                }
            }
        }

        return bombsCounter;
    }

    private boolean isPointValidToCount(char[][] map, int x, int y) {
        BiPredicate<Integer, Integer> checkIsNotMyPosition = (i, j) -> !(this.x.equals(i) && this.y.equals(j));
        BiPredicate<Integer, Integer> checkIsPointABomb = (i, j) -> map[i][j] == '*';

        return checkIsBombClose(x, y) && checkIsNotMyPosition.test(x, y) && checkIsPointABomb.test(x, y);
    }

    private boolean checkIsBombClose(int xPos, int yPos) {
        int xDiff = Math.abs(this.x - xPos);
        int yDiff = Math.abs(this.y - yPos);

        return winningPatterns.contains(Arrays.asList(xDiff, yDiff));
    }
}

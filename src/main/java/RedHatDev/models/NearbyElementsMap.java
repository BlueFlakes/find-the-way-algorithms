package RedHatDev.models;

import RedHatDev.exceptions.IndexOutOfBoundsException;

import java.util.stream.IntStream;

public class NearbyElementsMap {
    private int[][] map;

    public NearbyElementsMap(int[][] map) {
        this.map = map;
    }

    public int[] nearby(final int x, final int y, final int range) throws IndexOutOfBoundsException {
        isThereAnExpectedRow(x);

        int lowerBand = y - range - 1;
        int upperBand = y + range + 1;
        int rowRange = this.map[x].length;

        return IntStream.range(0, rowRange)
                        .filter(i -> i > lowerBand && i < upperBand && i != y)
                        .map(i -> this.map[x][i])
                        .toArray();
    }

    private void isThereAnExpectedRow(int x) throws IndexOutOfBoundsException {
        if (x > this.map.length - 1)
            throw new IndexOutOfBoundsException("Requested row out of available array bounds.");
    }
}
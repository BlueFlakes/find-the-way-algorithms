package RedHatDev.controllers;

import RedHatDev.exceptions.CustomIndexOutOfBoundsException;
import RedHatDev.models.NearbyElementsMap;

import java.util.Arrays;

public class NearbyElements {

    public void run() {
        int[][] multi = new int[][]{
                { 2, 0, 4, 1241, 12, 5, 11, 1110, -42, 424, 1, 12323 },
                { 1, 3, 5, 7 },
                { 321, 320, 32, 3, 41241, -11, -12, -13, -66, -688 }
        };

        NearbyElementsMap map = new NearbyElementsMap(multi);

        try {
            System.out.println(Arrays.toString(map.nearby(0,2,2)));
        } catch (CustomIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }

    }
}

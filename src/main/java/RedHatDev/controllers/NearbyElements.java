package RedHatDev.controllers;

import RedHatDev.exceptions.CustomIndexOutOfBoundsException;
import RedHatDev.models.NearbyElementsMap;
import RedHatDev.views.UserInterface;

import java.util.Arrays;

public class NearbyElements {
    private UserInterface userInterface = new UserInterface();

    public void run() {
        int[][] multi = new int[][]{
                { 2, 0, 4, 1241, 12, 5, 11, 1110, -42, 424, 1, 12323 },
                { 1, 3, 5, 7 },
                { 321, 320, 32, 3, 41241, -11, -12, -13, -66, -688 }
        };

        NearbyElementsMap map = new NearbyElementsMap(multi);

        try {
            userInterface.println("For: x = 0, y = 2, range = 2");
            userInterface.println(Arrays.toString(map.nearby(0,2,2)));

        } catch (CustomIndexOutOfBoundsException e) {
            userInterface.println(e.getMessage());
        }

    }
}

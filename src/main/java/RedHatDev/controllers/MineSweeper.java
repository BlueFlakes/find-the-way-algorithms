package RedHatDev.controllers;

import RedHatDev.models.GameMapGenerator;
import RedHatDev.models.Point;
import RedHatDev.views.UserInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class MineSweeper {
    private GameMapGenerator mapGenerator = new GameMapGenerator();
    private UserInterface userInterface = new UserInterface();

    public static void main(String[] args) {
        MineSweeper mineSweeper = new MineSweeper();
        mineSweeper.run();
    }

    public void run() {
        char[][] givenMap = mapGenerator.generateMap(5);
        List<List<String>> calculatedMap = new ArrayList<>();
        userInterface.showGameMap(givenMap);

        int mapSize = givenMap.length;

        for (int i = 0; i < mapSize; i++) {
            List<String> row = new ArrayList<>();

            for (int j = 0; j < mapSize; j++) {

                if (givenMap[i][j] == '.') {
                    Point point = new Point(i, j);
                    row.add(String.valueOf(point.countBombsAround(givenMap)));
                } else {
                    row.add(String.valueOf(givenMap[i][j]));
                }
            }

            calculatedMap.add(row);
        }

        userInterface.showGameMap(calculatedMap);
    }
}

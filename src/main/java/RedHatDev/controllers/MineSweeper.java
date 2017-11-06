package RedHatDev.controllers;

import RedHatDev.abstraction.CustomRunnable;
import RedHatDev.models.GameMapGenerator;
import RedHatDev.models.Point;
import RedHatDev.views.UserInterface;

import java.util.ArrayList;
import java.util.List;

public class MineSweeper {
    private GameMapGenerator mapGenerator = new GameMapGenerator();
    private UserInterface userInterface = new UserInterface();

    public void run() {
        String message = "Please provide size(~as positive int) for gameboard: ";

        userInterface.getIntegerInput(message)
                     .<CustomRunnable>map(n -> () -> this.start(n))
                     .orElse(() -> System.out.println("Wrong input, try later"))
                     .run();
    }

    private void start(int mapSize) {
        if (mapSize < 0)
            throw new IllegalStateException("Unacceptable input delivered.");

        char[][] givenMap = mapGenerator.generateMap(mapSize);
        List<List<String>> calculatedMap = new ArrayList<>();
        userInterface.showGameMap(givenMap);

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

        userInterface.println("\n - - - - - - - - - - - - - - - - - - - -\n");
        userInterface.showGameMap(calculatedMap);
    }
}

package RedHatDev.views;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserInterface {

    public void showGameMap(char[][] gameMap) {
        Arrays.stream(gameMap)
                .forEach(System.out::println);
    }

    public void showGameMap(List<List<String>> gameMap) {
        gameMap.stream()
                .map(row -> row.stream()
                               .collect(Collectors.joining("")))
                .forEachOrdered(System.out::println);
    }
}


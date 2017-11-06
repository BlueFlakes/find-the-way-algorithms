package RedHatDev.views;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class UserInterface {
    public final Inputs inputs = new Inputs();

    public void println(String message) {
        System.out.println(message);
    }

    public void print(String message) {
        System.out.print(message);
    }

    public void showGameMap(char[][] gameMap) {
        Arrays.stream(gameMap)
                .forEach(System.out::println);
    }

    public void showGameMap(List<List<String>> gameMap) {
        gameMap.stream()
                .map(row -> row.stream()
                               .collect(Collectors.joining(" ")))
                .forEachOrdered(System.out::println);
    }

    public Optional<Integer> getIntegerInput(String question) {
        String userChoice = inputs.getChoice(question);
        return isInteger(userChoice) ? Optional.of(parseInt(userChoice))
                                     : Optional.empty();
    }

    public static boolean isInteger(String input) {
        Pattern pattern = Pattern.compile("^-?\\d+$");

        return pattern.matcher(input)
                      .matches();
    }
}


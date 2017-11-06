package RedHatDev.controllers;

import RedHatDev.views.UserInterface;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class RequestHandler {
    private UserInterface userInterface = new UserInterface();

    public void run( ) {
        Optional<Integer> userInput;
        String question = "Dear user please deliver your choice: ";
        Predicate<Integer> userInputInBounds = n -> n > 0 && n <= 3;
        int defaultValue = 0;

        do {
            showAvailablePossibilities();
            userInput = userInterface.getIntegerInput(question);

        } while (!userInput.isPresent() || !userInputInBounds.test(userInput.orElse(defaultValue)));

        handleChoice(userInput.get());
    }

    private void handleChoice(Integer choice) {

        switch (choice) {
            case 1:
                runMineSweeper();
                break;

            case 2:
                runNearbyElements();
                break;

            case 3:
                runNumberFactorization();
                break;
        }
    }


    private void runMineSweeper() {
        MineSweeper mineSweeper = new MineSweeper();
        mineSweeper.run();
    }

    private void runNearbyElements() {
        NearbyElements nearbyElements = new NearbyElements();
        nearbyElements.run();
    }

    private void runNumberFactorization() {
        NumberFactorizationController controller = new NumberFactorizationController();
        controller.run();
    }

    private void showAvailablePossibilities() {
        final String[] menu = new String[] {"Show MineSweeper", "Show nearby elements", "Number factorization "};
        BiFunction<Integer, String, String> menuFormat = (idx, message) -> String.format("%s %d. %s", "\t", idx + 1, message);

        IntStream.range(0, menu.length)
                 .mapToObj(i -> menuFormat.apply(i, menu[i]))
                 .forEachOrdered(System.out::println);
    }



}

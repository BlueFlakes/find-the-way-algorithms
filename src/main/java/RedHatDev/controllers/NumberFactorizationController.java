package RedHatDev.controllers;

import RedHatDev.abstraction.CustomRunnable;
import RedHatDev.models.NumberFactorization;
import RedHatDev.views.UserInterface;

import java.util.List;
import java.util.stream.Collectors;

public class NumberFactorizationController {
    private NumberFactorization factorization = new NumberFactorization();
    private UserInterface userInterface = new UserInterface();

    public void run() {

        String message = "Please provide number to factorize: ";

        userInterface.getIntegerInput(message)
                     .<CustomRunnable>map(n -> () -> this.start(n))
                     .orElse(() -> System.out.println("Wrong input, try later"))
                     .run();

    }

    public void start(int numberToFactorize) {
        List<Integer> score = factorization.getNumberFactors(numberToFactorize);
        userInterface.println("Given number: " + numberToFactorize);
        userInterface.print("number factors: ");

        userInterface.println(score.stream()
                                   .map(String::valueOf)
                                   .collect(Collectors.joining(" ")));
    }
}

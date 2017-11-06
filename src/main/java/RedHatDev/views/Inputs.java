package RedHatDev.views;

import java.util.Scanner;

public class Inputs {
    private final Scanner in = new Scanner(System.in);

    public String getChoice(String question) {
        System.out.print(question);
        return in.nextLine();
    }
}

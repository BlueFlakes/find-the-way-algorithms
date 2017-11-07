package RedHatDev.controllers;

import RedHatDev.views.UserInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;

public class SHPcontroller {
    private final UserInterface userInterface = new UserInterface();
    private final ValidateDataForAccumulator validator = new ValidateDataForAccumulator();
    private final String formatDelimiter = "-";

    public void run() {
        accumulateData();
    }

    public static void main(String[] args) {
        SHPcontroller shPcontroller = new SHPcontroller();
        shPcontroller.run();
    }

    private List<String[]> accumulateData() {
        final List<String[]> accumulator = new ArrayList<>();
        final String stopAccumulationIndicator = "end";
        boolean isAccumulationFinished;
        String userChoice;

        do {
            String message = "Deliver data in from-to-price format (write \""
                    + stopAccumulationIndicator
                    + "\" to finish) format: ";

            userChoice = userInterface.inputs.getChoice(message);

            isAccumulationFinished = userChoice.equalsIgnoreCase(stopAccumulationIndicator);

            if (!isAccumulationFinished) {
                handleDataAccumulation(userChoice, accumulator);
            }
            
        } while (!isAccumulationFinished);

        return accumulator;
    }

    private void handleDataAccumulation(String userChoice, List<String[]> accumulator) {
        boolean isValid = validateGivenChoice(userChoice);

        if (isValid) {
            String[] deliveredInputs = userChoice.split(formatDelimiter);
            accumulator.add(deliveredInputs);

        } else {
            userInterface.println("Invalid data provided.\n");
        }
    }

    private boolean validateGivenChoice(String userChoice) {
        return validator.isDeliveredInputCorrect(userChoice);
    }

    private class ValidateDataForAccumulator {

        private String userChoice;
        private String[] deliveredData;
        private final int priceIndex = 2;

        public boolean isDeliveredInputCorrect(String userChoice) {
            this.userChoice = userChoice;
            this.deliveredData = this.userChoice.split(formatDelimiter);

            return wasDataTestSuccesful();
        }

        private boolean wasDataTestSuccesful() {

            if (!isChoiceDeliveredInExpectedFormat()) return false;
            if (!isAtLeastOneSignInEveryInput()) return false;
            if (!isThirdInputInteger()) return false;
            if (!isThirdInputNonNegative()) return false;
            
            return true;
        }

        private boolean isChoiceDeliveredInExpectedFormat() {
            int expectedAmountOfInputs = 3;
            int deliveredDataAmount = this.deliveredData.length;

            return deliveredDataAmount == expectedAmountOfInputs;
        }

        private boolean isAtLeastOneSignInEveryInput() {
            return Arrays.stream(this.deliveredData)
                         .noneMatch(String::isEmpty);
        }
        
        private boolean isThirdInputInteger() {
            String price = this.deliveredData[priceIndex];
            
            return UserInterface.isInteger(price);
        }
        
        private boolean isThirdInputNonNegative() {
            int price = parseInt(this.deliveredData[priceIndex]);
            
            return price >= 0;
        }
    }
}

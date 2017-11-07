package RedHatDev.controllers;

import RedHatDev.exceptions.CustomInvalidArgumentException;
import RedHatDev.views.UserInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class SHPcontroller {
    private final UserInterface userInterface = new UserInterface();
    private final ValidateDataForAccumulator validator = new ValidateDataForAccumulator();
    private final String formatDelimiter = " ";

    public void run() {
        List<String[]> data = accumulateData();

    }

    public static void main(String[] args) {
        SHPcontroller shPcontroller = new SHPcontroller();
        shPcontroller.run();
    }

    private List<String[]> accumulateData() {
        final List<String[]> accumulator = new ArrayList<>();
        String clearedData;

        do {
            userInterface.println("Deliver data in \"from to price\" format to continue or \"from to\" format to finish.");
            String userChoice = userInterface.inputs.getChoice("Delivered format: ");
            clearedData = deleteRedundantBlankSpacesBetweenInputs(userChoice);

            handleDataAccumulation(clearedData, accumulator);

        } while (!IsAccumulationFinished(clearedData));

        return accumulator;
    }

    private String deleteRedundantBlankSpacesBetweenInputs(String userChoice) {
        String[] givenData = userChoice.split(formatDelimiter);

        return Arrays.stream(givenData)
                     .filter(s -> !s.isEmpty())
                     .collect(Collectors.joining(formatDelimiter));
    }

    private void handleDataAccumulation(String userChoice, List<String[]> accumulator) {


        try {
            validateGivenChoice(userChoice, accumulator);

            String[] deliveredInputs = userChoice.split(formatDelimiter);
            accumulator.add(deliveredInputs);
            userInterface.println("Correct!\n");

        } catch (CustomInvalidArgumentException e) {
            String errorMessage = String.format("\nInvalid data provided | %s \n", e.getMessage());
            userInterface.println(errorMessage);
        }
    }

    private void validateGivenChoice(String userChoice, List<String[]> accumulator)
            throws CustomInvalidArgumentException {

        validator.isDeliveredInputCorrect(userChoice, accumulator);
    }

    private boolean IsAccumulationFinished(String userChoice) {
        int fromToFormatAmountOfInputs = 2;
        int amountOfDeliveredInputs = userChoice.split(formatDelimiter).length;

        return amountOfDeliveredInputs == fromToFormatAmountOfInputs;
    }

    private class ValidateDataForAccumulator {

        private String userChoice;
        private String[] deliveredData;
        private List<String[]> accumulator;
        private final int priceIndex = 2;

        public void isDeliveredInputCorrect(String userChoice, List<String[]> accumulator)
                throws CustomInvalidArgumentException {

            this.userChoice = userChoice;
            this.deliveredData = this.userChoice.split(formatDelimiter);
            this.accumulator = accumulator;

            runDataTest();
        }

        private void runDataTest() throws CustomInvalidArgumentException {

            if (!isChoiceDeliveredInExpectedFormat())
                throw new CustomInvalidArgumentException("~ Invalid amount of inputs provided. Expected 2 or 3.");

            if (!isAtLeastOneSignInEveryInput())
                throw new CustomInvalidArgumentException("~ Expected at least one sign per input.");

            if (isThirdElementPresent()) {
                if (!isThirdPriceCorrect())
                    throw new CustomInvalidArgumentException("~ Expected price as positive Integer");
            }

            if (!isGivenFromToSetupUnique())
                throw new CustomInvalidArgumentException("~ \"From-to\" setup should be unique");

        }

        private boolean isChoiceDeliveredInExpectedFormat() {
            List<Integer> expectedAmountOfInputs = Arrays.asList(2, 3);
            int deliveredDataAmount = this.deliveredData.length;

            return expectedAmountOfInputs.contains(deliveredDataAmount);
        }

        private boolean isAtLeastOneSignInEveryInput() {
            return Arrays.stream(this.deliveredData)
                         .noneMatch(String::isEmpty);
        }

        private boolean isThirdElementPresent() {
            int deliveredDataAmount = this.deliveredData.length;

            return deliveredDataAmount == 3;
        }

        private boolean isThirdPriceCorrect() {
            if (!isThirdInputInteger()) return false;
            if (!isThirdInputPositiveNumber()) return false;

            return true;
        }

        private boolean isThirdInputInteger() {
            String price = this.deliveredData[priceIndex];
            
            return UserInterface.isInteger(price);
        }
        
        private boolean isThirdInputPositiveNumber() {
            int price = parseInt(this.deliveredData[priceIndex]);
            
            return price > 0;
        }

        private boolean isGivenFromToSetupUnique() {
            final int fromIndex = 0;
            final int toIndex = 1;

            String givenFromName = this.deliveredData[fromIndex];
            String givenToName = this.deliveredData[toIndex];

            Function<String[], String> getFromName = array -> array[fromIndex];
            Function<String[], String> getToName = array -> array[toIndex];
            BiPredicate<String, String> areEqual = String::equalsIgnoreCase;

            for (String[] record : this.accumulator) {
                String foundFromName = getFromName.apply(record);
                String foundToName = getToName.apply(record);

                if (areEqual.test(givenFromName, foundFromName) && areEqual.test(givenToName, foundToName)) {
                    return false;
                }
            }

            return true;
        }
    }
}

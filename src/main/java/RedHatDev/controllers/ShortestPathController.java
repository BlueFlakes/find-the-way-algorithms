package RedHatDev.controllers;

import RedHatDev.abstraction.ShortestPathTools;
import RedHatDev.exceptions.CustomInvalidArgumentException;
import RedHatDev.models.Collections;
import RedHatDev.models.Dijkstra;
import RedHatDev.models.Graph;
import RedHatDev.models.Node;
import RedHatDev.views.UserInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class ShortestPathController implements ShortestPathTools {

    private final UserInterface userInterface = new UserInterface();
    private final ValidateDataForAccumulator validator = new ValidateDataForAccumulator();
    private final String formatDelimiter = " ";

    public void run() {

        List<String[]> data = accumulateData();

        String[] fromToData = withdrawStartAndTargetFromData(data);
        Graph graph = new Graph(data);

        String sourceFromName = getFromName.apply(fromToData);
        String sourceToName = getToName.apply(fromToData);

        try {
            Node fromNode = graph.findNodeByName(sourceFromName);
            Node toNode = graph.findNodeByName(sourceToName);

            Dijkstra.calculateShortestPathFrom(fromNode);
            if (toNode.getDistance() != Integer.MAX_VALUE) {
                presentCalculations(toNode);
            } else {
                userInterface.println("Sorry no connection between.");
            }
        } catch (NoSuchElementException e) {
            userInterface.println("Sorry but no found given source or target.");
        }
    }

    private void presentCalculations(Node toNode) {
        userInterface.println("Price = " + toNode.getDistance());
        userInterface.print("Path: ");
        userInterface.println(getPath(toNode));
    }

    private String[] withdrawStartAndTargetFromData(List<String[]> data) {
        String[] fromToData = Collections.getLast(data).get();
        data.remove(fromToData);

        return fromToData;
    }

    private String getPath(Node toNode) {
        List<String> path = toNode.getShortestPath().stream().map(Node::getName).collect(Collectors.toList());
        path.add(toNode.getName());

        return String.join(" -> ", path);
    }

    private List<String[]> loadSampleData() {
        List<String> temp = new ArrayList<>();
        temp.add("A B 10");
        temp.add("A C 15");
        temp.add("B D 12");
        temp.add("B F 15");
        temp.add("D F 1");
        temp.add("F E 5");
        temp.add("D E 2");
        temp.add("C E 10");
        temp.add("A E");

        return temp.stream()
                   .map(s -> s.split(formatDelimiter))
                   .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        ShortestPathController shPcontroller = new ShortestPathController();
        shPcontroller.run();
    }

    private List<String[]> accumulateData() {
        final List<String[]> accumulator = new ArrayList<>();
        boolean wasAccumulationSuccesfully;
        String clearedData;

        do {
            userInterface.println("Deliver data in \"from to price\" format to continue or \"from to\" format to finish.");
            String userChoice = userInterface.inputs.getChoice("Delivered format: ");
            clearedData = deleteRedundantBlankSpacesBetweenInputs(userChoice);

            wasAccumulationSuccesfully = handleDataAccumulation(clearedData, accumulator);

        } while (!IsAccumulationFinished(clearedData, wasAccumulationSuccesfully));

        return accumulator;
    }

    private String deleteRedundantBlankSpacesBetweenInputs(String userChoice) {
        String[] givenData = userChoice.split(formatDelimiter);

        return Arrays.stream(givenData)
                     .filter(s -> !s.isEmpty())
                     .collect(Collectors.joining(formatDelimiter));
    }

    private boolean handleDataAccumulation(String userChoice, List<String[]> accumulator) {

        try {
            validateGivenChoice(userChoice, accumulator);

            String[] deliveredInputs = userChoice.split(formatDelimiter);
            accumulator.add(deliveredInputs);
            userInterface.println("Correct!\n");

            return true;

        } catch (CustomInvalidArgumentException e) {
            String errorMessage = String.format("\nInvalid data provided | %s \n", e.getMessage());
            userInterface.println(errorMessage);

            return false;
        }
    }

    private void validateGivenChoice(String userChoice, List<String[]> accumulator)
            throws CustomInvalidArgumentException {

        validator.isDeliveredInputCorrect(userChoice, accumulator);
    }

    private boolean IsAccumulationFinished(String userChoice, boolean wasAccumulationSuccesfully) {
        int fromToFormatAmountOfInputs = 2;
        int amountOfDeliveredInputs = userChoice.split(formatDelimiter).length;

        return amountOfDeliveredInputs == fromToFormatAmountOfInputs && wasAccumulationSuccesfully;
    }

    private class ValidateDataForAccumulator {

        private String userChoice;
        private String[] deliveredData;
        private List<String[]> accumulator;

        void isDeliveredInputCorrect(String userChoice, List<String[]> accumulator)
                throws CustomInvalidArgumentException {

            this.userChoice = userChoice;
            this.deliveredData = this.userChoice.split(formatDelimiter);
            this.accumulator = accumulator;

            runDataTest();
        }

        private void runDataTest() throws CustomInvalidArgumentException {

            if (!isChoiceDeliveredInExpectedFormat())
                throw new CustomInvalidArgumentException("~ Invalid amount of inputs provided. Expected 2 or 3.");

            if (!areDifferentFromNameAndToName())
                throw new CustomInvalidArgumentException("~ From and To should be different.");

            if (!isAtLeastOneSignInEveryInput())
                throw new CustomInvalidArgumentException("~ Expected at least one sign per input.");

            if (isThirdElementPresent()) {
                if (!isThirdPriceCorrect())
                    throw new CustomInvalidArgumentException("~ Expected price as positive Integer");

                if (!isGivenFromToSetupUnique())
                    throw new CustomInvalidArgumentException("~ \"From-to\" setup should be unique");
            }
        }

        private boolean isChoiceDeliveredInExpectedFormat() {
            List<Integer> expectedAmountOfInputs = Arrays.asList(2, 3);
            int deliveredDataAmount = this.deliveredData.length;

            return expectedAmountOfInputs.contains(deliveredDataAmount);
        }

        private boolean areDifferentFromNameAndToName() {
            return !areEqual.test(getThisFromName(), getThisToName());
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
            String price = getPrice();
            
            return UserInterface.isInteger(price);
        }
        
        private boolean isThirdInputPositiveNumber() {
            int price = parseInt(getPrice());
            
            return price > 0;
        }

        private String getPrice() {
            return getPrice.apply(this.deliveredData);
        }

        private String getThisFromName() {
            return getFromName.apply(this.deliveredData);
        }

        private String getThisToName() {
            return getToName.apply(this.deliveredData);
        }

        private boolean isGivenFromToSetupUnique() {

            String givenFromName = getThisFromName();
            String givenToName = getThisToName();

            for (String[] record : this.accumulator) {
                String foundFromName = getFromName.apply(record);
                String foundToName = getToName.apply(record);

                if ((areEqual.test(givenFromName, foundFromName) && areEqual.test(givenToName, foundToName))
                        || areEqual.test(givenFromName, foundToName) && areEqual.test(givenToName, foundFromName)) {

                    return false;
                }
            }

            return true;
        }
    }
}

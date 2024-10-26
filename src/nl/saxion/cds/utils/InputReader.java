package nl.saxion.cds.utils;

import nl.saxion.cds.solution.data_structures.MyArrayList;

import java.util.Scanner;

public class InputReader {
    private static final Scanner scanner = new Scanner(System.in);

    public static String askForText(String message){
        System.out.println(message);
        return scanner.nextLine();
    }

    public static String askForNonBlankText(String message){
        String userInput = askForText(message);
        while(userInput.isBlank()){
            System.err.println("Please provide a valid value. ");
            userInput = askForText(message);
        }

        return userInput;
    }

    public static String askForTextVariations(String message, MyArrayList<String> variations, boolean caseSpecific){
        String userInput = askForNonBlankText(message);
        boolean isValid = false;

        do {
            for (String variation : variations){
                if(caseSpecific){
                    if(userInput.equals(variation)){
                        isValid = true;
                    }
                } else {
                    if(userInput.equalsIgnoreCase(variation)){
                        isValid = true;
                    }
                }
            }
            if(!isValid){
                userInput = askForNonBlankText(message);
            }
        } while (!isValid);

        return userInput;
    }

    public static int askForNumber(String message){
        String userInput = askForNonBlankText(message);
        boolean isValid = false;
        int answer = 0;

        while(!isValid){
            try {
                answer = Integer.parseInt(userInput);
                isValid = true;
            } catch (NumberFormatException nfe){
                System.err.println(userInput + " is not a valid number value. ");
                userInput = askForNonBlankText(message);
            }
        }

        return answer;
    }

    public static Integer askForOptionalNumber(String message){
        String userInput = askForText(message + " (put '-' to leave blank)");
        if(userInput.equalsIgnoreCase("-" ) || userInput.isBlank()) return null;
        boolean isValid = false;
        Integer answer = null;

        while(!isValid){
            try {
                answer = Integer.parseInt(userInput);
                isValid = true;
            } catch (NumberFormatException nfe){
                System.err.println(userInput + " is not a valid number value. ");
                userInput = askForText(message);
            }
        }

        return answer;
    }

    public static int askForNumberWithinRange(String message, int max){
        int num = askForNumber(message);
        while(num > max || num < 0) {
            System.err.println("Provided value is outside the range");
            num = askForNumber(message);
        }

        return num;
    }

    public static int askForPositiveNumber(String message){
        int num = askForNumber(message);
        while(num < 0) {
            System.err.println("Provided value is outside the range");
            num = askForNumber(message);
        }

        return num;
    }

    public static int askForNumberWithinRange(String message, int min, int max){
        int num = askForNumber(message);
        while(num > max || num < min) {
            System.err.println("Provided value is outside the range");
            num = askForNumber(message);
        }

        return num;
    }

    public static double askForDouble(String message){
        String userInput = askForNonBlankText(message);
        boolean isValid = false;
        double answer = 0.0;

        while(!isValid){
            try {
                answer = Double.parseDouble(userInput);
                isValid = true;
            } catch (NumberFormatException nfe){
                System.err.println(userInput + " is not a valid double value. ");
                userInput = askForNonBlankText(message);
            }
        }

        return answer;
    }

    public static double askForDoubleWithinRange(String message, int min, int max){
        double num = askForDouble(message);
        while(num > max || num < min) {
            System.err.println("Provided value is outside the range");
            num = askForNumber(message);
        }

        return num;
    }

    public static double askForPositiveDouble(String message){
        double num = askForDouble(message);
        while(num < 0) {
            System.err.println("Provided value is negative. Value must be positive. ");
            num = askForNumber(message);
        }

        return num;
    }

    public static Double askForOptionalDouble(String message){
        String userInput = askForText(message + " (put '-' to leave blank)");
        if(userInput.equalsIgnoreCase("-")) return null;

        boolean isValid = false;
        double answer = 0.0;

        while(!isValid){
            try {
                answer = Double.parseDouble(userInput);
                isValid = true;
            } catch (NumberFormatException nfe){
                System.err.println(userInput + " is not a valid number value. ");
                userInput = askForText(message);
            }
        }

        return answer;
    }

    public static boolean askForBoolean(String message){
        String input = askForNonBlankText(message);

        boolean answer = false;
        boolean isValid = false;
        try {
            answer = Boolean.parseBoolean(input);
        } catch (Exception e){
            System.out.println(input + " is not a valid boolean");
            input = askForNonBlankText(message);
        }

        return answer;
    }
}

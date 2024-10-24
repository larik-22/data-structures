package nl.saxion.cds.utils;

import nl.saxion.cds.data_structures.MyArrayList;

public class OptionSelector {
    public static int selectOption(MyArrayList<String> options) {
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }

        int choice = -1;
        while (choice < 1 || choice > options.size()) {
            choice = InputReader.askForNumber("Select an option: ");
        }

        return choice;
    }
}

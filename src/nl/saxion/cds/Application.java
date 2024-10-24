package nl.saxion.cds;

import nl.saxion.cds.collection.KeyNotFoundException;
import nl.saxion.cds.data_structures.MyArrayList;
import nl.saxion.cds.utils.InputReader;
import nl.saxion.cds.utils.OptionSelector;

public class Application {
    private static final OptionSelector OPTION_SELECTOR = new OptionSelector();
    private static final MyArrayList<String> MENU_OPTIONS = new MyArrayList<>();
    private final StationManager stationManager = new StationManager();

    public Application() {
        MENU_OPTIONS.addLast("Show station by code");
        MENU_OPTIONS.addLast("Show station by part of the name");
        MENU_OPTIONS.addLast("Show sorted stations by type");
        MENU_OPTIONS.addLast("A-Star Demonstration");
        MENU_OPTIONS.addLast("Exit");
    }

    public static void main(String[] args) {
        new Application().run();
    }

    public void run(){
        while (true){
            int choice = promtMainMenu();
            switch (choice){
                case 1 -> {
                    String code = InputReader.askForNonBlankText("Enter station code: ");

                    try {
                        System.out.println(stationManager.showStationByCode(code));
                    } catch (KeyNotFoundException e){
                        System.out.printf("Station with code %s not found\n", code);
                    }
                }
                case 2 -> {

                }
                case 3 -> {

                }
                case 4 -> {

                }
                case 5 -> {

                }
            }
        }
    }

    public int promtMainMenu(){
        return OPTION_SELECTOR.selectOption(MENU_OPTIONS);
    }

}

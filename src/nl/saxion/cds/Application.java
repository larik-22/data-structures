package nl.saxion.cds;

import nl.saxion.cds.collection.KeyNotFoundException;
import nl.saxion.cds.solution.application.StationManager;
import nl.saxion.cds.solution.data_structures.MyArrayList;
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
                    String partOfName = InputReader.askForNonBlankText("Enter part of the station name: ");
                    stationManager.showStationsByName(partOfName);
                }
                case 3 -> {

                }
                case 4 -> {
                    String start = InputReader.askForNonBlankText("Enter start station code: ");
                    String end = InputReader.askForNonBlankText("Enter end station code: ");
                    new Thread(() -> {
                        try {
                            stationManager.aStarDemonstration(start, end);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid station codes");
                        }
                    }).start();
                }
                case 5 -> {
                    System.out.println("Goodbye!");
                    return;
                }
            }
        }
    }

    public int promtMainMenu(){
        return OPTION_SELECTOR.selectOption(MENU_OPTIONS);
    }

}

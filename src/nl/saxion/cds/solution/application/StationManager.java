package nl.saxion.cds.solution.application;

import nl.saxion.app.SaxionApp;
import nl.saxion.cds.collection.KeyNotFoundException;
import nl.saxion.cds.solution.data_structures.MyAVLTree;
import nl.saxion.cds.solution.data_structures.MyArrayList;
import nl.saxion.cds.solution.data_structures.MySpHashMap;
import nl.saxion.cds.solution.data_models.Station;
import nl.saxion.cds.utils.LambdaReader;
import nl.saxion.cds.utils.OptionSelector;

import java.io.FileNotFoundException;
import java.util.Comparator;

public class StationManager {
    private MyArrayList<Station> stationsMyArrayList;
    private MySpHashMap<String, Station> stationMySpHashMap; // code / station
    private MyAVLTree<String, Station> stationMyAVLTree; // name / station
//    private MyAdjacencyListGraph<String>

    public StationManager() {
        loadStations();
    }

    /**
     * Load stations from the CSV file using the LambdaReader
     * @throws RuntimeException if the file is not found
     */
    private void loadStations(){
        fillArrayList();
        fillHashMap();
        fillTree();
    }

    /**
     * Fill the stationMyArrayList with the stations from the CSV file
     */
    private void fillArrayList(){
        try {
            LambdaReader<Station> lambdaReader = new LambdaReader<Station>(
                    "resources/stations.csv",
                    true,
                    Station::parseStation,
                    ","
            );

            stationsMyArrayList = lambdaReader.readObjects();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Fills the hashmap with the stations, with key being the station code
     * and value being the station object
     */
    private void fillHashMap(){
        if(stationsMyArrayList.isEmpty()) return;

        stationMySpHashMap = new MySpHashMap<>();
        for (Station station : stationsMyArrayList) {
            stationMySpHashMap.add(station.getCode(), station);
        }
    }

    /**
     * Fills the AVL tree with the stations, with key being the station name
     * and value being the station object
     */
    private void fillTree(){
        if(stationsMyArrayList.isEmpty()) return;

        stationMyAVLTree = new MyAVLTree<>(String::compareTo);

        for (Station station : stationsMyArrayList) {
            stationMyAVLTree.add(station.getName(), station);
        }
    }

    /**
     * Shows the station by code.
     * Hashmap is the most efficient way to do this
     */
    public Station showStationByCode(String code) {
        code = code.toUpperCase();
        return stationMySpHashMap.get(code);
    }

    /**
     * Shows the stations by part of the name.
     * @param partOfName the part of the name to search for
     */
    public void showStationsByName(String partOfName) {
        partOfName = partOfName.toLowerCase();
        MyArrayList<Station> matchingStations = new MyArrayList<>();

        for (Station station : stationsMyArrayList) {
            if (station.getName().toLowerCase().contains(partOfName)) {
                matchingStations.addLast(station);
            }
        }

        if (matchingStations.isEmpty()) {
            System.out.println("No stations found");
        } else {
            // We get station names to show to the user
            MyArrayList<String> stationNames = new MyArrayList<>();
            for (Station station : matchingStations) {
                stationNames.addLast(station.getName());
            }

            // We sort the station names to show them in alphabetical order
            stationNames.quickSort(String::compareTo);
            matchingStations.quickSort(Comparator.comparing(Station::getName));

            // We prompt user to select a station and show detailed information
            int choice = OptionSelector.selectOption(stationNames);
            System.out.println(matchingStations.get(choice - 1));

        }
    }

    public void aStarDemonstration(String start, String end) {
        start = start.toUpperCase();
        end = end.toUpperCase();

        try {
            stationMySpHashMap.get(start);
            stationMySpHashMap.get(end);

            // run a runnable with those args
            AStarDemo aStarDemo = new AStarDemo(start, end);
            SaxionApp.start(aStarDemo, AStarDemo.WINDOW_WIDTH, AStarDemo.WINDOW_HEIGHT);
        } catch (KeyNotFoundException e){
            throw new IllegalArgumentException("Invalid station codes");
        }
    }
}

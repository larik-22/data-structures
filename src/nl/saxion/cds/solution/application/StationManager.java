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
    private MySpHashMap<String, MyArrayList<Station>> stationTypeHashMap; // type / list of stations

    public StationManager() {
        loadStations();
    }

    /**
     * Load stations from the CSV file using the LambdaReader
     * @throws RuntimeException if the file is not found
     */
    private void loadStations(){
        fillArrayList();
        fillHashMaps();
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
    private void fillHashMaps(){
        if(stationsMyArrayList.isEmpty()) return;

        stationMySpHashMap = new MySpHashMap<>();
        stationTypeHashMap = new MySpHashMap<>();

        for (Station station : stationsMyArrayList) {
            // fill the hash map representing the code and station
            stationMySpHashMap.add(station.getCode(), station);

            // fill the hash map representing the type and list of stations
            if(stationTypeHashMap.contains(station.getType())){
                stationTypeHashMap.get(station.getType()).addLast(station);
            } else {
                MyArrayList<Station> stations = new MyArrayList<>();
                stations.addLast(station);
                stationTypeHashMap.add(station.getType(), stations);
            }
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
        if (stationsMyArrayList.isEmpty()) return null;

        code = code.toUpperCase();
        return stationMySpHashMap.get(code);
    }

    /**
     * Shows the stations by part of the name.
     * @param partOfName the part of the name to search for
     */
    public void showStationsByName(String partOfName) {
        if(stationsMyArrayList.isEmpty()) return;

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

    /**
     * Shows the stations sorted by type.
     * Stations are sorted based on the station name
     */
    public void showSortedStationsByType(){
        if (stationsMyArrayList.isEmpty()) return;

        MyArrayList<String> types = (MyArrayList<String>) stationTypeHashMap.getKeys();
        types.quickSort(String::compareTo);

        // let the user select which type of station they want to see
        int choice = OptionSelector.selectOption(types);

        // sort the stations by name
        MyArrayList<Station> stations = stationTypeHashMap.get(types.get(choice - 1));
        stations.quickSort(Comparator.comparing(Station::getName));

        // print the stations
        StringBuilder sb = new StringBuilder();
        for (Station station : stations) {
            sb.append(station).append("\n");
        }
        System.out.println(sb);
    }

    /**
     * Demonstrates the A* algorithm using the given start and end station codes
     * @param start the start station code
     * @param end the end station code
     ** @throws IllegalArgumentException if the station codes are invalid
     */
    public void aStarDemonstration(String start, String end) {
        start = start.toUpperCase();
        end = end.toUpperCase();

        try {
            stationMySpHashMap.get(start);
            stationMySpHashMap.get(end);

            // run a runnable with those args
            SaxionAppDemo aStarDemo = new SaxionAppDemo(start, end);
            SaxionApp.start(aStarDemo, SaxionAppDemo.WINDOW_WIDTH, SaxionAppDemo.WINDOW_HEIGHT);
        } catch (KeyNotFoundException e){
            throw new IllegalArgumentException("Invalid station codes");
        }
    }
}

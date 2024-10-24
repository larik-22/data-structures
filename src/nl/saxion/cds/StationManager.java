package nl.saxion.cds;

import nl.saxion.cds.data_structures.MyArrayList;
import nl.saxion.cds.data_structures.MySpHashMap;
import nl.saxion.cds.model.Station;
import nl.saxion.cds.utils.LambdaReader;

import java.io.FileNotFoundException;

public class StationManager {
    private MyArrayList<Station> stationMyArrayList;
    private MySpHashMap<String, Station> stationMySpHashMap; // code / station

    public MyArrayList<Station> getStationMyArrayList() {
        return stationMyArrayList;
    }

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

            stationMyArrayList = lambdaReader.readObjects();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Fills the hashmap with the stations, with key being the station code
     * and value being the station object
     */
    private void fillHashMap(){
        if(stationMyArrayList.isEmpty()) return;

        stationMySpHashMap = new MySpHashMap<>();
        for (Station station : stationMyArrayList) {
            stationMySpHashMap.add(station.getCode(), station);
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
}

package nl.saxion.cds;

import nl.saxion.cds.data_structures.MyArrayList;
import nl.saxion.cds.model.Station;
import nl.saxion.cds.utils.LambdaReader;

import java.io.FileNotFoundException;

public class StationManager {
    private MyArrayList<Station> stations;

    public MyArrayList<Station> getStations() {
        return stations;
    }

    public StationManager() {
        loadStations();
    }

    /**
     * Load stations from the CSV file using the LambdaReader
     * @throws RuntimeException if the file is not found
     */
    private void loadStations(){
        try {
            LambdaReader<Station> lambdaReader = new LambdaReader<Station>(
                    "resources/stations.csv",
                    true,
                    Station::parseStation,
                    ","
            );

            stations = lambdaReader.readObjects();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

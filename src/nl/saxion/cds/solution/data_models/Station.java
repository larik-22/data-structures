package nl.saxion.cds.solution.data_models;

import nl.saxion.cds.solution.data_structures.MyArrayList;
import nl.saxion.cds.utils.LambdaReader;

import java.io.FileNotFoundException;

public class Station {
    private final String code, name, country, type;

    private final double latitude, longitude;
    private final Coordinate coordinate;

    public Station(String code, String name, String country, String type, double latitude, double longitude) {
        if(code == null || code.isBlank()) throw new IllegalArgumentException("Invalid code");
        if(name == null || name.isBlank()) throw new IllegalArgumentException("Invalid name");
        if(country == null || country.isBlank()) throw new IllegalArgumentException("Invalid country");
        if(type == null || type.isBlank()) throw new IllegalArgumentException("Invalid type");

        this.code = code;
        this.name = name;
        this.country = country;
        this.type = type;

        this.latitude = latitude;
        this.longitude = longitude;

        this.coordinate = new Coordinate(latitude, longitude);
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getType() {
        return type;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public static Station parseStation(MyArrayList<String> data){
        return new Station(data.get(0), data.get(1), data.get(2), data.get(3), Double.parseDouble(data.get(4)), Double.parseDouble(data.get(5)));
    }

    /**
     * Reads the stations from the CSV file
     * @return a list of stations
     * @throws RuntimeException if the file is not found
     */
    public static MyArrayList<Station> readStations(){
        try {
            LambdaReader<Station> lambdaReader = new LambdaReader<Station>(
                    "resources/stations.csv",
                    true,
                    Station::parseStation,
                    ","
            );

             return lambdaReader.readObjects();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Station Information: [")
                .append("Code: ").append(code).append(", ")
                .append("Name: ").append(name).append(", ")
                .append("Country: ").append(country).append(", ")
                .append("Type: ").append(type).append(", ")
                .append("Latitude: ").append(latitude).append(", ")
                .append("Longitude: ").append(longitude).append(", ")
                .append("Coordinate: ").append(coordinate)
                .append("]")
                .toString();
    }
}

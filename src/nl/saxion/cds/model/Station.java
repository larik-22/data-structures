package nl.saxion.cds.model;

import nl.saxion.cds.data_structures.MyArrayList;

public class Station {
    private final String code, name, country, type;

    private final double latitude, longitude;

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
    }

    public static Station parseStation(MyArrayList<String> data){
        return new Station(data.get(0), data.get(1), data.get(2), data.get(3), Double.parseDouble(data.get(4)), Double.parseDouble(data.get(5)));
    }
}

package nl.saxion.cds;

public class Application {
    //create menu and start the application (while loop with input parsing)
    public static void main(String[] args) {
        StationManager stationManager = new StationManager();
        System.out.println(stationManager.getStations().size());
    }
}

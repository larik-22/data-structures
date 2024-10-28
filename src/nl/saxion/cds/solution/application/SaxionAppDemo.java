package nl.saxion.cds.solution.application;

import nl.saxion.app.SaxionApp;
import nl.saxion.cds.collection.SaxGraph;
import nl.saxion.cds.collection.SaxList;
import nl.saxion.cds.solution.data_models.Station;
import nl.saxion.cds.solution.data_models.Coordinate;
import nl.saxion.cds.solution.data_structures.MyAdjacencyListGraph;
import nl.saxion.cds.solution.data_structures.MyArrayList;
import nl.saxion.cds.solution.data_structures.MySpHashMap;
import nl.saxion.cds.utils.CoordinateConverter;
import nl.saxion.cds.utils.CsvReader;
import nl.saxion.cds.utils.LambdaReader;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.FileNotFoundException;

public class SaxionAppDemo implements Runnable {
    public final static int WINDOW_WIDTH = 1020;
    public final static int WINDOW_HEIGHT = 818;

    private final static int IMAGE_WIDTH = 1621;
    private final static int IMAGE_HEIGHT = 1920;
    private final static int BANNER_HEIGHT = 38;
    private final static double DRAW_SLEEP = 0.001;
    private final static int DOT_SIZE = 4;

    private final CoordinateConverter converter = new CoordinateConverter();
    private final MySpHashMap<String, Station> stations;
    private final MyAdjacencyListGraph<String> tracks;

    private final String from;
    private final String to;

    public static void main(String[] args) {
        SaxionApp.start(new SaxionAppDemo("MT", "GN"), WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    public SaxionAppDemo(String from, String to) {
        stations = new MySpHashMap<>();
        tracks = new MyAdjacencyListGraph<>();

        this.from = from;
        this.to = to;
    }

    @Override
    public void run() {
        try {
            initApplication();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void initApplication() throws FileNotFoundException {
        initMap();
        readStations();
        readAndDrawTracks();
        drawStations();
        drawShortestPath();
    }

    /**
     * Draws a map and resizes it to fit
     * the window while maintaining the aspect ratio.
     */
    private void initMap() {
        double aspectRatio = (double) IMAGE_WIDTH / IMAGE_HEIGHT;

        // Calculate the new dimensions to fit the image within the window while maintaining the aspect ratio
        int mapWidth = WINDOW_WIDTH;
        int mapHeight = (int) ((WINDOW_WIDTH / aspectRatio) - BANNER_HEIGHT);

        if (mapHeight > (WINDOW_HEIGHT - BANNER_HEIGHT)) {
            mapHeight = WINDOW_HEIGHT - BANNER_HEIGHT;
            mapWidth = (int) (mapHeight * aspectRatio);
        }

        // Resize the window and draw the map
        SaxionApp.resize(mapWidth, mapHeight + BANNER_HEIGHT);
        SaxionApp.drawImage("resources/Nederland.png", 0, 0, mapWidth, mapHeight);
    }


    //TODO: finish
    /**
     * Clear the screen and re-draws the map with tracks and stations
     */
    private void clearScreen(){
        SaxionApp.clear();
    }

    /**
     * Reads the data from stations.csv on start up
     */
    private void readStations() throws FileNotFoundException {
        LambdaReader<Station> reader = new LambdaReader<>(
                "resources/stations.csv",
                true,
                Station::parseStation,
                ","
        );
        MyArrayList<Station> stationList = reader.readObjects();

        for (Station station : stationList) {
            stations.add(station.getCode(), station);
        }

        reader.close();
    }

    /**
     * Read tracks from the CSV file.
     * If I were to save them into graph and draw them on the map, I would need
     * to traverse them, which breaks the order of the tracks and the drawing result.
     * @throws FileNotFoundException if the file is not found
     */
    private void readAndDrawTracks() throws FileNotFoundException {
        SaxionApp.setBorderColor(Color.decode("#f3f1b2"));
        CsvReader reader = new CsvReader("resources/tracks.csv", true);
        reader.setSeparator(",");

        while (reader.readLine()) {
            SaxionApp.sleep(DRAW_SLEEP);
            String from = reader.readString(0);
            String to = reader.readString(1);
            tracks.addEdge(from, to, reader.readDouble(3));

            drawTrack(from, to);
        }

        reader.close();
    }

    /**
     * Draws a track between two stations
     * @param from station code of origin
     * @param to station code of destination
     */
    private void drawTrack(String from, String to){
        Station fromStation = stations.get(from);
        Station toStation = stations.get(to);

        Point2D fromPixel = converter.convertLatLonToPixel(fromStation.getLatitude(), fromStation.getLongitude());
        Point2D toPixel = converter.convertLatLonToPixel(toStation.getLatitude(), toStation.getLongitude());

        if(stations.get(from).getCountry().equalsIgnoreCase("nl") && stations.get(to).getCountry().equalsIgnoreCase("nl")){
            SaxionApp.drawLine((int) fromPixel.getX(), (int) fromPixel.getY(), (int) toPixel.getX(), (int) toPixel.getY());
        }
    }

    /**
     * Draws all stations on the map
     * Only draws stations in the Netherlands
     */
    private void drawStations(){
        SaxionApp.setBorderColor(Color.decode("#ebbd80"));
        SaxionApp.setFill(Color.decode("#FF4F00"));
        for (String stationCode : stations.getKeys()){
            Station station = stations.get(stationCode);

            if(station.getCountry().equalsIgnoreCase("nl")){
                SaxionApp.sleep(DRAW_SLEEP);
                drawStation(station.getLatitude(), station.getLongitude());
            }
        }
    }

    /**
     * Draws a station on the map
     * @param lat latitude
     * @param lon longitude
     */
    private void drawStation(double lat, double lon) {
        Point2D pixel = converter.convertLatLonToPixel(lat, lon);
        int x = (int) (pixel.getX());
        int y = (int) (pixel.getY());

        SaxionApp.drawCircle(x, y, DOT_SIZE);
    }

    /**
     * Draws the shortest path between two stations
     * And prints the total distance of the path
     */
    public void drawShortestPath(){
        SaxList<SaxGraph.DirectedEdge<String>> shortestPathAStar = tracks.shortestPathAStar(from, to, (current, goal) -> {
            Station currentStation = stations.get(current);
            Station goalStation = stations.get(goal);

            return Coordinate.haversineDistance(currentStation.getCoordinate(), goalStation.getCoordinate());
        });

        double totalDistance = 0;
        for (SaxGraph.DirectedEdge<String> edge : shortestPathAStar) {
            totalDistance += edge.weight();
        }

        SaxionApp.setBorderColor(SaxionApp.SAXION_PINK);
        SaxionApp.setBorderSize(3);

        // List of codes, draw tracks between them with red
        SaxList<String> result = tracks.convertEdgesToNodes(shortestPathAStar);
        int index = 0;
        while (index < result.size() - 1) {
            SaxionApp.sleep(0.1);
            drawTrack(result.get(index), result.get(index + 1));
            index++;
        }

        SaxionApp.setTextDrawingColor(Color.WHITE);
        SaxionApp.drawText("Shortest path from " + stations.get(from).getName() + " to " + stations.get(to).getName() + " is: ", 10, 8, 14);
        SaxionApp.setTextDrawingColor(Color.ORANGE);
        SaxionApp.drawText(String.format("%.1f", totalDistance), 10,  28, 14);
        SaxionApp.setTextDrawingColor(Color.WHITE);
        SaxionApp.drawText(" km long", 50, 28, 14);

        // additionally log information in the console
        StringBuilder routeStr = new StringBuilder();
        for (int i = 0; i < result.size(); i++) {
            routeStr.append(stations.get(result.get(i)).getName());
            if (i < result.size() - 1) {
                routeStr.append(" -> ");
            }
        }

        System.out.println("Shortest path: " + routeStr);
        System.out.println("Total distance: " + totalDistance + " km");
    }

}
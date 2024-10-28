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
    private final MyArrayList<String> menuOptions;
    private final MyArrayList<String> pathAlgorithms;
    private final MyArrayList<String> drawOptions;

    public final static int WINDOW_WIDTH = 1020;
    public final static int WINDOW_HEIGHT = 818;

    private final static int IMAGE_WIDTH = 1621;
    private final static int IMAGE_HEIGHT = 1920;
    private final static int BANNER_HEIGHT = 38;
    private final static double DRAW_SLEEP = 0.001;
    private final static int DOT_SIZE = 4;

    private final CoordinateConverter converter = new CoordinateConverter();
    private MySpHashMap<String, Station> stations;
    private MyAdjacencyListGraph<String> tracks;
    private boolean nlOnly;

    public static void main(String[] args) throws FileNotFoundException {
        SaxionApp.start(new SaxionAppDemo(), WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    public SaxionAppDemo() {
        try {
            readStations();
            readTracks();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        menuOptions = new MyArrayList<>();
        menuOptions.addLast("Shortest path");
        menuOptions.addLast("Minimum cost spanning tree [Prim]");
        menuOptions.addLast("Exit");

        pathAlgorithms = new MyArrayList<>();
        pathAlgorithms.addLast("A*");
        pathAlgorithms.addLast("Dijkstra");

        drawOptions = new MyArrayList<>();
        drawOptions.addLast("Draw all stations and tracks");
        drawOptions.addLast("Draw only stations in the Netherlands");

        nlOnly = true;
    }

    @Override
    public void run() {
        try {
            initApplication();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Initializes the application.
     * Prompts the user to select an option from the menu.
     *
     * @throws FileNotFoundException if the station file is not found
     */
    private void initApplication() throws FileNotFoundException {
        while (true) {
            SaxionApp.clear();
            int choice = promptMenu("Select an option", menuOptions);

            switch (choice) {
                case 1 -> {
                    String from = askForStation("Enter the station code of the origin station: ");
                    String to = askForStation("Enter the station code of the destination station: ");
                    int algorithmChoice = promptMenu("Select an algorithm", pathAlgorithms);

                    // ask if user wants to draw tracks and stations outside NL
                    int drawChoice = promptMenu("Select an option", drawOptions);
                    nlOnly = drawChoice == 2;

                    switch (algorithmChoice) {
                        case 1 -> {
                            // a*
                            determineShortestPath(from, to, "A*");
                        }
                        case 2 -> {
                            // dijkstra
                            determineShortestPath(from, to, "Dijkstra");
                        }
                    }

                }
                case 2 -> {
                    // mst
                    determineMST();
                }
                case 3 -> {
                    SaxionApp.printLine("Goodbye!");
                    SaxionApp.quit();
                    return;
                }
                default -> {
                    SaxionApp.printLine("Invalid choice. Please enter a valid choice.");
                }
            }
        }

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

    /**
     * Clear the screen and re-draws the map with tracks and stations
     */
    private void initializeScreen() throws FileNotFoundException {
        SaxionApp.clear();
        initMap();
        drawTracks();
        drawStations();
    }

    /**
     * Reads the data from stations.csv on start up
     *
     * @throws FileNotFoundException if the file is not found
     */
    private void readStations() throws FileNotFoundException {
        stations = new MySpHashMap<>();
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
     * Reads the data from tracks.csv on start up.
     * Adds the edges to the graph.
     *
     * @throws FileNotFoundException if the file is not found
     */
    private void readTracks() throws FileNotFoundException {
        tracks = new MyAdjacencyListGraph<>();
        CsvReader reader = new CsvReader("resources/tracks.csv", true);
        reader.setSeparator(",");

        while (reader.readLine()) {
            String from = reader.readString(0);
            String to = reader.readString(1);
            tracks.addEdge(from, to, reader.readDouble(3));
        }

        reader.close();
    }

    /**
     * Unfortunately, I need to read tracks once again to draw them,
     * cause there is no way to draw the tracks in the correct sequence without reading them again.
     * When traversing the graph, the order of the edges is not guaranteed and therefore the tracks are not drawn correctly.
     *
     * @throws FileNotFoundException if the file is not found
     */
    private void drawTracks() throws FileNotFoundException {
        SaxionApp.setBorderSize(2);
        SaxionApp.setBorderColor(Color.decode("#f3f1b2"));
        CsvReader reader = new CsvReader("resources/tracks.csv", true);
        reader.setSeparator(",");

        while (reader.readLine()) {
            SaxionApp.sleep(DRAW_SLEEP);
            String from = reader.readString(0);
            String to = reader.readString(1);

            drawTrack(from, to);
        }

        reader.close();
    }

    /**
     * Draws a track between two stations
     *
     * @param from station code of origin
     * @param to   station code of destination
     */
    private void drawTrack(String from, String to) {
        Station fromStation = stations.get(from);
        Station toStation = stations.get(to);

        Point2D fromPixel = converter.convertLatLonToPixel(fromStation.getLatitude(), fromStation.getLongitude());
        Point2D toPixel = converter.convertLatLonToPixel(toStation.getLatitude(), toStation.getLongitude());

        if (nlOnly) {
            if (stations.get(from).getCountry().equalsIgnoreCase("nl") && stations.get(to).getCountry().equalsIgnoreCase("nl")) {
                SaxionApp.drawLine((int) fromPixel.getX(), (int) fromPixel.getY(), (int) toPixel.getX(), (int) toPixel.getY());
            }
        } else {
            SaxionApp.drawLine((int) fromPixel.getX(), (int) fromPixel.getY(), (int) toPixel.getX(), (int) toPixel.getY());
        }
    }

    /**
     * Draws all stations on the map
     * Only draws stations in the Netherlands
     */
    private void drawStations() {
        SaxionApp.setBorderSize(2);
        SaxionApp.setBorderColor(Color.decode("#ebbd80"));
        SaxionApp.setFill(Color.decode("#FF4F00"));

        for (String stationCode : stations.getKeys()) {
            Station station = stations.get(stationCode);

            if (nlOnly) {
                if (station.getCountry().equalsIgnoreCase("nl")) {
                    SaxionApp.sleep(DRAW_SLEEP);
                    drawStation(station.getLatitude(), station.getLongitude());
                }
            } else {
                SaxionApp.sleep(DRAW_SLEEP);
                drawStation(station.getLatitude(), station.getLongitude());
            }
        }
    }

    /**
     * Draws a station on the map
     *
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
     * Determines the shortest path between two stations and draws it on the map
     *
     * @param from      station code of origin
     * @param to        the station code of destination
     * @param algorithm the algorithm to use
     * @throws FileNotFoundException if the file is not found
     */
    private void determineShortestPath(String from, String to, String algorithm) throws FileNotFoundException {
        initializeScreen();

        from = from.toUpperCase();
        to = to.toUpperCase();

        SaxList<SaxGraph.DirectedEdge<String>> shortestPathEdges = new MyArrayList<>();
        SaxList<String> shortestPathNodes = new MyArrayList<>();
        double totalDistance = 0;

        // get list of edges
        switch (algorithm) {
            case "A*" -> {
                // a*
                shortestPathEdges = tracks.shortestPathAStar(from, to, (current, goal) -> {
                    Station currentStation = stations.get(current);
                    Station goalStation = stations.get(goal);

                    return Coordinate.haversineDistance(currentStation.getCoordinate(), goalStation.getCoordinate());
                });

                if(shortestPathEdges == null) {
                    SaxionApp.printLine("No path found between the stations", Color.RED);
                    SaxionApp.pause();
                    return;
                }

                // calculate total distance
                for (SaxGraph.DirectedEdge<String> edge : shortestPathEdges) {
                    totalDistance += edge.weight();
                }
            }

            case "Dijkstra" -> {
                // dijkstra
                shortestPathEdges = tracks.getDijkstraEdges(from, to);

                if (shortestPathEdges.isEmpty()) {
                    SaxionApp.printLine("No path found between the stations", Color.RED);
                    SaxionApp.pause();
                    return;
                }

                // total distance is the weight of the last edge
                totalDistance = shortestPathEdges.get(shortestPathEdges.size() - 1).weight();
            }
        }

        // convert edges to nodes and draw them
        shortestPathNodes = tracks.convertEdgesToNodes(shortestPathEdges);

        // Pick color based on the algorithm
        Color color = algorithm.equals("A*") ? SaxionApp.SAXION_PINK : Color.GREEN;

        // Draw the shortest path
        drawShortestPath(shortestPathNodes, color);

        // print total distance
        printShortestRouteOverview(stations.get(from).getName(), stations.get(to).getName(), totalDistance);

        // print the path in console in case SaxionApp is not running
        System.out.println("Shortest path from " + stations.get(from).getName() + " to " + stations.get(to).getName() + " is: ");
        for (String node : shortestPathNodes) {
            if (node.equals(shortestPathNodes.get(shortestPathNodes.size() - 1))) {
                System.out.print(stations.get(node).getName());
            } else {
                System.out.print(stations.get(node).getName() + " -> ");
            }
        }

        System.out.println("\nTotal distance: " + totalDistance + " km");

        // Wait for user to continue
        SaxionApp.pause();
    }

    /**
     * Draws the shortest path on the map
     *
     * @param shortestPathNodes list of station codes
     */
    private void drawShortestPath(SaxList<String> shortestPathNodes, Color color) {
        SaxionApp.setBorderColor(color);
        SaxionApp.setBorderSize(3);

        int index = 0;
        while (index < shortestPathNodes.size() - 1) {
            SaxionApp.sleep(0.1);
            drawTrack(shortestPathNodes.get(index), shortestPathNodes.get(index + 1));
            index++;
        }
    }

    /**
     * Helper to display the shortest path overview
     *
     * @param from          station name
     * @param to            station name
     * @param totalDistance total distance of the path
     */
    private void printShortestRouteOverview(String from, String to, double totalDistance) {
        SaxionApp.setTextDrawingColor(Color.WHITE);
        SaxionApp.drawText("Shortest path from " + from + " to " + to + " is: ", 10, 8, 14);
        SaxionApp.setTextDrawingColor(Color.ORANGE);
        SaxionApp.drawText(String.format("%.1f", totalDistance), 10, 28, 14);
        SaxionApp.setTextDrawingColor(Color.WHITE);
        SaxionApp.drawText(" km long", 50, 28, 14);
    }

    /**
     * Determines the minimum cost spanning tree and draws it on the map
     * Only draws stations and tracks in the Netherlands
     */
    private void determineMST() throws FileNotFoundException {
        MyAdjacencyListGraph<String> mst = (MyAdjacencyListGraph<String>) tracks.minimumCostSpanningTree();
        MyAdjacencyListGraph<String> filteredMST = new MyAdjacencyListGraph<>();

        double totalLength = 0;
        for (SaxGraph.DirectedEdge<String> edge : mst.getVertices()) {
            if (stations.get(edge.from()).getCountry().equalsIgnoreCase("nl") && stations.get(edge.to()).getCountry().equalsIgnoreCase("nl")) {
                filteredMST.addEdge(edge.from(), edge.to(), edge.weight());
                totalLength += edge.weight();
            }
        }

        drawMST(filteredMST);
        System.out.println("Total length of the MST: " + totalLength);
        SaxionApp.pause();
    }

    /**
     * Draws the minimum cost spanning tree on the map.
     * Only draws stations and tracks in the Netherlands.
     * Additionally, prints the total length of the connections.
     * @param mst the minimum cost spanning tree
     */
    private void drawMST(MyAdjacencyListGraph<String> mst) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        nlOnly = true;

        // Draw tracks before MST
        clearAndDrawMap();
        drawTracks();
        SaxionApp.setBorderColor(Color.WHITE);
        SaxionApp.drawText("Tracks before MST algorithm", 10, 8, 14);

        // Wait for user to click
        SaxionApp.pause();

        // Draw MST
        clearAndDrawMap();
        SaxionApp.setBorderSize(3);
        SaxionApp.setBorderColor(Color.BLUE);

        for (SaxGraph.DirectedEdge<String> edge : mst.getVertices()) {
            SaxionApp.sleep(DRAW_SLEEP);
            drawTrack(edge.from(), edge.to());
            sb.append(stations.get(edge.from()).getName()).append(" -> ").append(stations.get(edge.to()).getName()).append(" ");
        }

        double totalLength = mst.getTotalWeight();
        SaxionApp.setBorderColor(Color.WHITE);
        SaxionApp.drawText("Track connections after MST", 10, 8, 14);
        SaxionApp.drawText("Total length of connections:", 10, 28, 14);
        SaxionApp.setTextDrawingColor(Color.ORANGE);
        SaxionApp.drawText(String.format("%.1f", totalLength), 10, 48, 14);

        // print the path in console in case SaxionApp is not running
        System.out.println("Minimum cost spanning tree of stations ONLY in Netherlands:  \n" + sb);
    }

    private void clearAndDrawMap() {
        SaxionApp.clear();
        initMap();
        drawStations();
    }

    /**
     * Prompt menu with given message and options
     *
     * @param message message to display
     * @param options list of options to select from
     * @return the selected option
     */
    private int promptMenu(String message, MyArrayList<String> options) {
        SaxionApp.printLine(message);
        for (int i = 0; i < options.size(); i++) {
            SaxionApp.printLine((i + 1) + ". " + options.get(i));
        }

        int choice = SaxionApp.readInt();

        while (choice < 0 || choice > options.size()) {
            SaxionApp.printLine("Invalid choice. Please enter a valid choice: ", Color.red);
            choice = SaxionApp.readInt();
        }
        return choice;
    }

    /**
     * Prompts user to enter station code and checks if it's correct
     *
     * @param message message to display
     * @return the station code
     */
    private String askForStation(String message) {
        SaxionApp.printLine(message);
        String station = SaxionApp.readString();

        station = station.toUpperCase();
        while (!stations.contains(station)) {
            SaxionApp.printLine("Station not found. Please enter a valid station code.", Color.RED);
            station = SaxionApp.readString();
            station = station.toUpperCase();
        }

        return station;
    }
}
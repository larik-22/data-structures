package nl.saxion.cds;

import nl.saxion.app.SaxionApp;
import nl.saxion.cds.utils.CoordinateConverter;
import nl.saxion.cds.utils.CsvReader;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class AStarDemo implements Runnable {
    private final static int WINDOW_WIDTH = 1020;
    private final static int WINDOW_HEIGHT = 818;
    private final static int IMAGE_WIDTH = 1621;
    private final static int IMAGE_HEIGHT = 1920;
    private final static int BANNER_HEIGHT = 38;

    private final static double MIN_LAT = 43.30381;
    private final static double MAX_LAT = 53.458419;
    private final static double MIN_LON = -0.126136;
    private final static double MAX_LON = 16.375833;

    private double scaleX, scaleY;
    private int mapWidth, mapHeight;

    public static void main(String[] args) {
        SaxionApp.start(new AStarDemo(), WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    @Override
    public void run() {
        initApplication();
    }

    public void initApplication() {
        // read the data
        // draw the map
        // draw the stations
        initMap();
        try {
            readData();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Could not read the data file");
        }

//        drawStation(52.955276489258, 4.7611112594605);
    }

    /**
     * Draws a map and resizes it to fit
     * the window while maintaining the aspect ratio.
     */
    public void initMap() {
        SaxionApp.setBorderColor(Color.WHITE);
        double aspectRatio = (double) IMAGE_WIDTH / IMAGE_HEIGHT;

        // Calculate the new dimensions to fit the image within the window while maintaining the aspect ratio
        mapWidth = WINDOW_WIDTH;
        mapHeight = (int) ((WINDOW_WIDTH / aspectRatio) - BANNER_HEIGHT);

        if (mapHeight > (WINDOW_HEIGHT - BANNER_HEIGHT)) {
            mapHeight = WINDOW_HEIGHT - BANNER_HEIGHT;
            mapWidth = (int) (mapHeight * aspectRatio);
        }
        scaleX = (double) mapWidth / IMAGE_WIDTH;
        scaleY = (double) mapHeight / IMAGE_HEIGHT;

        // Resize the window and draw the map
        SaxionApp.resize(mapWidth, mapHeight + BANNER_HEIGHT);
        SaxionApp.drawImage("resources/Nederland.png", 0, 0, mapWidth, mapHeight);
        System.out.println("Map dimensions: " + mapWidth + "x" + mapHeight);
    }

    /**
     * Reads the data from stations.csv on start up
     *
     * @throws FileNotFoundException if the file is not found
     */
    private void readData() throws FileNotFoundException {
        CsvReader reader = new CsvReader("resources/stations.csv", true);
        reader.setSeparator(",");

        while (reader.readLine()) {
            double latitude = reader.readDouble(4);
            double longitude = reader.readDouble(5);

            if (reader.readString(2).equalsIgnoreCase("nl")) {
                drawStation(latitude, longitude);
            }
        }
        reader.close();
    }

    private void drawStation(double lat, double lon) {
        // Convert the latitude and longitude to pixel coordinates and adjust for the map offset

        CoordinateConverter converter = new CoordinateConverter();
        Point2D pixel = converter.convertLatLonToPixel(lat, lon);
        int x = (int) (pixel.getX());
        int y = (int) (pixel.getY());
        System.out.println("Station at: " + x + ", " + y);

        SaxionApp.drawCircle(x, y, 4);
    }
}
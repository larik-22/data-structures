package nl.saxion.cds.utils;

import java.awt.geom.Point2D;

public class CoordinateConverter {
    // Map dimensions
    private static final double RESIZED_MAP_WIDTH = 658.0;
    private static final double RESIZED_MAP_HEIGHT = 780.0;

    // Known min/max latitude and longitude
    private static final double MIN_LAT = 50.78;
    private static final double MAX_LAT = 53.5000;
    private static final double MIN_LON = 3.3000;
    private static final double MAX_LON = 7.2000;

    // Scaling factors
    private final double latitudeScale;
    private final double longitudeScale;

    // Vertical adjustment to fine-tune placement (calibrate based on observed shifts)
    private final double verticalOffset;

    public CoordinateConverter() {
        // Calculate scaling factors based on the latitude/longitude range
        this.latitudeScale = RESIZED_MAP_HEIGHT / (MAX_LAT - MIN_LAT);
        this.longitudeScale = RESIZED_MAP_WIDTH / (MAX_LON - MIN_LON);

        this.verticalOffset = 9.0;  // Adjust this value based on calibration needs
    }

    public Point2D.Double convertLatLonToPixel(double latitude, double longitude) {
        // Convert latitude and longitude to pixel coordinates
        double x = (longitude - MIN_LON) * longitudeScale;
        double y = ((MAX_LAT - latitude) * latitudeScale) + verticalOffset; // Apply vertical offset

        return new Point2D.Double(x, y);
    }

}

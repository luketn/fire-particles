package com.mycodefu.calculations;

import java.awt.geom.Point2D;

public class Distance {

    public static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2d) + Math.pow(y1 - y2, 2d));
    }

    public static double distance(Point2D.Double location1, Point2D.Double location2) {
        return distance(location1.x, location1.y, location2.x, location2.y);
    }
}

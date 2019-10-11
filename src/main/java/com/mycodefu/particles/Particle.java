package com.mycodefu.particles;

import java.awt.*;
import java.awt.geom.Point2D;

public class Particle {
    private Point2D.Double location;
    private Point2D.Double lastLocation;

    private double angleDegrees;
    private Color color;
    private double height;
    private double width;

    public Particle(double x, double y, double angleDegrees, double width, double height, Color color) {
        checkDegrees(angleDegrees);
        checkLocation(x, y, width, height);

        this.color = color;
        this.location = new Point2D.Double(x, y);
        this.lastLocation = new Point2D.Double(x, y);
        this.angleDegrees = angleDegrees;
        this.height = height;
        this.width = width;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getAngleDegrees() {
        return angleDegrees;
    }

    public void turnLeft(double angleDegrees) {
        checkDegrees(angleDegrees);

        if (this.angleDegrees - angleDegrees < 0) {
            setAngleDegrees(360 - this.angleDegrees - angleDegrees);
        } else {
            setAngleDegrees(this.angleDegrees - angleDegrees);
        }
    }

    public void turnRight(double angleDegrees) {
        checkDegrees(angleDegrees);

        if (this.angleDegrees + angleDegrees > 360) {
            setAngleDegrees(this.angleDegrees + angleDegrees - 360);
        } else {
            setAngleDegrees(this.angleDegrees + angleDegrees);
        }
    }

    public void setAngleDegrees(double angleDegrees) {
        checkDegrees(angleDegrees);

        this.angleDegrees = angleDegrees;
    }

    public Point2D.Double getLocation() {
        return location;
    }

    public Point2D.Double getLastLocation() {
        return lastLocation;
    }

    public void move(Point2D.Double location) {
        checkLocation(location.x, location.y, width, height);

        this.lastLocation = this.location;
        this.location = new Point2D.Double(location.x, location.y);
    }


    private void checkDegrees(double angleDegrees) {
        if (angleDegrees > 360d) {
            throw new IllegalArgumentException(String.format("Invalid argument for angleDegrees %.2f. Must be less than 360.", angleDegrees));
        } else if (angleDegrees < 0d) {
            throw new IllegalArgumentException(String.format("Invalid argument for angleDegrees %.2f. Must be greater than 0.", angleDegrees));
        }
    }

    private void checkLocation(double x, double y, double width, double height) {
        if (x < 0) {
            throw new IllegalArgumentException(String.format("Invalid argument for x %.2f. Must be greater than 0.", x));
        }
        if (x + width > 1) {
            throw new IllegalArgumentException(String.format("Invalid argument for x %.2f. Must be less than %.2f (1 less the width).", x, 1 - width));
        }
        if (y < 0) {
            throw new IllegalArgumentException(String.format("Invalid argument for y %.2f. Must be greater than 0.", y));
        }
        if (y + width > 1) {
            throw new IllegalArgumentException(String.format("Invalid argument for y %.2f. Must be less than %.2f (1 less the height).", y, 1 - height));
        }
    }
}

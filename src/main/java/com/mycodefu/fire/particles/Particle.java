package com.mycodefu.fire.particles;

import java.awt.*;
import java.awt.geom.Point2D;

public class Particle {
    private Point2D.Double location;
    private Point2D.Double lastLocation;

    private double angleDegrees;
    private double diameter;
    private Color color;

    public Particle(double x, double y, double angleDegrees) {
        this(x, y, angleDegrees, 0.01d, Color.ORANGE);
    }

    public Particle(double x, double y, double angleDegrees, double diameter, Color color) {
        this.color = color;
        this.location = new Point2D.Double(x, y);
        this.lastLocation = new Point2D.Double(x, y);
        this.angleDegrees = angleDegrees;
        this.diameter = diameter;
    }

    public Point2D.Double getLocation() {
        return location;
    }

    public Point2D.Double getLastLocation() {
        return lastLocation;
    }

    public double getAngleDegrees() {
        return angleDegrees;
    }

    public double getDiameter() {
        return diameter;
    }

    public void turnLeft(double angleDegrees) {
        if (this.angleDegrees - angleDegrees < 0) {
            this.angleDegrees = 360 - this.angleDegrees;
        }
        this.angleDegrees -= angleDegrees;
    }

    public void turnRight(double angleDegrees) {
        if (this.angleDegrees + angleDegrees > 360) {
            this.angleDegrees = 360 - this.angleDegrees;
        }
        this.angleDegrees += angleDegrees;
    }

    public void move(Point2D.Double location) {
        this.lastLocation = this.location;
        this.location = new Point2D.Double(location.x, location.y);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setAngleDegrees(double angleDegrees) {
        this.angleDegrees = angleDegrees;
    }
}

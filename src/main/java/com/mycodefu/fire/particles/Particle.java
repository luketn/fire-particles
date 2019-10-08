package com.mycodefu.fire.particles;

import java.awt.*;

public class Particle {
    public double x;
    public double y;
    public double directionAngleDegrees; //-pi to +pi
    public Color color;
    public double width;
    public double height;

    public Particle(double x, double y, double directionAngleDegrees) {
        this(x, y, directionAngleDegrees, Color.ORANGE, 0.01, 0.01);
    }
    public Particle(double x, double y, double directionAngleDegrees, Color color, double width, double height) {
        this.x = x;
        this.y = y;
        this.directionAngleDegrees = directionAngleDegrees;
        this.color = color;
        this.width = width;
        this.height = height;
    }

    public void turnLeft(double angleDegrees) {
        if (directionAngleDegrees - angleDegrees < 0) {
            directionAngleDegrees = 360 - directionAngleDegrees;
        }
        directionAngleDegrees -= angleDegrees;
    }

    public void turnRight(double angleDegrees) {
        if (directionAngleDegrees + angleDegrees > 360) {
            directionAngleDegrees = 360 - directionAngleDegrees;
        }
        directionAngleDegrees += angleDegrees;
    }

    public void move(double distance) {
        double candidateX = x + distance * Math.cos(directionAngleDegrees * Math.PI / 180);
        double candidateY = y + distance * Math.sin(directionAngleDegrees * Math.PI / 180);

        //bounce
        if (candidateX < 0 || candidateX > 1-width) {
            directionAngleDegrees = 180 - directionAngleDegrees;
        } else if (candidateY < 0 || candidateY > 1-height) {
            directionAngleDegrees = 360 - directionAngleDegrees;
        } else {
            candidateX = correctBounds(candidateX);
            candidateY = correctBounds(candidateY);

            x = candidateX;
            y = candidateY;
        }
    }

    private double correctBounds(double candidate) {
        if (candidate < 0d) {
            return 0d;
        } else if (candidate > 1d) {
            return 1d;
        } else {
            return candidate;
        }
    }
}

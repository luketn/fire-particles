package com.mycodefu.fire.particles;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class Particle {
    private static final int MAX_RANDOM_TURN_DEGREES = 15;

    public double x;
    public double y;
    public double directionAngleDegrees; //-pi to +pi
    public double width;
    public double height;
    private Point2D.Double lastLocation;
    private ParticleMode mode;
    private double warmth;

    private Random random = new Random();
    AtomicLong breathCounter = new AtomicLong();

    public Particle(double x, double y, double directionAngleDegrees) {
        this(x, y, directionAngleDegrees, 0.01, 0.01);
    }

    public Particle(double x, double y, double directionAngleDegrees, double width, double height) {
        this.x = x;
        this.y = y;
        this.lastLocation = new Point2D.Double(x, y);
        this.directionAngleDegrees = directionAngleDegrees;
        this.width = width;
        this.height = height;
        this.mode = ParticleMode.Happy;
        this.warmth = 1.0d;
    }

    public Color getColor() {

        if (mode == ParticleMode.Cold) {
            return new Color(0f, 0, (float) cappedWarmth()).brighter().brighter();
        } else {
            Color color = new Color(0, (float) cappedWarmth(), 0);

            //incandescent
            if (warmth > 1) {
                double countDownToOne = warmth;
                double cap = 0;
                while (countDownToOne > 1 && cap < 100) {
                    color = new Color((float) cap / 100f, (float) cappedWarmth(), (float) cap / 100f);
                    countDownToOne -= 0.01;
                    cap += 10;
                }
            }
            return color;
        }
        //random color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255))
    }

    private double cappedWarmth() {
        if (warmth < 0) {
            return 0;
        } else if (warmth > 1) {
            return 1;
        } else {
            return warmth;
        }
    }

    public double getWarmth() {
        return warmth;
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

    public void breathe() {
        double distanceFromHotCenter = distanceFromCenter() - 0.3;
        warmth -= distanceFromHotCenter * 0.02d;
        if (warmth < 0.5d) {
            mode = ParticleMode.Cold;
        } else {
            mode = ParticleMode.Happy;
        }

        switch (mode) {
            case Happy: {
                if (warmth > 1){
                    turnRight(1);
                }
                move(0.02d + (warmth * 0.005));
                break;
            }
            case Cold: {
                //when cold the particle will head toward the warm center
                if (furtherFromCenterAfterLastMove()) {
                    turnLeft(15);
                }
                //move faster the colder we are
                move(0.01d);
                break;
            }
        }
    }

    void move(double distance) {
        double candidateX = x + distance * Math.cos(directionAngleDegrees * Math.PI / 180);
        double candidateY = y + distance * Math.sin(directionAngleDegrees * Math.PI / 180);

        //bounce
        if (candidateX < 0 || candidateX > 1 - width) {
            directionAngleDegrees = 180 - directionAngleDegrees;
        } else if (candidateY < 0 || candidateY > 1 - height) {
            directionAngleDegrees = 360 - directionAngleDegrees;
        } else {
            candidateX = correctBounds(candidateX);
            candidateY = correctBounds(candidateY);

            lastLocation = new Point2D.Double(x, y);

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

    public double distanceFromCenter() {
        return Math.sqrt(Math.pow(0.5d - this.x, 2d) + Math.pow(0.5d - this.y, 2d));
    }

    public double lastDistanceFromCenter() {
        return Math.sqrt(Math.pow(0.5d - this.lastLocation.x, 2d) + Math.pow(0.5d - this.lastLocation.y, 2d));
    }

    public boolean furtherFromCenterAfterLastMove() {
        double distanceFromCenter = distanceFromCenter();
        double lastDistanceFromCenter = lastDistanceFromCenter();
        return distanceFromCenter > lastDistanceFromCenter;
    }
}

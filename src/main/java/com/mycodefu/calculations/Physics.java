package com.mycodefu.calculations;

import com.mycodefu.particles.Particle;

import java.awt.geom.Point2D;

public class Physics {

    public static double calculateBounceAngle(WallType wallType, double angleDegrees) {
        if (wallType == WallType.Vertical) {
            if (angleDegrees <= 180) {
                return 180 - angleDegrees;
            } else {
                return 360 - (angleDegrees - 180);
            }
        } else {
            return 360 - angleDegrees;
        }
    }

    public static void moveInBounds(Particle particle, double distance) {
        double candidateX = particle.getLocation().x + distance * Math.cos(particle.getAngleDegrees() * Math.PI / 180);
        double candidateY = particle.getLocation().y + distance * Math.sin(particle.getAngleDegrees() * Math.PI / 180);

        //bounce
        if (candidateX < 0 || candidateX > 1 - particle.getWidth()) {
            double bounceAngle = calculateBounceAngle(Physics.WallType.Vertical, particle.getAngleDegrees());
            particle.setAngleDegrees(bounceAngle);
        } else if (candidateY < 0 || candidateY > 1 - particle.getHeight()) {
            double bounceAngle = calculateBounceAngle(Physics.WallType.Horizontal, particle.getAngleDegrees());
            particle.setAngleDegrees(bounceAngle);
        } else {
            candidateX = correctBounds(candidateX);
            candidateY = correctBounds(candidateY);

            particle.move(new Point2D.Double(candidateX, candidateY));
        }
    }


    private static double correctBounds(double candidate) {
        if (candidate < 0d) {
            return 0d;
        } else if (candidate > 1d) {
            return 1d;
        } else {
            return candidate;
        }
    }

    public enum WallType {
        Horizontal,
        Vertical
    }
}

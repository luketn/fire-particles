package com.mycodefu.fire.particles;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

import static com.mycodefu.fire.particles.Distance.distance;
import static com.mycodefu.fire.particles.Physics.calculateBounceAngle;

public class ParticleMovementHeatMap implements ParticleMovement {
    public static final Point2D.Double CENTER_LOCATION = new Point2D.Double(0.5, 0.5);
    private final Map<Particle, ParticleHeatState> particleHeatMap = new HashMap<>();

    @Override
    public void breathe(Particle particle, ParticleArena arena) {
        ParticleHeatState particleHeat;
        if (!particleHeatMap.containsKey(particle)) {
            particleHeat = new ParticleHeatState();
            this.particleHeatMap.put(particle, particleHeat);
        } else {
            particleHeat = particleHeatMap.get(particle);
        }

        double distanceFromHotCenter = distanceFromCenter(particle) - 0.3;
        particleHeat.warmth -= distanceFromHotCenter * 0.02d;
        double factor = 1.5d;
        if (particleHeat.warmth < 0.5d && particleHeat.mode == ParticleMode.Happy) {
            particleHeat.mode = ParticleMode.Cold;
        } else if (particleHeat.warmth >= 1 && particleHeat.mode == ParticleMode.Cold) {
            particleHeat.mode = ParticleMode.Happy;
        }

        switch (particleHeat.mode) {
            case Happy: {
                if (particleHeat.warmth > 1) {
                    particle.turnRight(1);
                }
                move(particle, 0.01d + (particleHeat.warmth * 0.005));
                break;
            }
            case Cold: {
                //when cold the particle will head toward the warm center
                if (furtherFromCenterAfterLastMove(particle)) {
                    particle.turnLeft(25);
                }
                //move faster the colder we are
                move(particle, 0.01d);
                break;
            }
        }

        setColor(particle, particleHeat);
    }

    void move(Particle particle, double distance) {
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

    private double correctBounds(double candidate) {
        if (candidate < 0d) {
            return 0d;
        } else if (candidate > 1d) {
            return 1d;
        } else {
            return candidate;
        }
    }

    public double distanceFromCenter(Particle particle) {
        return distance(CENTER_LOCATION, particle.getLocation());
    }

    public double lastDistanceFromCenter(Particle particle) {
        return distance(CENTER_LOCATION, particle.getLastLocation());
    }

    public boolean furtherFromCenterAfterLastMove(Particle particle) {
        double distanceFromCenter = distanceFromCenter(particle);
        double lastDistanceFromCenter = lastDistanceFromCenter(particle);
        return distanceFromCenter > lastDistanceFromCenter;
    }


    public void setColor(Particle particle, ParticleHeatState heatState) {
        if (heatState.mode == ParticleMode.Cold) {
            particle.setColor(new Color(0f, 0, (float) cappedWarmth(heatState.warmth)).brighter().brighter());
        } else {
            Color color = new Color(0, (float) cappedWarmth(heatState.warmth), 0);

            //incandescent
            if (heatState.warmth > 1) {
                double countDownToOne = heatState.warmth;
                double cap = 0;
                while (countDownToOne > 1 && cap < 100) {
                    color = new Color((float) cap / 100f, (float) cappedWarmth(heatState.warmth), (float) cap / 100f);
                    countDownToOne -= 0.01;
                    cap += 10;
                }
            }
            particle.setColor(color);
        }
    }

    private double cappedWarmth(double warmth) {
        if (warmth < 0) {
            return 0;
        } else if (warmth > 1) {
            return 1;
        } else {
            return warmth;
        }
    }

    private class ParticleHeatState {
        private ParticleMode mode = ParticleMode.Happy;
        private double warmth = 1.0d;
    }

    public enum ParticleMode {
        Happy,
        Cold
    }
}

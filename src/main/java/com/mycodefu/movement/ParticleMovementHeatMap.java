package com.mycodefu.movement;

import com.mycodefu.particles.Particle;
import com.mycodefu.particles.ParticleArena;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.mycodefu.calculations.Distance.distance;
import static com.mycodefu.calculations.Physics.moveInBounds;

public class ParticleMovementHeatMap implements ParticleMovement {
    private static final Point2D.Double CENTER_LOCATION = new Point2D.Double(0.5, 0.5);
    private final Map<Particle, ParticleHeatState> particleHeatMap = new HashMap<>();
    private final Random random = new Random();
    private int coldTurnLeftDegrees;
    private double coldMovementSpeed;

    public ParticleMovementHeatMap() {
        initFactors();
    }

    @Override
    public void breathe(Particle particle, ParticleArena arena) {
        ParticleHeatState particleHeat = getParticleHeatState(particle);

        warm(particle, particleHeat);

        flipMode(arena, particleHeat);

        move(particle, particleHeat);

        color(particle, particleHeat);
    }

    @Override
    public void reset(Particle particle, ParticleArena arena) {
        initFactors();

        ParticleHeatState particleHeat = getParticleHeatState(particle);
        particleHeat.mode = ParticleMode.Hot;
        particleHeat.warmth = 1.0d;
        particle.move(CENTER_LOCATION);
    }

    private void initFactors() {
        coldTurnLeftDegrees = 20 + random.nextInt(5);
        coldMovementSpeed = 0.005d;
    }

    private void warm(Particle particle, ParticleHeatState particleHeat) {
        //Warm or cool the particles depending on the distance from the center. Hot particles warm slower.
        double distanceFromCenter = distanceFromCenter(particle);
        switch (particleHeat.mode) {
            case Cold:
                particleHeat.warmth -= (distanceFromCenter - 0.2) * 0.01d;
                break;
            case Hot:
                particleHeat.warmth -= (distanceFromCenter - 0.1) * 0.02d;
                break;
        }
    }

    private void flipMode(ParticleArena arena, ParticleHeatState particleHeat) {
        //Flip the mode of particles from Hot to Cold or vice versa, at different thresholds.
        if (particleHeat.warmth < 0.5d && particleHeat.mode == ParticleMode.Hot) {
            particleHeat.mode = ParticleMode.Cold;
        } else if (particleHeat.warmth >= 1.2 && particleHeat.mode == ParticleMode.Cold) {
            explodeAll(arena);
        }
    }

    private void move(Particle particle, ParticleHeatState particleHeat) {
        //Based on the mode, move the particle in different ways
        switch (particleHeat.mode) {
            case Hot: {
                //when hot the particle travels around randomly
                if (particleHeat.warmth > 1) {
                    if (random.nextBoolean()) {
                        particle.turnLeft(1);
                    } else {
                        particle.turnRight(1);
                    }
                }
                moveInBounds(particle, 0.01d);
                break;
            }
            case Cold: {
                //when cold the particle will head toward the warm center
                if (furtherFromCenterAfterLastMove(particle)) {
                    particle.turnLeft(coldTurnLeftDegrees);
                }
                moveInBounds(particle, coldMovementSpeed);
                break;
            }
        }
    }

    private void color(Particle particle, ParticleHeatState heatState) {
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

    private ParticleHeatState getParticleHeatState(Particle particle) {
        ParticleHeatState particleHeat;
        if (!particleHeatMap.containsKey(particle)) {
            particleHeat = new ParticleHeatState();
            this.particleHeatMap.put(particle, particleHeat);
        } else {
            particleHeat = particleHeatMap.get(particle);
        }
        return particleHeat;
    }

    private void explodeAll(ParticleArena arena) {
        initFactors();

        for (Particle particle : arena.getParticles()) {
            particle.setAngleDegrees(random.nextDouble() * 360d);

            ParticleHeatState particleHeat = getParticleHeatState(particle);
            particleHeat.mode = ParticleMode.Hot;
            particleHeat.warmth = 1.0d + random.nextDouble() * 0.1d;
        }
    }

    private double distanceFromCenter(Particle particle) {
        return distance(CENTER_LOCATION, particle.getLocation());
    }

    private double lastDistanceFromCenter(Particle particle) {
        return distance(CENTER_LOCATION, particle.getLastLocation());
    }

    private boolean furtherFromCenterAfterLastMove(Particle particle) {
        double distanceFromCenter = distanceFromCenter(particle);
        double lastDistanceFromCenter = lastDistanceFromCenter(particle);
        return distanceFromCenter > lastDistanceFromCenter;
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

    private static class ParticleHeatState {
        private ParticleMode mode = ParticleMode.Hot;
        private double warmth = 1.0d;
    }

    public enum ParticleMode {
        Hot,
        Cold
    }
}

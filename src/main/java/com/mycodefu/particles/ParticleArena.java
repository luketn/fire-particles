package com.mycodefu.particles;

import com.mycodefu.movement.ParticleMovement;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParticleArena {
    private final List<Particle> particles;
    private final Random random;
    private ParticleMovement movementStrategy;
    private final int width;
    private final int height;

    public ParticleArena(ParticleMovement movementStrategy, int width, int height) {
        this.movementStrategy = movementStrategy;
        this.width = width;
        this.height = height;
        this.particles = new ArrayList<>();
        this.random = new Random();
    }

    public void seedParticles(int numberOfParticles, int particleSizePixels) {
        double height = (double) particleSizePixels / (double) this.height;
        double width = (double) particleSizePixels / (double) this.width;
        double diameter;
        if (height > width) {
            diameter = width;
        } else {
            diameter = height;
        }

        for (int i = 0; i < numberOfParticles; i++) {
            particles.add(new Particle(0.5, 0.5, random.nextDouble() * 360d, diameter, diameter, Color.ORANGE));
        }
    }

    public void changeStrategy(ParticleMovement movementStrategy) {
        this.movementStrategy = movementStrategy;
    }

    public void reset() {
        for (Particle particle : particles) {
            movementStrategy.reset(particle, this);
        }
    }

    public void tick() {
        for (Particle particle : particles) {
            movementStrategy.breathe(particle, this);
        }
    }

    public List<Particle> getParticles() {
        return particles;
    }
}

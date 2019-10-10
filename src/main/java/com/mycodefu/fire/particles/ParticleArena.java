package com.mycodefu.fire.particles;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParticleArena {
    final List<Particle> particles;
    private final Random random;
    private final int width;
    private final int height;

    public ParticleArena(int width, int height) {
        this.width = width;
        this.height = height;
        this.particles = new ArrayList<>();
        this.random = new Random();
    }

    public void seedParticles(int numberOfParticles, int particleSizePixels) {
        double diameter = particleSizePixels / (double) this.width;

        for (int i = 0; i < numberOfParticles; i++) {
            particles.add(new Particle(0.5, 0.5, random.nextDouble() * 360d, diameter, Color.ORANGE));
        }
    }

    public void reset() {
        for (Particle particle : particles) {
            particle.move(new Point2D.Double(0.5, 0.5));
        }
    }

    public void tick(ParticleMovement movementStrategy) {
        for (Particle particle : particles) {
            movementStrategy.breathe(particle, this);
        }
    }
}

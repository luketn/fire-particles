package com.mycodefu.fire.particles;

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
        particles = new ArrayList<>();
        random = new Random();
    }

    public void seedParticles(int numberOfParticles, int particleSizePixels) {
        double width = particleSizePixels / (double) this.width;
        double height = particleSizePixels / (double) this.height;

        for (int i = 0; i < numberOfParticles; i++) {
            particles.add(new Particle(0.5, 0.5, random.nextDouble() * 360d, width, height));
        }
    }

    public void reset() {
        for (Particle particle : particles) {
            particle.x = 0.5;
            particle.y = 0.5;
        }
    }

    public void tick() {
        for (Particle particle : particles) {
            particle.breathe();
        }
    }
}

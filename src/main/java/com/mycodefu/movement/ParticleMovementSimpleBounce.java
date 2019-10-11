package com.mycodefu.movement;

import com.mycodefu.particles.Particle;
import com.mycodefu.particles.ParticleArena;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

import static com.mycodefu.calculations.Physics.moveInBounds;

public class ParticleMovementSimpleBounce implements ParticleMovement {
    private final Random random = new Random();

    @Override
    public void breathe(Particle particle, ParticleArena arena) {
        moveInBounds(particle, 0.01d);
        particle.setColor(Color.MAGENTA);
    }

    @Override
    public void reset(Particle particle, ParticleArena arena) {
        particle.move(new Point2D.Double(0.5, 0.5));
        particle.setAngleDegrees(random.nextDouble() * 360d);
    }
}

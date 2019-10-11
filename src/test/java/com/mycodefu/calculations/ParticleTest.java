package com.mycodefu.calculations;

import com.mycodefu.particles.Particle;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.awt.geom.Point2D;

import static org.junit.Assert.assertEquals;

public class ParticleTest {

    @Test(expected = IllegalArgumentException.class)
    public void construct_invalid_degrees_less_than_0() {
        new Particle(0, 0, -1d, 0.1d, 0.1d, Color.BLACK);
    }

    @Test(expected = IllegalArgumentException.class)
    public void construct_invalid_degrees_greater_than_360() {
        new Particle(0, 0, 360.5d, 0.1d, 0.1d, Color.BLACK);
    }

    @Test
    public void turnLeft() {
        Particle particle = new Particle(0.5, 0.5, 45d, 0.1d, 0.1d, Color.BLACK);
        particle.turnLeft(45);

        assertEquals("Failed to turn left to 0", 0d, particle.getAngleDegrees(), 0.01d);

        particle.turnLeft(45d);

        assertEquals("Failed to turn left past 360 to 315", 315d, particle.getAngleDegrees(), 0.01d);
    }

    @Test
    public void turnRight() {
        Particle particle = new Particle(0.5, 0.5, 300d, 0.1d, 0.1d, Color.BLACK);
        particle.turnRight(45);

        assertEquals("Failed to turn right to 345", 345d, particle.getAngleDegrees(), 0.01d);

        particle.turnRight(45d);

        assertEquals("Failed to turn left past 360 to 30", 30d, particle.getAngleDegrees(), 0.01d);
    }

    @Test
    public void move() {
        Particle particle = new Particle(0.5, 0.5, 45d, 0.1d, 0.1d, Color.BLACK);

        assertEquals(new Point2D.Double(0.5, 0.5), particle.getLocation());
        assertEquals(new Point2D.Double(0.5, 0.5), particle.getLastLocation());

        particle.move(new Point2D.Double(0.6, 0.6));

        assertEquals(new Point2D.Double(0.6, 0.6), particle.getLocation());
        assertEquals(new Point2D.Double(0.5, 0.5), particle.getLastLocation());

        particle.move(new Point2D.Double(0.7, 0.7));

        assertEquals(new Point2D.Double(0.7, 0.7), particle.getLocation());
        assertEquals(new Point2D.Double(0.6, 0.6), particle.getLastLocation());
    }
}
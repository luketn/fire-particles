package com.mycodefu.fire.particles;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ParticleTest {

    @Test
    public void move_45_10percent() {
        Particle particle = new Particle(0.5, 0.5, 45);

        System.out.println(String.format("Before x: %f, y: %f", particle.x, particle.y));

        particle.move(0.1);

        System.out.println(String.format("After x: %f, y: %f", particle.x, particle.y));

        assertTrue("Failed to move right", particle.x > 0.5);
        assertTrue("Failed to move up", particle.y > 0.5);
    }

    @Test
    public void move_across_screen() {
        Particle particle = new Particle(0.0, 0.0, 0);

        System.out.println(String.format("Before x: %f, y: %f", particle.x, particle.y));

        for (int i = 1; i <= 10; i++) {
            particle.move(0.1);
            System.out.println(String.format("After %d move%s x: %f, y: %f", i, i == 1 ? "" : "s", particle.x, particle.y));

            assertEquals("Failed to move right", i * 0.1d, particle.x, 0.001d);
            assertEquals("Failed to remain horizontal", 0d, particle.y, 0.001d);
        }

        assertEquals("Failed to move right to the edge of the screen", 1.0d, particle.x, 0.001d);
        assertEquals("Failed to remain horizontal", 0d, particle.y, 0.001d);
    }

    @Test
    public void move_down_screen() {
        Particle particle = new Particle(0.5, 0.0, 90);

        System.out.println(String.format("Before x: %f, y: %f", particle.x, particle.y));

        for (int i = 1; i <= 10; i++) {
            particle.move(0.1);
            System.out.println(String.format("After %d move%s x: %f, y: %f", i, i == 1 ? "" : "s", particle.x, particle.y));

            assertEquals("Failed to remain in the middle", 0.5d, particle.x, 0.001d);
            assertEquals("Failed to move up",  (i * 0.1d), particle.y, 0.001d);
        }

        assertEquals("Failed to remain in the middle", 0.5d, particle.x, 0.001d);
        assertEquals("Failed to move up to the top of the screen", 1.0d, particle.y, 0.001d);
    }
}
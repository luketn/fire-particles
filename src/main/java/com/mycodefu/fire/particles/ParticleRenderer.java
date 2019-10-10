package com.mycodefu.fire.particles;

import java.util.List;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class ParticleRenderer {
    private static final boolean DRAW_TEMPERATURE = false;

    private final BufferedImage bufferedImage;
    private final Graphics graphics;
    private int width;
    private int height;

    public ParticleRenderer(int width, int height) {
        this.width = width;
        this.height = height;
        bufferedImage = new BufferedImage(width, height, TYPE_INT_RGB);
        graphics = bufferedImage.getGraphics();
    }

    private void eraseBackground() {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, width, height);
    }

    private void drawParticle(Graphics graphics, Particle particle) {
        drawParticle(graphics, particle, particle.getColor());
    }

    private void eraseParticle(Graphics graphics, Particle particle) {
        drawParticle(graphics, particle, Color.BLACK);
    }

    private void drawParticle(Graphics graphics, Particle particle, Color color) {
        int x = (int) (particle.x * this.width);
        int y = (int) (particle.y * this.height);

        int width = (int) (particle.width * this.width);
        int height = (int) (particle.height * this.height);

        graphics.setColor(color);
        graphics.fillOval(x, y, width, height);

        if (DRAW_TEMPERATURE) {
            graphics.setColor(Color.BLACK);
            String warmth = String.format("%.2f", particle.getWarmth());
            Rectangle2D stringBounds = graphics.getFontMetrics().getStringBounds(warmth, graphics);
            graphics.drawString(warmth, x + width / 2 - (int) stringBounds.getWidth() / 2, y + height / 2 + (int) stringBounds.getHeight() / 2);
        }
    }

    public void render(List<Particle> particles) {
        eraseBackground();
        for (Particle particle : particles) {
            drawParticle(graphics, particle);
        }
    }

    public void draw(Graphics drawTo, int offsetTop) {
        drawTo.drawImage(bufferedImage, 0, offsetTop, width, height, Color.BLACK, null);
    }
}

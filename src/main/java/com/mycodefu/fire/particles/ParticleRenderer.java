package com.mycodefu.fire.particles;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.List;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class ParticleRenderer {
    private static final boolean DRAW_LOCATION = false;

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

    private void drawParticle(Graphics graphics, Particle particle, Color color) {
        int x = (int) (particle.getLocation().x * this.width);
        int y = (int) (particle.getLocation().y * this.height);

        int width = (int) (particle.getWidth() * this.width);
        int height = (int) (particle.getHeight() * this.width);

        graphics.setColor(color);
        graphics.fillOval(x, y, width, height);

        if (DRAW_LOCATION) {
            graphics.setColor(Color.BLACK);
            String location = String.format("%d,%d\n%.2f,%.2f", x, y, particle.getLocation().x, particle.getLocation().y);
            Rectangle2D stringBounds = graphics.getFontMetrics().getStringBounds(location, graphics);
            graphics.drawString(location, x + width / 2 - (int) stringBounds.getWidth() / 2, y + height / 2 + (int) stringBounds.getHeight() / 2);
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

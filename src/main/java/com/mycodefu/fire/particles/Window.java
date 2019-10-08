package com.mycodefu.fire.particles;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class Window extends JFrame {
    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;

    private static final int TOP = 22;
    public static final int MAX_RANDOM_TURN_DEGREES = 15;
    public static final int NUMBER_OF_PARTICLES = 1_000;

    private BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, TYPE_INT_RGB);

    private Window() {
        Container cp = getContentPane();
        cp.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);
        pack();
        setBackground(Color.BLACK);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Fire Particles!");
        setVisible(true);

        Graphics graphics = bufferedImage.getGraphics();
        graphics.setColor(Color.BLACK);
        graphics.drawRect(0, 0, WIDTH, HEIGHT);

        Random random = new Random();
        graphics.setColor(Color.ORANGE.brighter());

        List<Particle> particles = new ArrayList<>();
        double size = 10d;
        double width = size / (double) WIDTH;
        double height = size / (double) HEIGHT;

        for (int i = 0; i < NUMBER_OF_PARTICLES; i++) {
            particles.add(new Particle(0.5, 0.5, random.nextDouble() * 360d, new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)), width, height));
        }

        AtomicLong counter = new AtomicLong();
        Timer loop = new Timer(30, e -> {
            for (Particle particle : particles) {
                eraseParticle(graphics, particle);
                particle.move(0.01);
                drawParticle(graphics, particle);

                if (counter.incrementAndGet() % (random.nextInt(42) + 1) == 0) {
                    if (random.nextBoolean()) {
                        particle.turnRight(random.nextDouble() * MAX_RANDOM_TURN_DEGREES);
                    } else {
                        particle.turnLeft(random.nextDouble() * MAX_RANDOM_TURN_DEGREES);
                    }
                }
            }

            this.repaint();
        });

        loop.start();
    }

    private void drawParticle(Graphics graphics, Particle particle) {
        drawParticle(graphics, particle, particle.color);
    }

    private void eraseParticle(Graphics graphics, Particle particle) {
        drawParticle(graphics, particle, Color.BLACK);
    }

    private void drawParticle(Graphics graphics, Particle particle, Color color) {
        int x = (int) (particle.x * WIDTH);
        int y = (int) (particle.y * HEIGHT);

        int width = (int) (particle.width * WIDTH);
        int height = (int) (particle.height * HEIGHT);

        graphics.setColor(color);
        graphics.fillOval(x, y, width, height);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(bufferedImage, 0, TOP, WIDTH, HEIGHT, Color.BLACK, null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Window::new);
    }
}

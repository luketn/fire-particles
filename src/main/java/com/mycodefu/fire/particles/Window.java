package com.mycodefu.fire.particles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Window extends JFrame implements KeyListener {
    private static int WIDTH = 1024;
    private static int HEIGHT = 768;
    private static int TOP = 22;

    private static final int NUMBER_OF_PARTICLES = 5_000;
    private static final int PARTICLE_SIZE_PIXELS = 3;

    private final ParticleRenderer particleRenderer;
    private final ParticleArena particleArena;

    static GraphicsDevice device = GraphicsEnvironment
            .getLocalGraphicsEnvironment().getScreenDevices()[0];

    private Window() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        WIDTH = screenSize.width;
        HEIGHT = screenSize.height;
        TOP = 0;

        device.setFullScreenWindow(this);

        addKeyListener(this);

        particleArena = new ParticleArena(WIDTH, HEIGHT);
        particleArena.seedParticles(NUMBER_OF_PARTICLES, PARTICLE_SIZE_PIXELS);

        ParticleMovement movementStrategy = new ParticleMovementHeatMap();

        particleRenderer = new ParticleRenderer(WIDTH, HEIGHT);
        Timer loop = new Timer(60, e -> {
            particleArena.tick(movementStrategy);
            particleRenderer.render(particleArena.particles);
            this.repaint();
        });

        loop.start();
    }

    @Override
    public void paint(Graphics g) {
        particleRenderer.draw(g, TOP);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Window::new);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            particleArena.reset();
            this.repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

package com.mycodefu;

import com.mycodefu.movement.ParticleMovement;
import com.mycodefu.movement.ParticleMovementHeatMap;
import com.mycodefu.particles.ParticleArena;
import com.mycodefu.particles.ParticleRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Window extends JFrame implements KeyListener {
    private static final int NUMBER_OF_PARTICLES = 10_000;
    private static final int PARTICLE_SIZE_PIXELS = 3;

    private final ParticleRenderer particleRenderer;
    private final ParticleArena particleArena;

    private Window() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
        device.setFullScreenWindow(this);

        addKeyListener(this);

        ParticleMovement movementStrategy = new ParticleMovementHeatMap();

        particleArena = new ParticleArena(movementStrategy, screenSize.width, screenSize.height);
        particleArena.seedParticles(NUMBER_OF_PARTICLES, PARTICLE_SIZE_PIXELS);

        particleRenderer = new ParticleRenderer(screenSize.width, screenSize.height);
        Timer loop = new Timer(60, e -> {
            particleArena.tick();
            particleRenderer.render(particleArena.getParticles());
            this.repaint();
        });

        loop.start();
    }

    @Override
    public void paint(Graphics g) {
        particleRenderer.draw(g);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Window::new);
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                particleArena.reset();
                this.repaint();
                break;
            case KeyEvent.VK_Q:
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }
}

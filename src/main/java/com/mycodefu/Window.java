package com.mycodefu;

import com.mycodefu.movement.ParticleMovement;
import com.mycodefu.movement.ParticleMovementHeatMap;
import com.mycodefu.movement.ParticleMovementSimpleBounce;
import com.mycodefu.particles.ParticleArena;
import com.mycodefu.particles.ParticleRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.List;

public class Window extends JFrame implements KeyListener {
    private static final int NUMBER_OF_PARTICLES = 10_000;
    private static final int PARTICLE_SIZE_PIXELS = 3;
    private final List<ParticleMovement> MOVEMENT_STRATEGIES = Arrays.asList(
            new ParticleMovementHeatMap(),
            new ParticleMovementSimpleBounce()
    );
    private int MOVEMENT_STRATEGY = 0;


    private final ParticleRenderer particleRenderer;
    private final ParticleArena particleArena;

    private Window() {
        var screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        var device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
        device.setFullScreenWindow(this);

        addKeyListener(this);

        var movementStrategy = MOVEMENT_STRATEGIES.get(MOVEMENT_STRATEGY);

        particleArena = new ParticleArena(movementStrategy, screenSize.width, screenSize.height);
        particleArena.seedParticles(NUMBER_OF_PARTICLES, PARTICLE_SIZE_PIXELS);

        particleRenderer = new ParticleRenderer(screenSize.width, screenSize.height);
        var loop = new Timer(60, e -> {
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
            case KeyEvent.VK_LEFT:
                MOVEMENT_STRATEGY--;
                if (MOVEMENT_STRATEGY<0){
                    MOVEMENT_STRATEGY = MOVEMENT_STRATEGIES.size()-1;
                }
                particleArena.changeStrategy(MOVEMENT_STRATEGIES.get(MOVEMENT_STRATEGY));
                break;
            case KeyEvent.VK_RIGHT:
                MOVEMENT_STRATEGY++;
                if (MOVEMENT_STRATEGY > MOVEMENT_STRATEGIES.size() - 1) {
                    MOVEMENT_STRATEGY = 0;
                }
                particleArena.changeStrategy(MOVEMENT_STRATEGIES.get(MOVEMENT_STRATEGY));
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }
}

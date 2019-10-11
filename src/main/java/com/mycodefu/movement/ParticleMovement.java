package com.mycodefu.movement;

import com.mycodefu.particles.Particle;
import com.mycodefu.particles.ParticleArena;

public interface ParticleMovement {
    /**
     * Move time along for the particle according to the strategy of the implementation.
     */
    void breathe(Particle particle, ParticleArena arena);

    /**
     * Reset a particle in response to a user requesting a reset of the particle arena.
     */
    void reset(Particle particle, ParticleArena arena);
}

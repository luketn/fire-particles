package com.mycodefu.fire.particles;

public interface ParticleMovement {
    /**
     * Move time along for the particle according to the strategy of the implementation.
     */
    void breathe(Particle particle, ParticleArena arena);
}

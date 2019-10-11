package com.mycodefu.fire.particles;

import org.junit.Test;

import static org.junit.Assert.*;

public class PhysicsTest {

    @Test
    public void calculateBounceAngle_straight_to_right_wall() {
        //hit the right hand vertical wall going straight, you bounce right back toward the left wall
        assertEquals(180d, Physics.calculateBounceAngle(Physics.WallType.Vertical, 0), 0.01d);
    }
    @Test
    public void calculateBounceAngle_45_to_right_wall() {
        //hit the right hand vertical wall going down at 45 degrees, you bounce back toward the left wall continuing down at a 90 degree rotation
        assertEquals(135d, Physics.calculateBounceAngle(Physics.WallType.Vertical, 45), 0.01d);
    }
    @Test
    public void calculateBounceAngle_135_to_left_wall() {
        //hit the left hand vertical wall going down at 135 degrees, you bounce back toward the opposite wall continuing down at a 90 degree rotation back
        assertEquals(45d, Physics.calculateBounceAngle(Physics.WallType.Vertical, 135), 0.01d);
    }
    @Test
    public void calculateBounceAngle_225_to_left_wall() {
        //hit the left hand vertical wall going down at 135 degrees, you bounce back toward the opposite wall continuing down at a 90 degree rotation back
        assertEquals(315d, Physics.calculateBounceAngle(Physics.WallType.Vertical, 225), 0.01d);
    }

    @Test
    public void calculateBounceAngle_up_to_top_wall() {
        //hit the top horizontal wall going straight up, you bounce right back down
        assertEquals(90d, Physics.calculateBounceAngle(Physics.WallType.Horizontal, 270d), 0.01d);
    }
    @Test
    public void calculateBounceAngle_45_to_bottom_wall() {
        //hit the bottom wall going down at 45 degrees, you bounce back up toward the opposite wall at a 90 degree left rotation
        assertEquals(315d, Physics.calculateBounceAngle(Physics.WallType.Horizontal, 45), 0.01d);
    }
}
package amalgamation.parts.arms;

import amalgamation.parts.Arm;

/**
 * A test Head implementation. It's basically a Red Stick.
 * 
 * This was hastily thrown together and is primarily for testing.
 *
 * @author Caleb Rush
 */
public class RedStick extends Arm {
    // All final implementations should have an empty contructor.
    // There should be a better way to handle the IOException.
    public RedStick() throws java.io.IOException {
        super("Red Stick", "Red Stick.png",
                // Base Stats
                5, 20, 5, 5,
                // Pivot position
                5, 5);
    }
}
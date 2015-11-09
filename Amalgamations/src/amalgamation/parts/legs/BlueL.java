package amalgamation.parts.legs;

import amalgamation.parts.Leg;

/**
 * A test Head implementation. It's basically a Blue L.
 * 
 * This was hastily thrown together and is primarily for testing.
 *
 * @author Caleb Rush
 */
public class BlueL extends Leg {
    // All final implementations should have an empty contructor.
    // There should be a better way to handle the IOException.
    public BlueL() throws java.io.IOException {
        super("Blue L", "Blue L.png",
                // Base Stats
                5, 5, 5, 20,
                // Pivot position
                10, 37);
    }
}
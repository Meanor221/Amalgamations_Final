package amalgamation.parts.heads;

import amalgamation.parts.Head;

/**
 * A test Head implementation. It's basically a Green Circle.
 * 
 * This was hastily thrown together and is primarily for testing.
 *
 * @author Caleb Rush
 */
public class GreenCircle extends Head {
    // All final implementations should have an empty contructor.
    public GreenCircle() throws java.io.IOException {
        super("Green Circle", "Green Circle.png",
                // Base Stats
                20, 5, 5, 5,
                // Pivot position
                75, 137);
    }
}

/**
 * Typically heads have moderate amount of defense and health. No speed or 
 * attack quantifier.
 * 
 */
package amalgamation.parts;

import java.io.IOException;

/**
 *
 * @author Jordan LaRiccia, Caleb Rush
 */
public abstract class Head extends Part {
    // Constructor for the head class that inherits from the part class
    public Head(String name, String imageFile, int baseHealth,
            int baseAttack, int baseDefense, int baseSpeed,
            int pivotX, int pivotY) throws IOException {
        super(name, imageFile, baseHealth, baseAttack, baseDefense, baseSpeed,
                pivotX, pivotY);
    }
    
    @Override
    public String imageDirectory() {
        return super.imageDirectory() + "Heads/";
    }
}

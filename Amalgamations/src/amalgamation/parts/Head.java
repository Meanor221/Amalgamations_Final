/**
 * Typically heads have moderate amount of defense and health. No speed or 
 * attack quantifier.
 * 
 */
package amalgamation.parts;

import amalgamations.abilities.Ability;
import java.io.IOException;

/**
 *
 * @author Jordan LaRiccia, Caleb Rush
 */
public class Head extends Part {
    // Constructor for the head class that inherits from the part class
    public Head(String name, String imageFile, int baseHealth,
            int baseAttack, int baseDefense, int baseSpeed,
            int pivotX, int pivotY, Ability[] abilities) {
        super(name, imageFile, baseHealth, baseAttack, baseDefense, baseSpeed,
                pivotX, pivotY, abilities);
    }
    
    @Override
    public String imageDirectory() {
        return Parts.HEADS_IMG_DIR;
    }
}

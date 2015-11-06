/**
 * Typically heads have moderate amount of defense and health. No speed or 
 * attack quantifier.
 * 
 */
package amalgamation.parts;

import java.awt.image.BufferedImage;

/**
 *
 * @author Jordan LaRiccia
 */
public abstract class Head extends Part {
    // Constructor for the head class that inherits from the part class
    public Head(String name, BufferedImage image, int baseHealth,
            int baseAttack, int baseDefense, int baseSpeed,
            int pivotX, int pivotY) {
        super(name, image, baseHealth, baseAttack, baseDefense, baseSpeed,
                pivotX, pivotY);
    }
}

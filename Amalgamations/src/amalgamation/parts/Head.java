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

    /**
     * Constructor for the head class that inherits from the part class
     * 
     * @param name The name of the head part
     * @param image The buffered image of the head part
     * @param baseHealth  The base health for the head part
     * @param baseAttack The base attack for the head part
     * @param baseDefense The base defense for the head part
     * @param baseSpeed The base speed for the head part
     */
    public Head(String name, BufferedImage image, int baseHealth,
            int baseAttack, int baseDefense, int baseSpeed) {
        super(name, image, baseHealth, baseAttack, baseDefense, baseSpeed);
    }
    
}

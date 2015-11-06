package amalgamation;

import java.awt.image.BufferedImage;

/**
 * It is an extension of Part class. It is itself a collection of parts and its
 * primary stat is defense
 *
 * @author Caleb Rush, Adam Meanor
 */
public class Body extends Part {
    // Constructor compatible with Part constructor.
    public Body(String name, BufferedImage image,
            int baseHealth, int baseAttack, int baseDefense, int baseSpeed) {
        super(name, image, baseHealth, baseAttack, baseDefense, baseSpeed);
    }
}

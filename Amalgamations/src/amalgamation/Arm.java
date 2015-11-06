package amalgamation;

import java.awt.image.BufferedImage;

/**
 * An Arm is a type of Part that makes up an Amalgamation.
 * 
 * Typically, an Arm supplies primarily to the Attack base stat with some minor
 * additions to other areas.
 * 
 * An Arm is still an abstract type of body part. The Arm, Head, and Leg class
 * are primarily used to identify the body part type of more specific body
 * parts. A final concrete class should extend one of these classes and not the
 * Part class directly.
 * 
 * @author Caleb Rush
 */
public class Arm extends Part {
    // Compatible constructor with the Part contructor.
    public Arm(String name, BufferedImage image,
            int baseHealth, int baseAttack, int baseDefense, int baseSpeed) {
        // Call the Part constructor with the given parameters.
        super(name, image, baseHealth, baseAttack, baseDefense, baseSpeed);
    }
}

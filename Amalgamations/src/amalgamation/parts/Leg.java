package amalgamation.parts;

import java.awt.image.BufferedImage;

/**
 * A Leg represents a part of the Amalgamation specifically the legs of the 
 * Amalgamation.
 * 
 * It will include values for all the base 
 * stats, however the leg will focus mainly on the agility stat. 
 * It will also have a name and image.
 * 
 * @author Adam Meanor
 */
public abstract class Leg extends Part {
    
    // Compatablie Constructor with the Part constructor
    public Leg(String name, BufferedImage image, 
            int baseHealth, int baseAttack, int baseDefense, int baseSpeed,
            int pivotX, int pivotY) {
        // Calls the PArt class constructor
        super(name, image, baseHealth, baseAttack, baseDefense, baseSpeed,
                pivotX, pivotY);
    }
}

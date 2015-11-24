package amalgamation.parts;

import util.Parts;
import amalgamation.abilities.Ability;
import java.io.IOException;

/**
 * A Leg represents a part of the Amalgamation specifically the legs of the 
 * Amalgamation.
 * 
 * It will include values for all the base 
 * stats, however the leg will focus mainly on the agility stat. 
 * It will also have a name and image.
 * 
 * @author Adam Meanor, Caleb Rush
 */
public class Leg extends Part {
    
    // Compatablie Constructor with the Part constructor
    public Leg(String name, String imageFile, 
            int baseHealth, int baseAttack, int baseDefense, int baseSpeed,
            int pivotX, int pivotY, Ability[] abilities) {
        // Calls the PArt class constructor
        super(name, imageFile, baseHealth, baseAttack, baseDefense, baseSpeed,
                pivotX, pivotY, abilities);
    }
    
    @Override
    public String imageDirectory() {
        return Parts.LEGS_IMG_DIR;
    }
}

package amalgamation.parts;

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
public abstract class Leg extends Part {
    
    // Compatablie Constructor with the Part constructor
    public Leg(String name, String imageFile, 
            int baseHealth, int baseAttack, int baseDefense, int baseSpeed,
            int pivotX, int pivotY) throws IOException {
        // Calls the PArt class constructor
        super(name, imageFile, baseHealth, baseAttack, baseDefense, baseSpeed,
                pivotX, pivotY);
    }
    
    @Override
    public String imageDirectory() {
        return super.imageDirectory() + "Legs/";
    }
}

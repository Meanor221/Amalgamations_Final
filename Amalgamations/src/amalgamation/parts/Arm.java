package amalgamation.parts;

import util.Parts;
import amalgamation.abilities.Ability;
import java.io.IOException;
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
    public Arm(String name, String imageFile, 
            int baseHealth, int baseAttack, int baseDefense, int baseSpeed,
            int pivotX, int pivotY, Ability[] abilities) {
        // Calls the Part class constructor
        super(name, imageFile, baseHealth, baseAttack, baseDefense, baseSpeed,
                pivotX, pivotY, abilities);
    }
    
    @Override
    public String imageDirectory() {
        return Parts.ARMS_IMG_DIR;
    }
}

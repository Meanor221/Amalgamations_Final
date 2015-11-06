package amalgamation;

import java.awt.image.BufferedImage;

/**
 * 
 * It is an extension of Part class. It is itself a collection of parts and it's
 * primary stat is defense
 *
 * @author cir5274 aam5617
 */
public class Body extends Part {
    public Body(String name, BufferedImage image,
            int baseHealth, int baseAttack, int baseDefense, int baseSpeed) {
        super(name, image, baseHealth, baseAttack, baseDefense, baseSpeed);
    }
}

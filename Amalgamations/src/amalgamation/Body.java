package amalgamation;

import java.awt.image.BufferedImage;

/**
 *
 * @author cir5274
 */
public class Body extends Part {
    public Body(String name, BufferedImage image,
            int baseHealth, int baseAttack, int baseDefense, int baseSpeed) {
        super(name, image, baseHealth, baseAttack, baseDefense, baseSpeed);
    }
}

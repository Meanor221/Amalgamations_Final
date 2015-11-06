package amalgamation.parts;

import java.awt.image.BufferedImage;

/**
 * A Part represents a body part on an Amalgamation.
 * 
 * Every Part has a list of base stats that collectively make up the stats of
 * the final Amalgamation.
 * 
 * @author Caleb Rush
 */
public abstract class Part {
    // The name of the body part. To be used in menus.
    private final String name;
    // The image that will represent the body part graphically.
    private final BufferedImage image;
    // The base Health stat that the body part supplies.
    private final int baseHealth;
    // The base Attack stat that the body part supplies.
    private final int baseAttack;
    // The base Defense stat that the body part supplies.
    private final int baseDefense;
    // The base Speed stat that the body part supplies.
    private final int baseSpeed;
    
    /**
     * Constructs an object of the Part class.
     * 
     * The constructor is where all properties of a specific Part should be
     * specified. All implementing subclasses should call this constructor
     * specifying these properties. Most subclasses of this class should be
     * relatively small.
     * 
     * @param name the name of the body part to be used in menus
     * @param image the image that will act as the graphical representation of
     *              the body part
     * @param baseHealth the base Health stat that the body part will supply
     * @param baseAttack the base Attack stat that the body part will supply
     * @param baseDefense the base Defense stat that the body part will supply
     * @param baseSpeed the base Speed stat that the body part will supply
     */
    public Part(String name, BufferedImage image, 
            int baseHealth, int baseAttack, int baseDefense, int baseSpeed) {
        // Initialize all instance variables.
        this.name = name;
        this.image = image;
        this.baseHealth = baseHealth;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.baseSpeed = baseSpeed;
    }
    
    /**
     * Returns the base Attack that the body part will supply to the final
     * Amalgamation.
     * 
     * @return the base Attack
     */
    public int getBaseAttack() {
        return baseAttack;
    }
    
    /**
     * Returns the base Defense that the body part will supply to the final
     * Amalgamation.
     * 
     * @return the base Defense
     */
    public int getBaseDefense() {
        return baseDefense;
    }
    
    /**
     * Returns the base Health that the body part will supply to the final
     * Amalgamation.
     * 
     * @return the base Health
     */
    public int getBaseHealth() {
        return baseHealth;
    }
    
    /**
     * Returns the base Speed that the body part will supply to the final
     * Amalgamation.
     * 
     * @return the base Speed
     */
    public int getBaseSpeed() {
        return baseSpeed;
    }
    
    /**
     * Retrieves the image that will be used to represent the body part
     * graphically.
     * 
     * @return the body part's image
     */
    public BufferedImage getImage() {
        return image;
    }
    
    /**
     * Retrieves the name of the body part. The name is what should be used to
     * identify a specific body part in the menus.
     * 
     * @return the name of the body part
     */
    public String getName() {
        return name;
    }
    
}


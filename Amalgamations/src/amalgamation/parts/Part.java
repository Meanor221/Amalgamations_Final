package amalgamation.parts;

import util.Parts;
import amalgamation.abilities.Ability;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

/**
 * A Part represents a body part on an Amalgamation.
 * 
 * Every Part has a list of base stats that collectively make up the stats of
 * the final Amalgamation.
 * 
 * @author Caleb Rush
 */
public abstract class Part implements Serializable {
    // The name of the body part. To be used in menus.
    private final String name;
    // The name of the image that will represent the body part graphically.
    private final String imageFile;
    // The image that will represent the body part graphically.
    private transient BufferedImage image;
    // The base Health stat that the body part supplies.
    private final int baseHealth;
    // The base Attack stat that the body part supplies.
    private final int baseAttack;
    // The base Defense stat that the body part supplies.
    private final int baseDefense;
    // The base Speed stat that the body part supplies.
    private final int baseSpeed;
    // The x position of the Part's pivot position.
    private final int pivotX;
    // The y position of the Part's pivot position.
    private final int pivotY;
    // List of the part's available Abilities
    private final Ability[] abilities;
    
    /**
     * Constructs an object of the Part class.
     * 
     * The constructor is where all properties of a specific Part should be
     * specified. All implementing subclasses should call this constructor
     * specifying these properties. Most subclasses of this class should be
     * relatively small.
     * 
     * @param name the name of the body part to be used in menus
     * @param imageFile the name of the image file in the appropriate directory
     *                  to attempt to load the image from
     * @param baseHealth the base Health stat that the body part will supply
     * @param baseAttack the base Attack stat that the body part will supply
     * @param baseDefense the base Defense stat that the body part will supply
     * @param baseSpeed the base Speed stat that the body part will supply
     * @param pivotX the X coordinate of the position the Part's image will
     *               pivot around when rotated
     * @param pivotY the Y coordinate of the position the Part's image will
     *               pivot around when rotated
     * @param abilities the list of the Part's available moves/abilities which are of type Ability
     */
    public Part(String name, String imageFile, 
            int baseHealth, int baseAttack, int baseDefense, int baseSpeed,
            int pivotX, int pivotY, Ability[] abilities) {
        // Initialize all instance variables.
        this.name = name;
        this.imageFile = imageFile;
        this.baseHealth = baseHealth;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.baseSpeed = baseSpeed;
        this.pivotX = pivotX;
        this.pivotY = pivotY;
        this.abilities = abilities;
    }
    
    /**
     * Returns the array containing all of the abilities of this part
     * @return the abilities array
     */
    public Ability[] getAbilities() {
        return abilities;
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
     * The first time this method is called, the image will be loaded from the 
     * file containing the Part's image. If this operation fails, null will be
     * returned. If the image is successfully loaded, subsequent calls of this
     * method will not need to load the image from the file again.
     * 
     * @return the body part's image
     */
    public BufferedImage getImage() {
        // If the image has not been loaded yet, load it from the file.
        if (image == null) {
            try {
                image = loadImage(imageFile);
            } catch (IOException e) {
                image = null;
            }
        }
        
        return image;
    }
    
    /**
     * Retrieves the absolute path of the image file.
     * 
     * @return the absolute path of the image file.
     */
    public String getImageFile() {
        return new File(imageDirectory() + imageFile).getAbsolutePath();
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

    /**
     * Retrieves the X position of the Part's Pivot position.
     * 
     * @return the X position of the Part's Pivot position.
     */
    public int getPivotX() {
        return pivotX;
    }
    
    /**
     * Retrieves the Y position of the Part's Pivot position.
     * 
     * @return the Y position of the Part's Pivot position.
     */
    public int getPivotY() {
        return pivotY;
    }
    
    /**
     * Returns the relative Path where the image files for this particular type
     * of Part should be located. 
     * 
     * This method should only be overriden by a subclass if the subclass has a
     * different directory where its images should be stored.
     * 
     * @return the relative Path to the folder containing Part image files.
     */
    public String imageDirectory() {
        return Parts.PARTS_IMG_DIR;
    }
    
    /**
     * Loads an image from the file with the specified name.
     * 
     * This method automatically appends the given file name to the directory
     * returned by the imageDirectory method. Any image that needs to be loaded
     * should be placed in the correct directory so that only the name of the
     * file needs to be specified.
     * 
     * @param fileName
     * @return the BufferedImage loaded from the given file
     * @throws IOException if the file does not exist or does not have read
     *                     permissions for the current user
     */
    public BufferedImage loadImage(String fileName) throws IOException {
        return ImageIO.read(new File(imageDirectory() + fileName));
    }
}


package amalgamation.parts;

import java.io.File;
import java.io.IOException;

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
    // The x position of the Part's pivot position.
    private final int pivotX;
    // The y position of the Part's pivot position.
    private final int pivotY;
    
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
     * @throws IOException if the image file name does not point to a valid file
     *                     or if the file it refers to does not have read access
     */
    public Part(String name, String imageFile, 
            int baseHealth, int baseAttack, int baseDefense, int baseSpeed,
            int pivotX, int pivotY) throws IOException {
        // Initialize all instance variables.
        this.name = name;
        this.image = loadImage(imageFile);
        this.baseHealth = baseHealth;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.baseSpeed = baseSpeed;
        this.pivotX = pivotX;
        this.pivotY = pivotY;
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
        return "res/img/Parts/";
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
    
    /**
     * Draws the Part's image on the given image.
     * 
     * The X and Y coordinates determine the position on the image
     * that the Part's image will draw its pivot point. The rotation determines 
     * how many radians the image will rotate around its pivot point clockwise.
     * 
     * @param img the image to draw the Part on
     * @param x the X position to draw the Part image's pivot point
     * @param y the Y position to draw the Part image's pivot point
     * @param rotation the number of radians to rotate the Part image clockwise
     *                 around the pivot point
     */
    public void render(BufferedImage img, int x, int y, double rotation) {
        // Create a new image that is the same size as the given image.
        BufferedImage temp = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        
        // Draw the part's image on the temp image.
        Graphics2D g = temp.createGraphics();
        g.drawImage(
                image, 
                // Ensure the pivot point is at (x, y).
                x - pivotX,
                y - pivotY, 
                null
        );
        g.dispose();
        
        // Draw the temp image over top the given image.
        g = img.createGraphics();
        g.drawImage(
                temp, 
                // Rotate the image around the pivot point.
                AffineTransform.getRotateInstance(rotation, x, y),
                null
        );
        g.dispose();
    }
}


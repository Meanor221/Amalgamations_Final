package amalgamation.parts;

import java.io.Serializable;

/**
 * A Slot specifies an area on a body where a specific type of body part can be
 * connected. 
 * 
 * On top of containing the actual body part, a Slot also contains X and Y
 * coordinates that point to the specific spot on the Body where the Slot is
 * located. The Slot also contains a Z index which specifies the layer that the
 * body part should be drawn on. Finally, a Slot also contains an angle which
 * specifies how much the body part should be rotated when drawn.
 * 
 * @param <T> the type of body part that can be placed in this slot. Should be 
 *            one of the following types:
 *            <ul>
 *                <li>Arm</li>
 *                <li>Head</li>
 *                <li>Leg</li>
 *                <li>Body (if you're feeling particularly dangerous)</li>
 *            </ul>
 * @author Caleb Rush
 */
public class Slot<T extends Part> implements Serializable {
    // The x position of the slot on the body.
    private final int x;
    // The y position of the slot on the body.
    private final int y;
    // The z index that determines the layer the part should be drawn.
    private final int z;
    // The number of radians the body part should be rotated.
    private double rotation;
    // The body part connected to the slot.
    private T part;
    
    /**
     * Constructs a new Slot object positioned at the Body's origin point (0, 0)
     * (top left corner), a Z index of zero, and an initial rotation of zero 
     * radians.
     * 
     * Once set, the X and Y coordinates and Z index cannot be changed.
     */
    public Slot() {
        this(0, 0, 0, 0);
    }
    
    /**
     * Constructs a new Slot object with the specified position, a Z index of
     * zero, and an initial rotation of zero radians.
     * 
     * Once set, the X and Y coordinates and Z index cannot be changed.
     * 
     * @param x the X coordinate of the Slot's position on the Body
     * @param y the Y coordinate of the Slot's position on the Body
     */
    public Slot(int x, int y) {
        this(x, y, 0, 0);
    }
    
    /**
     * Constructs a new Slot object with the specified position and Z index and 
     * an initial rotation of zero radians.
     * 
     * Once set, the X and Y coordinates and Z index cannot be changed.
     * 
     * @param x the X coordinate of the Slot's position on the Body
     * @param y the Y coordinate of the Slot's position on the Body
     * @param z the Z index determining the layer that the body part should be
     *          drawn on
     */
    public Slot(int x, int y, int z) {
        this(x, y, z, 0);
    }
    
    /**
     * Constructs a new Slot object with the specified position and rotation.
     * 
     * Once set, the X and Y coordinates and Z index cannot be changed.
     * 
     * @param x the X coordinate of the Slot's position on the Body
     * @param y the Y coordinate of the Slot's position on the Body
     * @param z the Z index determining the layer that the body part should be
     *          drawn on
     * @param rotation the number of radians the body part should be rotated
     */
    public Slot(int x, int y, int z, double rotation) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.rotation = rotation;
    }
    
    /**
     * Retrieves the Part connected to the Slot.
     * 
     * @return the Part connected to the Slot. The specific type of this Part
     *         will be the type specified as T (one of Arm, Leg, etc.). If no
     *         Part is connected to the Slot, <b>null</b> is returned.
     */
    public T getPart() {
        return part;
    }
    
    /**
     * Retrieves the number of radians the body part should be rotated.
     * 
     * @return Retrieves the number of radians the body part should be rotated.
     *         A value of zero means that the body part will have its natural
     *         orientation. A positive value rotates the body part clockwise,
     *         while a negative value rotates the body part counterclockwise.
     */
    public double getRotation() {
        return rotation;
    }
    
    /**
     * Retrieves the number of degrees the body part should be rotated.
     * 
     * This is a convenience method that simply calculates the number of degrees
     * from the actual rotation value (which is in radians).
     * 
     * @return Retrieves the number of degrees the body part should be rotated.
     *         A value of zero means that the body part will have its natural
     *         orientation. A positive value rotates the body part clockwise,
     *         while a negative value rotates the body part counterclockwise.
     */
    public double getRotationDegrees() {
        return (rotation * 180) / Math.PI;
    }
    
    /**
     * Retrieves the X coordinate of the Slot's position on the body.
     * @return the X coordinate of the Slot's position on the body. This is
     *         relative to the Body's origin point (top left corner). Larger
     *         values of X move further to the right, and vice versa.
     */
    public int getX() {
        return x;
    }
    
    /**
     * Retrieves the Y coordinate of the Slot's position on the body.
     * @return the Y coordinate of the Slot's position on the body. This is
     *         relative to the Body's origin point (top left corner). Larger
     *         values of Y move further down, and vice versa.
     */
    public int getY() {
        return y;
    }
    
    /**
     * Retrieves the Z index which specifies the layer that the body part should
     * be drawn on.
     * @return the Z index which specifies the layer that the body part should
     *         be drawn on. Lower values mean that the body part will be drawn
     *         sooner, and will therefore be drawn over when body parts on
     *         higher layers are drawn. The Body is drawn with a Z index
     *         of zero.
     */
    public int getZ() {
        return z;
    }
    
    /**
     * Connects a new Part to the slot. Any Part that was previously connected
     * will be disconnected.
     * 
     * @param part the new Part to connect. This should be the type specified
     *             as T (one of Arm, Leg, etc.).
     */
    public void setPart(T part) {
        this.part = part;
    }
    
    /**
     * Sets the rotation of the body part.
     * 
     * @param rotation the number of radians the body part should be rotated
     *                 clockwise. A negative value rotates the body part 
     *                 counterclockwise.
     */
    public void setRotation(double rotation) {
        this.rotation = rotation;
    }
    
    /**
     * Sets the rotation of the body part.
     * 
     * This is a convenience method that simply converts the given number of
     * degrees into radians and passes it to the <code>setRotation</code>
     * method.
     * 
     * @param rotation the number of degrees the body part should be rotated
     *                 clockwise. A negative value rotates the body part 
     *                 counterclockwise.
     */
    public void setRotationDegrees(double rotation) {
        this.rotation = (rotation * Math.PI) / 180;
    }
}

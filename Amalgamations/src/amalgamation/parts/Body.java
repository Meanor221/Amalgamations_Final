package amalgamation.parts;

import amalgamation.abilities.Ability;
import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * It is an extension of Part class. It is itself a collection of parts and its
 * primary stat is defense
 *
 * @author Caleb Rush, Adam Meanor
 */
public class Body extends Part {
    // The list of arm slots on the body.
    private final Slot<Arm>[] arms;
    // The list of head slots on the body.
    private final Slot<Head>[] heads;
    // The list of Leg slots on the body.
    private final Slot<Leg>[] legs;
    
    /**
     * Constructs a Body object.
     * 
     * When constructing a Body object, all Slots on the Body need to be
     * specified by passing arrays of corresponding Slots. All Slots in the
     * arrays should be initialized, as the Body will not initialize them
     * itself. If there are no Slots of a specified type, pass an empty array
     * to the argument requiring that type's array. <b>DO NOT PASS null AS AN
     * ARGUMENT FOR ANY OF THE ARRAYS.</b>
     * 
     * @param name the name of the body part to be used in menus
     * @param imageFile the name of the image file in the appropriate directory
     *                  to attempt to load the image from
     * @param baseHealth the base Health stat that the body part will supply
     * @param baseAttack the base Attack stat that the body part will supply
     * @param baseDefense the base Defense stat that the body part will supply
     * @param baseSpeed the base Speed stat that the body part will supply
     * @param arms the array of Arm slots on the body
     * @param heads the array of Head slots on the body
     * @param legs the array of Leg slots on the body
     */
    public Body(String name, String imageFile,
            int baseHealth, int baseAttack, int baseDefense, int baseSpeed,
            Slot<Arm>[] arms, Slot<Head>[] heads, Slot<Leg>[] legs, Ability[] abilities) {
        // Call Part constructor with the pivot located at (0, 0).
        super(name, imageFile, baseHealth, baseAttack, baseDefense, baseSpeed, 
                0, 0, abilities);
        // Initialize arrays of slots.
        this.arms = arms;
        this.heads = heads;
        this.legs = legs;
    }
    
    /**
     * Generates the graphical representation of the body with all of the body
     * parts connected to its slots. 
     * 
     * This method should be used to update the image of the body and its
     * connected parts when the status of one of the body's slots changes (via
     * changing the rotation or connecting/disconnecting a part). A reference
     * to the image returned by this image should be saved instead of constantly
     * calling this method, as it is not a simple operation.
     * 
     * @return the full image of the body combined with all of its connected
     *         parts
     */
    public BufferedImage fullImage() {
        // Create an image matching the size of the boy's image.
        final BufferedImage fullImage = new BufferedImage(getImage().getWidth(), 
                getImage().getHeight(), BufferedImage.TYPE_INT_ARGB);
        
        // Create an ArrayList to hold all of the slots.
        ArrayList<Slot> slots = new ArrayList<>(Arrays.asList(getSlots()));
        // Add a Slot containing the body so that it gets drawn properly.
        slots.add(new Slot());
        slots.get(slots.size() - 1).setPart(this);
        
        // Retrieve a stream of the full list of slots.
        Arrays.stream(slots.toArray(new Slot[0]))
                // Filter out the slots that don't have conencted parts.
                .filter(s -> s.getPart() != null)
                // Sort the slots by their Z indices in order to "layer" them.
                .sorted((s1, s2) -> s1.getZ() - s2.getZ())
                // Draw each slot's body part to the image.
                .forEach(s -> s.render(fullImage));
        
        return fullImage;
    }
    
    /**
     * Retrieves the list of Slots that can have Arm objects connected to them.
     * 
     * The Slots in the retrieved array should all be initialized, but they
     * may not necessarily all contain an Arm.
     * 
     * @return the list of Slots that can have Arm objects connected to them
     */
    public Slot<Arm>[] getArmSlots() {
        return arms;
    }
    
    /**
     * Retrieves the list of Slots that can have Head objects connected to them.
     * 
     * The Slots in the retrieved array should all be initialized, but they
     * may not necessarily all contain a Head.
     * 
     * @return the list of Slots that can have Head objects connected to them
     */
    public Slot<Head>[] getHeadSlots() {
        return heads;
    }
    
    /**
     * Retrieves the list of Slots that can have Leg objects connected to them.
     * 
     * The Slots in the retrieved array should all be initialized, but they
     * may not necessarily all contain a Leg.
     * 
     * @return the list of Slots that can have Leg objects connected to them
     */
    public Slot<Leg>[] getLegSlots() {
        return legs;
    }
    
    /**
     * Retrieves the full list of all Slots of all types.
     * 
     * The type of the Slots returned are the most generic type of Slot. This
     * means that there is no way to determine what type of body part can be
     * connected to the slot without using <code>instanceof</code>.
     * 
     * Ideally, this method should not be used to connect parts to the Slots.
     * Use the more specific getXXXSlots methods to retrieve the necessary
     * Slots. This method's use should be used simply to evaluate the Slots.
     * 
     * @return the list of all the Slots of all types on the body.
     */
    public Slot[] getSlots() {
        // Create an ArrayList to store every slot.
        ArrayList<Slot> slots = new ArrayList<>();
        
        // Add all arm slots to the ArrayList.
        slots.addAll(Arrays.asList(arms));
        // Add all head slots to the ArrayList.
        slots.addAll(Arrays.asList(heads));
        // Add all leg slots to the ArrayList.
        slots.addAll(Arrays.asList(legs));
        
        // Convert the ArrayList to an array.
        return slots.toArray(new Slot[0]);
    }
    
    @Override
    public String imageDirectory() {
        return Parts.BODIES_IMG_DIR;
    }
    
    /**
     * Calculates the total Base Attack stat from all of the combined Base
     * Health stats from the connected body parts.
     * 
     * @return the total Base Attack of the body an all connected parts
     */
    public int totalBaseAttack() {
        // Retrieve the Body's base health.
        int baseAttack = getBaseAttack();
        
        // Sum up the the base attack of each connected part.
        for (Slot s : getSlots())
            // Check if the slot contains a part.
            if (s.getPart() != null)
                // Add the part's base attack to the total.
                baseAttack += s.getPart().getBaseAttack();

        return baseAttack;
    }
    
    /**
     * Calculates the total Base Defense stat from all of the combined Base
     * Defense stats from the connected body parts.
     * 
     * @return the total Base Attack of the body an all connected parts
     */
    public int totalBaseDefense() {
        // Retrieve the Body's base defense.
        int baseDefense = getBaseDefense();
        
        // Sum up the the base defense of each connected part.
        for (Slot s : getSlots())
            // Check if the slot contains a part.
            if (s.getPart() != null)
                // Add the part's base defense to the total.
                baseDefense += s.getPart().getBaseDefense();

        return baseDefense;
    }
    
    /**
     * Calculates the total Base Health stat from all of the combined Base
     * Health stats from the connected body parts.
     * 
     * @return the total Base Health of the body an all connected parts
     */
    public int totalBaseHealth() {
        // Retrieve the Body's base health.
        int baseHealth = getBaseHealth();
        
        // Sum up the the base health of each connected part.
        for (Slot s : getSlots())
            // Check if the slot contains a part.
            if (s.getPart() != null)
                // Add the part's base health to the total.
                baseHealth += s.getPart().getBaseHealth();

        return baseHealth;
    }
    
    /**
     * Calculates the total Base Speed stat from all of the combined Base
     * Speed stats from the connected body parts.
     * 
     * @return the total Base Speed of the body an all connected parts
     */
    public int totalBaseSpeed() {
        // Retrieve the Body's base speed.
        int baseSpeed = getBaseSpeed();
        
        // Sum up the the base speed of each connected part.
        for (Slot s : getSlots())
            // Check if the slot contains a part.
            if (s.getPart() != null)
                // Add the part's base speed to the total.
                baseSpeed += s.getPart().getBaseSpeed();

        return baseSpeed;
    }
}

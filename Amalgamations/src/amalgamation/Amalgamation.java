package amalgamation;
import menus.components.AbilityReplaceDialog;
import amalgamation.abilities.Ability;
import amalgamation.parts.Body;

import java.awt.image.BufferedImage;

import java.io.Serializable;

import java.util.Random;

/**
 * An Amalgamation is a character that contains a body, name, and calculated
 * stats. 
 * 
 * @author Adam Meanor, Caleb Rush
 */
public class Amalgamation implements Serializable {    
    // The range in which the variance can be calculated.
    private static final double VARIANCE_RANGE = 0.3;
    // The highest level that can be reached (also used for calculations).
    public static final int     MAX_LEVEL = 100;
    
    // The Body containing all the Parts that make up the Amalgamation.
    private final Body      body;
    // The Abilities the Amalgamation can use in battle.
    private final Ability[] abilities = new Ability[4];
    // The name of the Amalgamation.
    private final String    name;
    // The graphical representation of the Amalgamation.
    private transient BufferedImage   fullImage;
    // The level of the Amalgamation.
    private int             level;
    // The amount of experience the Amalgamation has,
    private int             experience;
    // The amount of experience the Amalgamation needs to level up.
    private int             targetExperience;
    // The randomized variance of all the stats.
    private final double    healthVariance;
    private final double    attackVariance;
    private final double    defenseVariance;
    private final double    speedVariance;
    private final double    luckVariance;
    // The calculated stats.
    private int             health;
    private int             attack;
    private int             defense;
    private int             speed;
    // The current stats (used for battle when stats get modified).
    private int             currentHealth;
    private int             currentAttack;
    private int             currentDefense;
    private int             currentSpeed;
    
    /**
     * Constructs a new Amalgamation with the given name and Body.
     * 
     * Once the Amalgamation's name and Body are set, they cannot be changed.
     * 
     * @param name the name of the Amalgamation.
     * @param body the Body of the Amlagamation
     */
    public Amalgamation(String name, Body body) {
        this.name = name;
        this.body = body;
        
        Random random   =   new Random();
        healthVariance  =   random.nextDouble() % VARIANCE_RANGE + 0.85;
        speedVariance   =   random.nextDouble() % VARIANCE_RANGE + 0.85;
        attackVariance  =   random.nextDouble() % VARIANCE_RANGE + 0.85;
        defenseVariance =   random.nextDouble() % VARIANCE_RANGE + 0.85;
        luckVariance    =   random.nextDouble() % VARIANCE_RANGE + 1.0;
        
        // Level up initially to start at Level 1.
        levelUp();
        calculateStats();
    }
    
    /**
     * Attempts to add the given Ability to the Amalgamation's list of Abilities
     * it can use in battle.
     * 
     * @param ability the Ability to attempt to add
     * @return true if the Ability was successfully added; false otherwise
     */
    public boolean addAbility(Ability ability) {
        for (int i = 0; i < abilities.length; i++) {
            // If the spot in the array is empty, put the Ability there.
            if (abilities[i] == null) {
                abilities[i] = ability;
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Retrieves all of the Abilities the Amalgamation can learn at its current
     * level based on its Parts.
     * 
     * @return the Abilities the Amalgamation can learn at its current level
     */
    public Ability[] availableAbilities() {
        return java.util.stream.Stream.of(body.allAbilities())
                // Filter out the Abilities that cannot be learned yet.
                .filter(a -> level >= a.getLevel())
                .toArray(Ability[]::new);
    }
    
    /**
     * Calculates the stats of the Amalgamation based on current level
     */
    private void calculateStats() {
        health  = (int)(10 * ((double)level/(double)MAX_LEVEL) 
                * body.totalBaseHealth() * healthVariance) + 20;
        
        attack  = (int)(2 * ((double)level/(double)MAX_LEVEL) 
                * body.totalBaseAttack() * attackVariance) + 5;
        
        defense = (int)(2 * ((double)level/(double)MAX_LEVEL) 
                * body.totalBaseDefense() * defenseVariance) + 5;
        
        speed   = (int)(2 * ((double)level/(double)MAX_LEVEL) 
                * body.totalBaseSpeed() * speedVariance) + 5;
        
        experience -= targetExperience;
        
        targetExperience = (int)(Math.pow(MAX_LEVEL, 2) / 
                (1 + Math.pow( Math.E, (-0.08 * (level - 50)))));
        
        resetCurrentStats();
    }

    /**
     * Decreases the current Amalgamation's currentHealth most likely due to an
     * opponents attack.
     * 
     * @param damage the amount to reduce the Amalgamation's currentHealth by.
     * @return true if the damage lowered the Amaglamation's currentHealth to
     *         zero, false otherwise.
     */
    public boolean doDamage(int damage) { 
        currentHealth = currentHealth - damage;
        
        // Ensure the currentHealth does not fall below 0.
        if (currentHealth < 0) {
            currentHealth = 0;
            return true;
        }
        
        return false;
    }

    /**
     * Increases the current Amalgamation's experience total
     * 
     * @param experience the amount of experience to add to the Amalgamation's total
     *            experience
     */
    public void gainExp(double experience) {
        if (experience < 0)
            return;
        
        this.experience += experience;
        
        // Check if the Amalgamation can level up.
        if (this.experience >= targetExperience)
            levelUp();
    }
    
    /**
     * Retrieves the Amalgamation's list of Abilities.
     * 
     * @return the Amalgamation's list of Abilities
     */
    public Ability[] getAbilities() {
        return abilities;
    }
    
    /**
     * Retrieves the attack stat of the Amalgamation.
     * 
     * @return the attack stat of the Amalgamation
     */
    public int getAttack() {
        return attack;
    }
    
    /**
     * Retrieves the Body that makes up the full collection of Parts for this
     * Amalgamation.
     * 
     * @return the Body that makes up the full collection of Parts for this
     *         Amalgamation
     */
    public Body getBody() {
        return body;
    }
    
    /**
     * Retrieves the currentAttack of the Amalgamation.
     * 
     * The currentAttack is the stat that should be used in battle, as it is the
     * one that is modified.
     * 
     * @return the currentAttack of the Amalgamation
     */
    public int getCurrentAttack() {
        return currentAttack;
    }
    
    /**
     * Retrieves the currentDefense of the Amalgamation.
     * 
     * The currentDefense is the stat that should be used in battle, as it is the
     * one that is modified.
     * 
     * @return the currentDefense of the Amalgamation
     */
    public int getCurrentDefense() {
        return currentDefense;
    }
    
    /**
     * Retrieves the currentHealth of the Amalgamation.
     * 
     * The currentHealth is the stat that should be used in battle, as it is the
     * one that is modified.
     * 
     * @return the currentHealth of the Amalgamation
     */
    public int getCurrentHealth() {
        return currentHealth;
    }
    
    /**
     * Retrieves the currentHealth of the Amalgamation.
     * 
     * The currentHealth is the stat that should be used in battle, as it is the
     * one that is modified.
     * 
     * @return the currentHealth of the Amalgamation
     */
    public int getCurrentSpeed() {
        return currentSpeed;
    }
    
    /**
     * Returns the amount of experience earned from defeating this Amalgamation.
     * 
     * @return the amount of experience earned from defeating this Amalgamation
     */
    public int getDefeatedExperience() {
        int baseStatsTotal = body.totalBaseHealth() + body.totalBaseAttack() 
                + body.totalBaseDefense() + body.totalBaseSpeed();
        return 10 * (int)Math.pow(
                Math.log((baseStatsTotal * (level + 5)) / 100), 3);
    }
    
    /**
     * Retrieves the defense stat of the Amalgamation.
     * 
     * @return the defense stat of the Amalgamation
     */
    public int getDefense() {
        return defense;
    }
    
    /**
     * Retrieves the health stat of the Amalgamation.
     * 
     * @return the health stat of the Amalgamation
     */
    public int getHealth() {
        return health;
    }
    
    /**
     * Retrieves the name of the Amalgamation.
     * 
     * @return the name of the Amalgamation
     */
    public String getName(){
        return name;
    }
    
    /**
     * Retrieves the graphical representation of the Amalgamation.
     * 
     * The first time this method is called, the image will be constructed by
     * the Body of the Amalgamation. After the initial call, all subsequent
     * calls will be able to return the image immediately.
     * 
     * @return the graphical representation of the Amalgamation
     */
    public BufferedImage getFullImage() {
        if (fullImage == null)
            fullImage = body.fullImage();
        
        return fullImage;
    }
    
    /**
     * Retrieves the graphical representation of the Amalgamation.
     * 
     * Unlike getFullImage, this method will update and return the
     * full image every time it is called. This means that the image will be
     * redrawn every time this method is called. Use this method when you are
     * certain that the Amalgamation's image needs to be updated (such as 
     * when a Slot on the Amalgamation's Body has had a value changed).
     * 
     * @return the graphical representation of the Amalgamation
     */
    public BufferedImage getFullUpdatedImage() {
        fullImage = body.fullImage();
        return fullImage;
    }
    
    /**
     * Returns the level that the Amalgamation is at.
     * 
     * @return the level that the Amalgamation is at
     */
    public int getLevel() {
        return level;
    }
    
    /**
     * Retrieves the Amalgamation's luck variance.
     * 
     * @return the Amalgamation's luck variance
     */
    public double getLuckVariance() {
        return luckVariance;
    }
    
    /**
     * Retrieves the speed stat of the Amalgamation.
     * 
     * @return the speed stat of the Amalgamation
     */
    public int getSpeed() {
        return speed;
    }
    
    /**
     * Retrieves the experience needed for the Amalgamation to level up.
     * 
     * @return the experience needed for the Amalgamation to level up
     */
    public int getTargetExperience() {
        return targetExperience;
    }
    
    /**
     * Increases the current Amalgamation's level
     */
    public void levelUp() {
        level++;
        
        acomponent.ADialog.createMessageDialog(
                null, 
                name + " grew to level " + level + "!", 
                "Sweet!"
        ).showDialog();
        
        // Recalculate the stats since the level has changed.
        calculateStats();
        
        // Iterate through each of the new Abilites the Amalgamation can learn.
        for (Ability a : newAbilities()) {
            // Try to add the Ability to the array.
            if (addAbility(a))
                acomponent.ADialog.createMessageDialog(null, String.format(
                        "%s learned the ability %s!", name, a.getName()),
                        "Huzzah!").showDialog();
            else
                // Show a dialog to learn the ability.
                AbilityReplaceDialog.showAbilityReplaceDialog(null, this, a);
        }
    }
    
    /**
     * Retrieves all of the new Abilities that the Amalgamation can only learn
     * at its current level.
     * 
     * @return all of the new Abilities that the Amalgamation can only learn
     * at its current level
     */
    public Ability[] newAbilities() {
        return java.util.Arrays.stream(body.allAbilities())
                // Filter out the Abilities that cannot be learned yet.
                .filter(a -> level == a.getLevel())
                .toArray(Ability[]::new);
    }
    
    /**
     * Replaces the Amalgamation's Ability at the given index with the given 
     * Ability.
     * 
     * @param ability the Ability to replace the old Ability with
     * @param index the index of the old Abiltiy
     * @throws ArrayIndexOutOfBoundsException if the index is not in the valid
     *                                        range (0 - 3)
     */
    public void replaceAbility(Ability ability, int index) 
            throws ArrayIndexOutOfBoundsException {
        abilities[index] = ability;
    }
    
    /**
     * Resets the Amalgamation's current stats to their default values.
     */
    public void resetCurrentStats() {
        currentHealth = health;
        currentAttack = attack;
        currentDefense = defense;
        currentSpeed = speed;
        // Reset Ability cooldowns.
        for (Ability a : abilities)
            if (a != null)
                a.resetCurrentCooldown();
    }
    
    /**
     * Sets the currentAttack of the Amalgamation.
     * 
     * @param currentAttack the new value of set the currentAttack to
     */
    public void setCurrentAttack(int currentAttack){
        // Ensure the current attack does not go above the normal health or
        // below 5.
        if (currentAttack < 5)
            this.currentAttack = 5;
        else
            this.currentAttack = currentAttack;
    }
    
    /**
     * Sets the currentDefense of the Amalgamation.
     * 
     * @param currentDefense the new value of set the currentAttack to
     */
    public void setCurrentDefense(int currentDefense){
        // Ensure the current defense does not go above the normal health or
        // below 5.
        if (currentDefense < 5)
            this.currentDefense = 5;
        else
            this.currentDefense = currentDefense;
    }
    
    /**
     * Sets the currentHealth of the Amalgamation.
     * 
     * @param currentHealth the new value of set the currentAttack to
     */
    public void setCurrentHealth(int currentHealth){
        // Ensure the current health does not go above the normal health or
        // below 0.
        if (currentHealth > health)
            this.currentHealth = health;
        else if (currentHealth < 0)
            this.currentHealth = 0;
        else
            this.currentHealth = currentHealth;
    }
    
    /**
     * Sets the currentSpeed of the Amalgamation.
     * 
     * @param currentSpeed the new value of set the currentAttack to
     */
    public void setCurrentSpeed(int currentSpeed){
        // Ensure the current speed does not go above the normal health or
        // below 5.
        if (currentSpeed < 5)
            this.currentSpeed = 5;
        else
            this.currentSpeed = currentSpeed;
    }
}
package campaign;

import amalgamation.Amalgamation;
import amalgamation.battle.AIController;
import amalgamation.parts.Arm;
import amalgamation.parts.Body;
import amalgamation.parts.Head;
import amalgamation.parts.Leg;

import java.io.Serializable;

import menus.components.BattleDialog;

/**
 * A CampaignLevel is a list of opponents that can be battled as a way of
 * providing a single player with an entertaining experience.
 * 
 * A CampaignLevel defines the following:
 * 
 * A number of low level "minion" Amalgamations that the user fights first.
 * 
 * A number of mid-level "guard" Amalgamations that the user fights after
 * defeating the minions.
 * 
 * A high level "boss" Amalgamation that the user fights once all other
 * Amalgamations are defeated.
 * 
 * All minions will have the same level, and all guards will have the same level.
 * Both Amalgamations of this type will be randomly generated using a specific
 * list of parts.
 * 
 * The boss Amalgamation will be statically defined and never change.
 * 
 * @author Caleb Rush
 */
public class CampaignLevel implements Serializable {
    // The name of the level.
    private String              name;
    // The minion Amalgamations.
    private Amalgamation[]      minions;
    // The guard Amalgamations.
    private Amalgamation[]      guards;
    // The boss Amalgamation.
    private Amalgamation        boss;
    // Whether or not each Amalgamation was defeated.
    private boolean[]           minionsDefeated;
    private boolean[]           guardsDefeated;
    private boolean             bossDefeated;
    // The levels of the minions and guards.
    private int                 minionsLevel;
    private int                 guardsLevel;
    // The parts used to generate random Amalgamations.
    private Arm[]               arms;
    private Body[]              bodies;
    private Head[]              heads;
    private Leg[]               legs;
    
    /**
     * Creates a new CampaignLevel with the specified characteristics.
     * 
     * @param name the name of the level, to be displayed when the level is 
     *             being played
     * @param numMinions the number of minions in the level
     * @param numGuards the number of guards in the level
     * @param minionsLevel the level the minions will be at when fought
     * @param guardsLevel the level the guards will be at when fought
     * @param boss the Boss Amalgamation of the level
     * @param arms the list of Arms that can be used when generating 
     *             Amalgamations.
     * @param bodies the list of Bodies that can be used when generating 
     *             Amalgamations.
     * @param heads the list of Heads that can be used when generating 
     *             Amalgamations.
     * @param legs the list of Legs that can be used when generating 
     *             Amalgamations.
     * @throws IllegalArgumentException if the number of guards or minions is
     *                                  less than 1, the boss is null, or any
     *                                  of the arrays of parts is null or empty.
     */
    public CampaignLevel(String name, int numMinions, int numGuards, 
            int minionsLevel, int guardsLevel, Amalgamation boss, 
            Arm[] arms, Body[] bodies, Head[] heads, Leg[] legs)
                throws IllegalArgumentException {
        // Check if the arguments were valid.
        if (numMinions < 1)
            throw new IllegalArgumentException(
                    "The number of minions must be at least 1");
        if (numGuards < 1)
            throw new IllegalArgumentException(
                    "The number of guards must be at least 1");
        if (boss == null)
            throw new IllegalArgumentException(
                    "The boss Amalgamation cannot be null");
        if (arms == null)
            throw new IllegalArgumentException(
                    "The array of Arms cannot be null");
        if (arms.length == 0)
            throw new IllegalArgumentException(
                    "The array of Arms cannot be empty");
        if (bodies == null)
            throw new IllegalArgumentException(
                    "The array of bodies cannot be null");
        if (bodies.length == 0)
            throw new IllegalArgumentException(
                    "The array of bodies cannot be empty");
        if (heads == null)
            throw new IllegalArgumentException(
                    "The array of heads cannot be null");
        if (heads.length == 0)
            throw new IllegalArgumentException(
                    "The array of heads cannot be empty");
        if (legs == null)
            throw new IllegalArgumentException(
                    "The array of legs cannot be null");
        if (legs.length == 0)
            throw new IllegalArgumentException(
                    "The array of legs cannot be empty");
        
        // Set the values.
        this.name = name;
        minions = new Amalgamation[numMinions];
        guards = new Amalgamation[numGuards];
        minionsDefeated = new boolean[numMinions];
        guardsDefeated = new boolean[numGuards];
        this.minionsLevel = minionsLevel;
        this.guardsLevel = guardsLevel;
        this.boss = boss;
        this.arms = arms;
        this.bodies = bodies;
        this.heads = heads;
        this.legs = legs;
    }
    
    /**
     * Battles the guards at the specified index.
     * 
     * @param index the index of the minion in the range 0 - numGuards - 1
     * @param playerAmalgamation the Amalgamation the player will control.
     */
    public void battleGuard(int index, Amalgamation playerAmalgamation) {
        // Check if the index is in range.
        if (index < 0 || index >= guards.length)
            return;
        
        // Set up a new Battle with the minion being controlled by an AI and the
        // player controlling their Amalgamation.
        AIController ai = new AIController();
        BattleDialog.startBattle(ai, playerAmalgamation, guards[index]);
    }
    
    /**
     * Battles the minion at the specified index.
     * 
     * @param index the index of the minion in the range 0 - numMinions - 1
     * @param playerAmalgamation the Amalgamation the player will control.
     */
    public void battleMinion(int index, Amalgamation playerAmalgamation) {
        // Check if the index is in range.
        if (index < 0 || index >= minions.length)
            return;
        
        // Set up a new Battle with the minion being controlled by an AI and the
        // player controlling their Amalgamation.
        AIController ai = new AIController();
        BattleDialog.startBattle(ai, playerAmalgamation, minions[index]);
    }
    
    /**
     * Retrieves the boss of the level.
     * 
     * @return the boss of the level
     */
    public Amalgamation getBoss() {
        return boss;
    }
    
    /**
     * Retrieves the guards in the level.
     * 
     * If the guards have not been generated yet, this method will generate
     * them.
     * 
     * @return the guards in the level
     */
    public Amalgamation[] getGuards() {
        // Check if the minions have been generated yet.
        if (guards[0] == null) {
            // Generate the minions.
            for (int i = 0; i < guards.length; i++) {
                try {
                    // Generate the minion using the given lists of parts.
                    guards[i] = util.Amalgamations.randomAmalgamation(arms, 
                            bodies, heads, legs);
                    // Set the level of the minion.
                    guards[i].setLevel(guardsLevel);
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return guards;
    }
    
    /**
     * Retrieves the minions in the level.
     * 
     * If the minions have not been generated yet, this method will generate
     * them.
     * 
     * @return the minions in the level
     */
    public Amalgamation[] getMinions() {
        // Check if the minions have been generated yet.
        if (minions[0] == null) {
            // Generate the minions.
            for (int i = 0; i < minions.length; i++) {
                try {
                    // Generate the minion using the given lists of parts.
                    minions[i] = util.Amalgamations.randomAmalgamation(arms, 
                            bodies, heads, legs);
                    // Set the level of the minion.
                    minions[i].setLevel(minionsLevel);
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return minions;
    }
    
    /**
     * Returns the name of the level.
     * 
     * @return the name of the level
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns whether or not the boss was defeated.
     * 
     * @return true if the boss was defeated, false otherwise.
     */
    public boolean isBossDefeated() {
        return bossDefeated;
    }
    
    /**
     * Returns whether or not the guard at the specified index is defeated.
     * 
     * @param index the index of the guard in the range of 0 - numGuards - 1
     * @return true if the guard was defeated, false if it wasn't or if index
     *         was out of range
     */
    public boolean isGuardDefeated(int index) {
        return index > 0 
                && index < guardsDefeated.length 
                && guardsDefeated[index];
    }
    
    /**
     * Returns whether or not the minion at the specified index is defeated.
     * 
     * @param index the index of the minion in the range of 0 - numMinions - 1
     * @return true if the minion was defeated, false if it wasn't or if index
     *         was out of range
     */
    public boolean isMinionDefeated(int index) {
        return index > 0 
                && index < minionsDefeated.length 
                && minionsDefeated[index];
    }
    
    /**
     * Resets the all of the generated Amalgamations and sets them all as
     * undefeated.
     */
    public void reset() {
        // Set all the defeated enemies as false and remove the generated
        // Amalgamations.
        for (int i = 0; i < minionsDefeated.length; i++) {
            minionsDefeated[i] = false;
            minions[i] = null;
        }
        for (int i = 0; i < guardsDefeated.length; i++) {
            guardsDefeated[i] = false;
            guards[i] = null;
        }
        bossDefeated = false;
    }
    
    /**
     * Sets the Arms that can be used to generate Amalgamations.
     * 
     * @param arms the Arms that can be used to generate Amalgamations
     */
    public void setArms(Arm[] arms) {
        if (arms != null && arms.length != 0)
            this.arms = arms;
    }
    
    /**
     * Sets the Bodies that can be used to generate Amalgamations.
     * 
     * @param bodies the Bodies that can be used to generate Amalgamations
     */
    public void setBodies(Body[] bodies) {
        if (bodies != null && bodies.length != 0)
            this.bodies = bodies;
    }
    
    /**
     * Sets the boss of the level.
     * 
     * @param boss the boss of the level.
     */
    public void setBoss(Amalgamation boss) {
        if (boss != null)
            this.boss = null;
    }
    
    /**
     * Sets the Heads that can be used to generate Amalgamations.
     * 
     * @param heads the Heads that can be used to generate Amalgamations
     */
    public void setHeads(Head[] heads) {
        if (heads != null && heads.length != 0)
            this.heads = heads;
    }
    
    /**
     * Sets the Legs that can be used to generate Amalgamations.
     * 
     * @param legs the Legs that can be used to generate Amalgamations
     */
    public void setLegs(Leg[] legs) {
        if (legs != null && legs.length != 0)
            this.legs = legs;
    }
    
    /**
     * Sets the name of the CampaignLevel.
     * 
     * @param name the name of the CampaignLevel.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Sets the level of all guards in the level.
     * 
     * This will reset the level.
     * 
     * @param guardsLevel the level all the guards should be
     */
    public void setGuardsLevel(int guardsLevel) {
        if (guardsLevel >= 0 && guardsLevel <= 100) {
            this.guardsLevel = guardsLevel;
            reset();
        }
    }
    
    /**
     * Sets the level of all minions in the level.
     * 
     * This will reset the level.
     * 
     * @param minionsLevel the level all the minions should be
     */
    public void setMinionsLevel(int minionsLevel) {
        if (minionsLevel >= 0 && minionsLevel <= 100) {
            this.minionsLevel = minionsLevel;
            reset();
        }
    }
    
    /**
     * Sets the number of guards in the level. This will reset the level.
     * 
     * @param numGuards the number of guards to have in the level
     */
    public void setNumGuards(int numGuards) {
        if (numGuards > 0) {
            guards = new Amalgamation[numGuards];
            guardsDefeated = new boolean[numGuards];
        }
    }
    
    /**
     * Sets the number of minions in the level. This will reset the level.
     * 
     * @param numMinions the number of minions to have in the level
     */
    public void setNumMinions(int numMinions) {
        if (numMinions > 0) {
            minions = new Amalgamation[numMinions];
            minionsDefeated = new boolean[numMinions];
        }
    }
}


package amalgamations.abilities;

import amalgamation.Amalgamation;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Ability that an Amalgamation can use in combat
 * @author Jordan LaRiccia
 */
public class Ability implements Serializable {
    public static final char SPLITTER = '|';
    
    private final String name;
    private final int cooldown;
    private final int accuracy;
    private final int level;
    private final StatModifier[] modifiers;
    
     /**
      * Constructs a new Ability object
      * @param name the name of the ability
      * @param cooldown the cooldown value of the ability
      * @param accuracy the accuracy value of the ability
      * @param level the level when this move is learned
      * @param modifiers the list of stat modifiers for the ability
      */
    public Ability(String name, int cooldown, int accuracy, int level, 
            StatModifier[] modifiers){
        this.name = name;
        this.accuracy = accuracy;
        this.cooldown = cooldown;
        this.level = level;
        this.modifiers = modifiers;
    }
    
    /**
     * Calls statAdjuster for the list of stat modifiers for the move
     * @param player the current user/Amalgamation of the ability
     * @param opponent the other Amalgamation that is opposing the current
     * Amalgamation
     */
    public void affect(Amalgamation player, Amalgamation opponent){
        for(StatModifier m : modifiers) 
        {
            m.statAdjuster(player, opponent);
        }
    }
    
    public void doMove(Amalgamation a){
        
    }
    
    /**
     * Gets the accuracy value  
     * @return accuracy
     */
    public int getAccuracy(){
        return accuracy;
    }
    
    /**
     * Retrieves the cooldown for the Ability.
     * 
     * @return the cooldown for the Ability
     */
    public int getCooldown() {
        return cooldown;
    }
    
    /**
     * Retrieves the array of Modifiers used by this Ability.
     * 
     * @return the array of Modifiers used by this Ability
     */
    public StatModifier[] getModifiers() {
        return modifiers;
    }
    
    /**
     * Retrieves the level at which this Ability can be learned.
     * 
     * @return the level at which this Ability can be learned
     */
    public int getLevel() {
        return level;
    }
    
    /**
     * Gets the name of the ability 
     * @return name
     */
    public String getName(){
        return name;
    }
    
    /**
     * Retrieves the script for the Ability.
     * 
     * @param userName the name of the Amalgamation using the Ability
     * @param targetName the name of the Amalgamation opposing the user
     * @return script the script for the Ability
     */
    public String[] getScript(String userName, String targetName){
        ArrayList<String> script = new ArrayList<>();
        
        // Add the use of the move.
        script.add(String.format("%s used %s!", userName, name));
        
        // Add each modifier's script.
        for (StatModifier m : modifiers)
            script.add(m.getScript(userName, targetName));
        
        // Return the script.
        return script.toArray(new String[0]);
    }
}

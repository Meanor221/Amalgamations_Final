
package amalgamations.abilities;

import amalgamation.Amalgamation;
import java.util.ArrayList;

/**
 * The Ability that an Amalgamation can use in combat
 * @author Jordan LaRiccia
 */
public class Ability {
    private final String name;
    private final int cooldown;
    private final int accuracy;
    private final String script;
    private final ArrayList<StatModifier> modifiers;
    public static final char SPLITTER = '|';
    
     /**
      * Constructs a new Ability objecy
      * @param name the name of the ability
      * @param cooldown the cooldown value of the ability
      * @param accuracy the accuracy value of the ability
      * @param script the flavor text of the ability
      * @param modifiers the list of stat modifiers for the ability
      */
    public Ability(String name, int cooldown, int accuracy, String script,
            StatModifier... modifiers){
        this.name=name;
        this.accuracy=accuracy;
        this.cooldown=cooldown;
        this.script=script;
        this.modifiers = new ArrayList<>(java.util.Arrays.asList(modifiers));
    }
    
    /**
     * Calls statAdjuster for the list of stat modifiers for the move
     * @param player the current user/Amalgamation of the ability
     * @param opponent the other Amalgamation that is opposing the current
     * Amalgamation
     */
    private void affect(Amalgamation player, Amalgamation opponent){
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
     * Gets the name of the ability 
     * @return name
     */
    public String getName(){
        return name;
    }
    
    /**
     * Gets the script for flavor text and stuff 
     * @return script
     */
    public String getScript(){
        return script;
    }
}

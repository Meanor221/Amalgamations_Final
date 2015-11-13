
package amalgamations.abilities;

import amalgamation.Amalgamation;

/**
 *
 * @author jjl5451
 */
public class Ability {
    private final String name;
    private final int cooldown;
    private final int accuracy;
    private final String script;
    public static final char SPLITTER = '|';
    
    private final int healthModifier;
    private final int attackModifier;
    private final int defenseModifier;
    private final int speedModifier;
    
     
    public Ability(String name, int cooldown, int accuracy, String script,
    int healthModifier, int attackModifier, int defenseModifier, 
    int speedModifier){
        this.name=name;
        this.accuracy=accuracy;
        this.cooldown=cooldown;
        this.script=script;
        this.attackModifier=attackModifier;
        this.defenseModifier=defenseModifier;
        this.healthModifier=healthModifier;
        this.speedModifier=speedModifier;
    }
    
    
    private void affect(Amalgamation a){
       
        a.setCurrentAttack(a.getAttack()+ attackModifier);
        a.setCurrentDefense(a.getDefense()+ defenseModifier);
        a.setCurrentHealth(a.getHealth()+ healthModifier);
        a.setCurrentSpeed(a.getSpeed()+ speedModifier);
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
     * Gets the attack modifier value  
     * @return attackModifier
     */
    public int getAttackModifier(){
        return attackModifier;
    }
    
    /**
     * Gets the cooldown value  
     * @return cooldown
     */
    public int getCooldown(){
        return cooldown;
    }
    
    /**
     * Gets the defense modifier value 
     * @return defenseModifier
     */
    public int getDefenseModifier(){
        return defenseModifier;
    }
    
    /**
     * Gets the health modifier value  
     * @return healthModifier
     */
    public int getHealthModifier(){
        return healthModifier;
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
    
    /**
     * Gets the speed modifier value  
     * @return speedModifier
     */
    public int getSpeedModifier(){
        return speedModifier;
    }
   
    
}

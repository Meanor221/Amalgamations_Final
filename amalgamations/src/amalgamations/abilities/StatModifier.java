
package amalgamations.abilities;
import amalgamation.Amalgamation;

/**
 *
 * @author Jordan LaRiccia, Adam Meanor
 */
public class StatModifier {
    public static final int     MODIFIER_USER_HEALTH       = 0;
    public static final int     MODIFIER_USER_ATTACK       = 1;
    public static final int     MODIFIER_USER_DEFENSE      = 2;
    public static final int     MODIFIER_USER_SPEED        = 3;
    public static final int     MODIFIER_OPPONENT_ATTACK   = 4;
    public static final int     MODIFIER_OPPONENT_DEFENSE  = 5;
    public static final int     MODIFIER_OPPONENT_SPEED    = 6;
    
    private final int           adder;
    private final double        multiplier;
    private final int           ability_id;
    
    /**
     * Constructor setting the adder, multiplier, ability_id
     * @param adder the value to be added to the current stat
     * @param multiplier the value to multiply the current value by
     * @param ability_id the numerical identifier for which stat is to be 
     * modified
     */
    public StatModifier(int adder, double multiplier, int ability_id) {
        this.adder      =   adder;
        this.multiplier =   multiplier;
        this.ability_id =   ability_id; 
    }
    
    /**
     * For a StatModifer object it determines which stat is being modified based
     * on the ability id number and then calculates a new current value for that
     * stat
     * @param player the current user/Amalgamation of the ability
     * @param opponent the other Amalgamation that is opposing the current 
     * Amalgamation
     */
    public void statAdjuster(Amalgamation player, Amalgamation opponent) {
        int calculatedModifier;
        
        switch(ability_id)
        {
            case MODIFIER_USER_HEALTH:
                calculatedModifier = (int)((player.getCurrentHealth() 
                        + adder) * multiplier);
                player.setCurrentHealth(calculatedModifier);
                break;
            
            case MODIFIER_USER_ATTACK:
                calculatedModifier = (int)((player.getCurrentAttack() 
                        + adder) * multiplier);
                player.setCurrentAttack(calculatedModifier);
                break;
                
            case MODIFIER_USER_DEFENSE:
                calculatedModifier = (int)((player.getCurrentDefense() 
                        + adder) * multiplier);
                player.setCurrentDefense(calculatedModifier);
                break;
                
            case MODIFIER_USER_SPEED:
                calculatedModifier = (int)((player.getCurrentSpeed() 
                        + adder) * multiplier);
                player.setCurrentSpeed(calculatedModifier);
                break;
                
            case MODIFIER_OPPONENT_ATTACK:
                calculatedModifier = (int)((opponent.getCurrentAttack() 
                        + adder) * multiplier);
                opponent.setCurrentAttack(calculatedModifier);
                break;
                
            case MODIFIER_OPPONENT_DEFENSE:
                calculatedModifier = (int)((opponent.getCurrentDefense() 
                        + adder) * multiplier);
                opponent.setCurrentDefense(calculatedModifier);
                break;
                
            case MODIFIER_OPPONENT_SPEED:
                calculatedModifier = (int)((opponent.getCurrentSpeed() 
                        + adder) * multiplier);
                opponent.setCurrentSpeed(calculatedModifier);
                break;
                
            default:
                throw new IllegalArgumentException(
                        "Invalid Ability ID: " + ability_id);
        }
        
    }
    
    
    
    
}

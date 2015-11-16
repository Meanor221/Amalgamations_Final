package amalgamation.abilities;

import amalgamation.Amalgamation;

import java.util.Random;

/**
 * A damage dealing Ability 
 * @author Jordan LaRiccia
 */
public class Attack extends Ability {
    private final int damage;
    private static final double VARIANCE_RANGE = 0.3;

    /**
     * Constructs a new Attack object
     * @param name the name of the Attack
     * @param cooldown the cooldown value for the Attack
     * @param accuracy the accuracy value for the Attack
     * @param level the level when this move is learned
     * @param damage the damage that the Attack does to the opponent
     * @param modifiers the list of stat modifiers for the ability
     */
    public Attack(String name, int cooldown, int accuracy, int level, 
            int damage, StatModifier[] modifiers) {
        super(name, cooldown, accuracy, level, modifiers);
        this.damage = damage;
    }
    
    // Calculates the damage done to the opponent.
    private int calculateDamage(Amalgamation user, Amalgamation target) {
        // Calculate damage variance.
        Random random = new Random();
        double damageVariance = random.nextDouble() % VARIANCE_RANGE + 0.85;
        
        int dmg=(int) ((damageVariance * damage)-target.getCurrentDefense()
                +user.getCurrentAttack());
        return dmg;
    }
    
    
    @Override
    public void doMove(Amalgamation player, Amalgamation opponent){
        affect(player,opponent);
        
        opponent.doDamage(calculateDamage(player,opponent));
       
    }
    
    /**
     * Retrieves the base damage done by this Attack.
     * 
     * @return the base damage done by this Attack
     */
    public int getDamage() {
        return damage;
    }
}

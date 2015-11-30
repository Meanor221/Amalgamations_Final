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
     * @param description a basic description of the Ability to be displayed
      *                     to the user. This can use endline characters.
     */
    public Attack(String name, int cooldown, int accuracy, int level, 
            int damage, StatModifier[] modifiers, String description) {
        super(name, cooldown, accuracy, level, modifiers, description);
        this.damage = damage;
    }
    
    public String attack(Amalgamation player, Amalgamation opponent) {        
        // Do damage to the opponent.
        int damage = calculateDamage(player, opponent);
        opponent.doDamage(damage);
        return String.format("%s took %d damage!%c1%c%d%c", 
                opponent.getName(), damage, StatModifier.HEALTH_CHANGE_DELIM,
                StatModifier.HEALTH_CHANGE_DELIM, -damage, 
                StatModifier.HEALTH_CHANGE_DELIM);
    }
    
    // Calculates the damage done to the opponent.
    private int calculateDamage(Amalgamation user, Amalgamation target) {
        // Calculate damage variance.
        Random random = new Random();
        double damageVariance = random.nextDouble() % VARIANCE_RANGE + 0.85;
        
        int dmg = (int)(damageVariance * damage * 
                user.getCurrentAttack() / target.getCurrentDefense());
        return dmg;
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

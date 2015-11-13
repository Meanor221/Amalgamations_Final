package amalgamations.abilities;
import java.util.Random;

/**
 * A damage dealing Ability 
 * @author Jordan LaRiccia
 */
public class Attack extends Ability {
    private final int damage;
    private static final double VARIANCE_RANGE = 0.3;
    private final double damageVariance;

    /**
     * Constructs a new Attack object
     * @param name the name of the Attack
     * @param cooldown the cooldown value for the Attack
     * @param accuracy the accuracy value for the Attack
     * @param script the flavor text for the Attack
     * @param damage the damage that the Attack does to the opponent
     */
    public Attack(String name, int cooldown, int accuracy, String script,int damage) {
        super(name, cooldown, accuracy, script);
        this.damage=damage;
        
        Random random   =   new Random();
        damageVariance  =   random.nextDouble() % VARIANCE_RANGE + 0.85;
    }
    
}

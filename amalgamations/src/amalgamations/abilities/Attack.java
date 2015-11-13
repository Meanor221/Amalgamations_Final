/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amalgamations.abilities;
import java.util.Random;

/**
 *
 * @author jjl5451
 */
public class Attack extends Ability {
    private final int damage;
    private static final double VARIANCE_RANGE = 0.3;
    private final double damageVariance;


    public Attack(String name, int cooldown, int accuracy, String script,int damage) {
        super(name, cooldown, accuracy, script, 0, 0, 0, 0);
        this.damage=damage;
        
        Random random   =   new Random();
        damageVariance  =   random.nextDouble() % VARIANCE_RANGE + 0.85;
    }
    
}

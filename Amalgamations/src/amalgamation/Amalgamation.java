package amalgamation;
import amalgamation.parts.Body;
import amalgamation.parts.arms.RedStick;
import amalgamation.parts.bodies.Board;
import amalgamation.parts.heads.GreenCircle;
import amalgamation.parts.legs.BlueL;
import java.util.Random;
import java.awt.image.BufferedImage;

/**
 *
 * @author Adam Meanor
 */
public class Amalgamation {    
    private static final double VARIANCE_RANGE = 0.3;
    private static final int    MAX_LEVEL = 100;
    
    private Body            body;
    private String          name;
    private BufferedImage   fullImage;
    private int             level;
    private int             experience;
    private final double    healthVariance;
    private final double    attackVariance;
    private final double    defenseVariance;
    private final double    speedVariance;
    private final double    luckVariance;
    private int             health;
    private int             attack;
    private int             defense;
    private int             speed;
    private int             currentHealth;
    private int             currentAttack;
    private int             currentDefense;
    private int             currentSpeed;
    
    // Constructor
    public Amalgamation(String name, Body body) {
        this.name = name;
        this.body = body;
        
        Random random   =   new Random();
        healthVariance  =   random.nextDouble() % VARIANCE_RANGE + 0.85;
        speedVariance   =   random.nextDouble() % VARIANCE_RANGE + 0.85;
        attackVariance  =   random.nextDouble() % VARIANCE_RANGE + 0.85;
        defenseVariance =   random.nextDouble() % VARIANCE_RANGE + 0.85;
        luckVariance    =   random.nextDouble() % VARIANCE_RANGE + 0.85;
        
        fullImage       =   body.fullImage();
        level           =   100;
        calculateStats();
    }
    
    // Calculates the stats of the Amalgamation based on current level
    private void calculateStats() {
        System.out.println(healthVariance);
        health  = (int)(2 * ((double)level/(double)MAX_LEVEL) 
                * body.totalBaseHealth() * healthVariance) + 5;
        
        attack  = (int)(2 * ((double)level/(double)MAX_LEVEL) 
                * body.totalBaseAttack() * attackVariance) + 5;
        
        defense = (int)(2 * ((double)level/(double)MAX_LEVEL) 
                * body.totalBaseDefense() * defenseVariance) + 5;
        
        speed   = (int)(2 * ((double)level/(double)MAX_LEVEL) 
                * body.totalBaseSpeed() * speedVariance) + 5;
    }
    
    // Decreases the current Amalgamation's currentHealth most likely due to an
    // opponents attack
    public void doDamage(int damage) { 
        
    }
    
    // Increases the current Amalgamation's experience total
    public void gainExp(double exp) {
        
    }
    
    // Gets attack stat
    public int getAttack() {
        return attack;
    }
    
    // Gets defense stat
    public int getDefense() {
        return defense;
    }
    
    // Gets health stat
    public int getHealth() {
        return health;
    }
    
    // Gets the full image of the Amalgamation
    public BufferedImage getFullImage() {
        return fullImage;
    }
    
    //Gets speed stat
    public int getSpeed() {
        return speed;
    }
    
    // Increases the current Amalgamation's level
    public void levelUp() {
        
    }
    
    public static void main(String [] ars) {
        Body body;
        // Create the body.
        try {
            body = new Board();
            // Add parts to the slots.
            body.getHeadSlots()[0].setPart(new GreenCircle());
            body.getArmSlots()[0].setPart(new RedStick());
            body.getArmSlots()[1].setPart(new RedStick());
            body.getLegSlots()[0].setPart(new BlueL());
            body.getLegSlots()[1].setPart(new BlueL());
            
            Amalgamation test = new Amalgamation("Tested", body);
            System.out.printf("Health: %d", test.getHealth());
            System.out.printf("Attack: %d", test.getAttack());
            System.out.printf("Defense: %d", test.getDefense());
            System.out.printf("Speed: %d", test.getSpeed());
            
            
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
package amalgamation;
import amalgamation.parts.Body;
import java.util.Random;
import java.awt.image.BufferedImage;

/**
 * An Amalgamation is a character that contains a body, name, and calculated
 * stats. 
 * 
 * @author Adam Meanor, Caleb Rush
 */
public class Amalgamation {    
    // The range in which the variance can be calculated.
    private static final double VARIANCE_RANGE = 0.3;
    // The highest level that can be reached (also used for calculations).
    public static final int    MAX_LEVEL = 100;
    
    // The Body containing all the Parts that make up the Amalgamation.
    private final Body      body;
    // The name of the Amalgamation.
    private final String    name;
    // The graphical representation of the Amalgamation.
    private BufferedImage   fullImage;
    // The level of the Amalgamation.
    private int             level;
    // The amount of experience the Amalgamation has,
    private int             experience;
    // The randomized variance of all the stats.
    private final double    healthVariance;
    private final double    attackVariance;
    private final double    defenseVariance;
    private final double    speedVariance;
    private final double    luckVariance;
    // The calculated stats.
    private int             health;
    private int             attack;
    private int             defense;
    private int             speed;
    // The current stats (used for battle when stats get modified).
    private int             currentHealth;
    private int             currentAttack;
    private int             currentDefense;
    private int             currentSpeed;
    
    /**
     * Constructs a new Amalgamation with the given name and Body.
     * 
     * Once the Amalgamation's name and Body are set, they cannot be changed.
     * 
     * @param name the name of the Amalgamation.
     * @param body the Body of the Amlagamation
     */
    public Amalgamation(String name, Body body) {
        this.name = name;
        this.body = body;
        
        Random random   =   new Random();
        healthVariance  =   random.nextDouble() % VARIANCE_RANGE + 0.85;
        speedVariance   =   random.nextDouble() % VARIANCE_RANGE + 0.85;
        attackVariance  =   random.nextDouble() % VARIANCE_RANGE + 0.85;
        defenseVariance =   random.nextDouble() % VARIANCE_RANGE + 0.85;
        luckVariance    =   random.nextDouble() % VARIANCE_RANGE + 0.85;
        
        level           =   100;
        calculateStats();
    }
    
    /**
     * Calculates the stats of the Amalgamation based on current level
     */
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

    /**
     * Decreases the current Amalgamation's currentHealth most likely due to an
     * opponents attack.
     * 
     * @param damage the amount to reduce the Amalgamation's currentHealth by.
     */
    public void doDamage(int damage) { 
        currentHealth = currentHealth - damage;
        
    }

    
    /**
     * Increases the current Amalgamation's experience total
     * 
     * @param exp the amount of experience to add to the Amalgamation's total
     *            experience
     */
    public void gainExp(double exp) {
        
    }
    
    /**
     * Retrieves the attack stat of the Amalgamation.
     * 
     * @return the attack stat of the Amalgamation
     */
    public int getAttack() {
        return attack;
    }
    
    /**
     * Retrieves the currentAttack of the Amalgamation.
     * 
     * The currentAttack is the stat that should be used in battle, as it is the
     * one that is modified.
     * 
     * @return the currentAttack of the Amalgamation
     */
    public int getCurrentAttack() {
        return currentAttack;
    }
    
    /**
     * Retrieves the currentDefense of the Amalgamation.
     * 
     * The currentDefense is the stat that should be used in battle, as it is the
     * one that is modified.
     * 
     * @return the currentDefense of the Amalgamation
     */
    public int getCurrentDefense() {
        return currentDefense;
    }
    
    /**
     * Retrieves the currentHealth of the Amalgamation.
     * 
     * The currentHealth is the stat that should be used in battle, as it is the
     * one that is modified.
     * 
     * @return the currentHealth of the Amalgamation
     */
    public int getCurrentHealth() {
        return currentHealth;
    }
    
    /**
     * Retrieves the currentHealth of the Amalgamation.
     * 
     * The currentHealth is the stat that should be used in battle, as it is the
     * one that is modified.
     * 
     * @return the currentHealth of the Amalgamation
     */
    public int getCurrentSpeed() {
        return currentSpeed;
    }
    
    /**
     * Retrieves the defense stat of the Amalgamation.
     * 
     * @return the defense stat of the Amalgamation
     */
    public int getDefense() {
        return defense;
    }
    
    /**
     * Retrieves the health stat of the Amalgamation.
     * 
     * @return the health stat of the Amalgamation
     */
    public int getHealth() {
        return health;
    }
    
    /**
     * Retrieves the name of the Amalgamation.
     * 
     * @return the name of the Amalgamation
     */
    public String getName(){
        return name;
    }
    
    /**
     * Retrieves the graphical representation of the Amalgamation.
     * 
     * The first time this method is called, the image will be constructed by
     * the Body of the Amalgamation. After the initial call, all subsequent
     * calls will be able to return the image immediately.
     * 
     * @return the graphical representation of the Amalgamation
     */
    public BufferedImage getFullImage() {
        if (fullImage == null)
            fullImage = body.fullImage();
        
        return fullImage;
    }
    
    /**
     * Retrieves the speed stat of the Amalgamation.
     * 
     * @return the speed stat of the Amalgamation
     */
    public int getSpeed() {
        return speed;
    }
    
    /**
     * Increases the current Amalgamation's level
     */
    public void levelUp() {
        
    }
    
    /**
     * Sets the currentAttack of the Amalgamation.
     * 
     * @param currentAttack the new value of set the currentAttack to
     */
    public void setCurrentAttack(int currentAttack){
        this.currentAttack = currentAttack;
        
    }
    
    /**
     * Sets the currentDefense of the Amalgamation.
     * 
     * @param currentDefense the new value of set the currentAttack to
     */
    public void setCurrentDefense(int currentDefense){
        this.currentDefense = currentDefense;
        
    }
    
    /**
     * Sets the currentHealth of the Amalgamation.
     * 
     * @param currentHealth the new value of set the currentAttack to
     */
    public void setCurrentHealth(int currentHealth){
        this.currentHealth = currentHealth;
        
    }
    
    /**
     * Sets the currentSpeed of the Amalgamation.
     * 
     * @param currentSpeed the new value of set the currentAttack to
     */
    public void setCurrentSpeed(int currentSpeed){
        this.currentSpeed = currentSpeed;
        
    }
}
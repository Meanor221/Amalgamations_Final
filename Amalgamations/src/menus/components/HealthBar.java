package menus.components;

import java.awt.Color;

/**
 * A HealthBar visually displays a current health value relative to its
 * maximum health value. It can animate changes in the health.
 * 
 * @author Caleb Rush
 */
public class HealthBar extends acomponent.AComponent {
    // The maximum health value.
    private int maxHealth;
    // The current health value.
    private int currentHealth;
    // The length of the bar.
    private int barLength;
    // The color of the bar.
    private Color barColor;
    // Whether or not the actual numbers of the health should be displayed.
    private boolean displayNumbers = true;
    
    /**
     * Constructs a HealthBar with a default maxHealth of 100 and a default
     * currentHealth of 75.
     */
    public HealthBar() {
        this(100, 75, new Color(76, 175, 80));
    }
    
    /**
     * Constructs a new HealthBar with the specified max health, current health,
     * and bar color.
     * 
     * @param maxHealth the maximum health the bar can visualize
     * @param currentHealth the actual health
     * @param barColor the color of the bar representing the currentHealth
     */
    public HealthBar(int maxHealth, int currentHealth, Color barColor) {
        this.maxHealth = maxHealth;
        this.barColor = barColor;
        setCurrentHealth(currentHealth);
        
        // Set the default size.
        java.awt.Dimension size = new java.awt.Dimension(300, 20);
        setSize(size);
        setPreferredSize(size);
        
        // Create a border.
        setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 2, 2, 
                Color.LIGHT_GRAY));
        
        // Set the font.
        setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 
                14));
    }
    
    /**
     * Animates to the new currentHealth value.
     * 
     * @param health the new currentHealth value
     * @return this HealthBar instance to chain animation method calls together
     */
    public HealthBar animateHealth(int health) {
        animations.add(
                acomponent.Animator.animateValue(
                        barLength(currentHealth), 
                        barLength(health), 
                        2000,
                        newBarLength -> {
                            setBarLength((int)newBarLength);
                            repaint();
                        }, 
                        null
        ));
        animations.add(
                acomponent.Animator.animateValue(
                        currentHealth, 
                        health, 
                        2000, 
                        newHealth -> currentHealth = (int)newHealth, 
                        null
                )
        );
        
        return this;
    }
    
    /**
     * Returns an adjusted color that is modifies th barColor based on the value
     * of the currentHealth.
     * 
     * @return the adjusted color or null if the maxHealth is zero
     */
    public Color adjustBarColor() {
        if (maxHealth == 0)
            return null;
        
        double scale = (double)currentHealth / (double)maxHealth;
        // Calculate the red value.
        int r = (int)(barColor.getRed() + ((255 - barColor.getRed()) 
                * (1.0 - scale)));
        if (r > 255)
            r = 255;
        else if (r < 0)
            r = 0;
        
        // Calculate the green value.
        int g = (int)(barColor.getGreen() * scale);
        if (g > 255)
            g = 255;
        else if (g < 0)
            g = 0;
        
        // Calculate the blue value.
        int b = (int)(barColor.getBlue() * scale);
        if (b > 255)
            b = 255;
        else if (b < 0)
            b = 0;
        
        return new Color(r, g, b);
    }
    
    // Calculates the bar length at the for the given current health.
    private int barLength(int currentHealth) {
        return maxHealth == 0?
                    0:
                    (int)((double)currentHealth * (double)getWidth() 
                            / (double)maxHealth);
    }
    
    /**
     * Returns the color of the bar representing the current health.
     * 
     * @return the color of the representing the current health.
     */
    public Color getBarColor() {
        return barColor;
    }
    
    /**
     * Returns the actual health.
     * 
     * @return the actual health.
     */
    public int getCurrentHealth() {
        return currentHealth;
    }
    
    /**
     * Returns the maximum health the bar can represent.
     * 
     * @return the maximum health the bar can represent.
     */
    public int getMaxHealth() {
        return maxHealth;
    }
    
    /**
     * Returns whether or not the numbers are displayed on the health bar.
     * 
     * @return true if the numbers are displayed, false if not
     */
    public boolean isDisplayNumbers() {
        return displayNumbers;
    }
    
    /**
     * Sets the color of the bar representing the current health.
     * 
     * @param barColor the color of the bar representing the current health.
     */
    public void setBarColor(Color barColor) {
        this.barColor = barColor;
    }
    
    // Sets the barLength and calculates the currentHealth.
    private void setBarLength(int barLength) {
        this.barLength = barLength;
    }
    
    /**
     * Sets the actual health.
     * 
     * The given value is first checked to see if its value is in the range of 
     * 0 - maxHealth.
     * 
     * @param currentHealth the actual health
     */
    public void setCurrentHealth(int currentHealth) {
        if (currentHealth <= maxHealth && currentHealth >= 0) {
            this.currentHealth = currentHealth;
            barLength = barLength(currentHealth);
        }
        System.out.println(barLength);
    }
    
    /**
     * Sets whether or not the current health and max health should be displayed
     * on the health bar.
     * 
     * @param displayNumbers true if the numbers should be displayed, false if
     *                       not
     */
    public void setDisplayNumbers(boolean displayNumbers) {
        this.displayNumbers = displayNumbers;
    }
    
    /**
     * Sets the maximum health the bar can represent.
     * 
     * If the currentHealth is less than the given maxHealth, the currentHealth
     * will be lowered to the value of the maxHealth.
     * 
     * @param maxHealth the maximum health the bar can represent.
     */
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
        if (currentHealth > maxHealth)
            setCurrentHealth(maxHealth);
    }
    
    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        
        // Ensure the max health is not zero.
        if (maxHealth != 0) {
            // Check if the barLength needs calculated.
            if (currentHealth != 0 && barLength == 0)
                barLength = barLength(currentHealth);
            // Draw the health bar.
            g.setColor(adjustBarColor());
            g.fillRect(0, 0, barLength, getHeight());
        }
        
        // Display the numbers.
        if (displayNumbers) {
            g.setColor(Color.BLACK);
            g.setFont(getFont());
            // Determine the size of the String.
            String text = String.format("%d / %d", currentHealth, maxHealth);
            java.awt.geom.Rectangle2D textBounds 
                    = g.getFontMetrics().getStringBounds(text, g);
            g.drawString(text, 
                    getWidth() / 2 - (int)textBounds.getCenterX(), 
                    getHeight() / 2 - (int)textBounds.getCenterY());
        }
    }
}

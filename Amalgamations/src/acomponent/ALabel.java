package acomponent;

import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics;

import javax.swing.SwingConstants;

/**
 * An ALabel is an AComponent that can display text.
 * 
 * @author Caleb Rush
 */
public class ALabel extends AComponent {
    // The text to be displayed by the label.
    private String text;
    // The alignment of the text.
    private int horizontalAlignment = SwingConstants.LEFT;
    
    /**
     * Constructs an ALabel that displays the text "Label".
     */
    public ALabel() {
        this("Label");
    }
    
    /**
     * Constructs an ALabel that displays the given text.
     * 
     * @param text the text to be displayed by the ALabel.
     */
    public ALabel(String text) {
        this.text = text;
        
        // Set a default font.
        setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 18));
    }
    
    // Calculate and set the size based on the current font and text.
    private void calculateSize() {
        // Calculate the bounds of the text.
        Rectangle2D bounds = 
                getFontMetrics(getFont()).getStringBounds(text, getGraphics());
        // Set the size based on the text.
        setSize((int)bounds.getWidth() + 20, (int)bounds.getHeight() + 10);
    }
    
    /**
     * Returns the horizontal alignment of the text.
     * 
     * @return the horizontal alignment of the text.
     */
    public int getHorizontalAlignment() {
        return horizontalAlignment;
    }
    
    /**
     * Returns the text displayed by the label.
     * 
     * @return the text displayed by the label.
     */
    public String getText() {
        return text;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Set the font and color for the graphics.
        g.setFont(getFont());
        g.setColor(getForeground());
        // Get the bounds of the text.
        Rectangle2D bounds = g.getFontMetrics().getStringBounds(text, g);
        // Determine the location to draw the text based on the alignment.
        int textX;
        int textY = getHeight() / 2 - (int)bounds.getCenterY();
        if (horizontalAlignment == SwingConstants.LEFT)
            textX = 0;
        else if (horizontalAlignment == SwingConstants.RIGHT)
            textX = getWidth() - (int)bounds.getWidth();
        else
            textX = getWidth() / 2 - (int)bounds.getCenterX();
        // Draw the text on the component.
        g.drawString(text, textX, textY);
    }
    
    @Override
    public void setFont(Font font) {
        super.setFont(font);
        if (getGraphics() != null)
            calculateSize();
    }
    
    /**
     * Sets the horizontal alignment of the text displayed on the label.
     * 
     * @param horizontalAlignment the horizontal alignment of the label.
     */
    public void setHorizontalAlignment(int horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
    }
    
    /**
     * Sets the text to be displayed by the label.
     * 
     * @param text the text to be displayed by the label.
     */
    public void setText(String text) {
        this.text = text;
        // Calculate the size.
        calculateSize();
    }
}

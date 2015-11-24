package acomponent;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics;

import javax.swing.BorderFactory;

/**
 * An AButton is an AComponent that can perform an action when clicked.
 * 
 * An AButton performs similarly to a JButton, and has some of the same methods.
 * 
 * @author Caleb Rush
 */
public class AButton extends AComponent {
    // The text to display on the button.
    private String text;
    // The ActionLister to be called when the button is clicked.
    private ActionListener actionListener;
    // Text color.
    private Color textColor;
    
    /**
     * Constructs a default AButton object.
     */
    public AButton() {
        text = "Button";
        setBackground(Color.BLUE);
        setHighlightColor(Color.WHITE);
        setForeground(Color.WHITE);
        setSize(100, 40);
        setBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 2, Color.LIGHT_GRAY));
        initMouseListener();
    }
    
    /**
     * Constructs an AButton object with the given size.
     * 
     * @param width the width of the AButton
     * @param height the height of the AButton
     */
    public AButton(int width, int height) {
        this();
        setSize(width, height);
    }
    
    @Override
    public void dehighlight(int x, int y, int radius) {
        super.dehighlight(x, y, radius);
        
        // Set the text color back to normal.
        setForeground(textColor);
    }
    
    public void doClick() {
        // Simulate a mouse click in the middle of the button.
        if (getMouseListeners().length != 0) {
            MouseEvent e = new MouseEvent(this, 
                    MouseEvent.MOUSE_CLICKED, 0, 0, getWidth() / 2, 
                    getHeight() / 2, 1, false);
            getMouseListeners()[0].mouseClicked(e);
        }
    }
    
    /**
     * Retrieves the Action Listener to be called when the button is clicked.
     * 
     * @return the Action Listener to be called when the button is clicked
     */
    public ActionListener getActionListener() {
        return actionListener;
    }
    
    /**
     * Retrieves the text displayed by the button.
     * 
     * @return the text displayed by the button
     */
    public String getText() {
        return text;
    }
    
    @Override
    public void highlight(int x, int y, int radius) {
        super.highlight(x, y, radius);
        
        // Set the text color to white.
        textColor = getForeground();
        setForeground(getBackground());
    }
    
    // Initializes the mouse listener for the button.
    private final void initMouseListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Alert the connected ActionListener.
                if (actionListener != null)
                    actionListener.actionPerformed(new ActionEvent(
                            this, ActionEvent.ACTION_PERFORMED, null));
                
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                stopAnimations();
                highlight(e.getX(), e.getY(), 10);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
                stopAnimations();
                dehighlight(e.getX(), e.getY(), 0);
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the text.
        if (getFont() != null) {
            // Set the font.
            g.setFont(getFont());
            // Set the color to draw the font.
            g.setColor(getForeground());
            // Get the font metrics.
            Rectangle2D bounds = g.getFontMetrics().getStringBounds(text, g);
            // The text should be centered on the button.
            int x = getWidth() / 2 - (int)bounds.getCenterX();
            int y = getHeight() / 2 - (int)bounds.getCenterY();
            g.drawString(text, x, y);
        }
    }
    
    /**
     * Sets the action listener that is called when the button is pressed.
     * 
     * @param actionListener the action listener to be called when the button 
     *                       is pressed
     */
    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }
    
    /**
     * Sets the text displayed on the button.
     * 
     * @param text the new text to be displayed on the button.
     */
    public void setText(String text) {
        this.text = text;
    }
}

package amalgamation.menus.components;

import amalgamation.Amalgamation;
import java.awt.Color;

/**
 * An AmalgamationNamePanel is a GUI element that can be used to display the
 * name and image of an Amalgamation.
 * 
 * @author Caleb Rush
 */
public class AmalgamationNamePanel extends acomponent.AComponent {
    // The panel that displays the image.
    private final AmalgamationPanel amalPanel;
    private javax.swing.JLabel nameLabel;
    
    /**
     * Creates an AmalgamationNamePanel that displays the name and full image
     * of the given Amalgamation.
     * 
     * @param amal the Amalgamation to display in the created panel.
     */
    public AmalgamationNamePanel(Amalgamation amal) {
        // Set the layout.
        setLayout(new java.awt.BorderLayout());
        // Create an AmalgamationPanel with the given Amalgamation.
        amalPanel = new AmalgamationPanel(amal);
        add(amalPanel);
        // Get rid of the border on the amalPanel.
        amalPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        // Create a border around the full panel.
        setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 2, 2, 
                Color.LIGHT_GRAY));
        // Set the background.
        setBackground(new java.awt.Color(244, 67, 54));
        // Initialize the JLabel.
        initNameLabel();
    }
    
    // Initialize the name label.
    private void initNameLabel() {
        nameLabel = new javax.swing.JLabel(
                amalPanel.getAmalgamation().getName());
        nameLabel.setFont(new java.awt.Font("Berlin Sans FB Demi", 
                java.awt.Font.BOLD, 18));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(nameLabel, java.awt.BorderLayout.SOUTH);
    }
    
    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
    }
    
    /**
     * Sets the Runnable to be run when this panel is clicked.
     * 
     * @param clickAction the Runnable to be run when this panel is clicked
     */
    public void setClickAction(Runnable clickAction) {
        amalPanel.setClickAction(clickAction);
    }
    
    @Override
    public void setHighlightColor(java.awt.Color highlightColor) {
        amalPanel.setHighlightColor(highlightColor);
        setBackground(highlightColor);
    }
    
    public static void main(String[] args) {
        javax.swing.JFrame window = new javax.swing.JFrame();
        window.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        
        AmalgamationNamePanel panel = new AmalgamationNamePanel(
                util.Amalgamations.load("Davy Jones"));
        panel.setHighlightColor(Color.GREEN);
        window.add(panel);
        
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}

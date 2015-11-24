package menus.components;

import amalgamation.Amalgamation;
import java.awt.Color;

/**
 * An AmalgamationDialog can be used to have a user select an Amalgamation from
 * the full list of available Amalgamations.
 * 
 * @author Caleb Rush
 */
public class AmalgamationDialog extends javax.swing.JDialog {
    // The number of columns of AmalgamaitionNamePanels to display.
    private static final int COLUMNS = 3;
    
    // The selected Amalgamation.
    private Amalgamation amal;
    
    // Creates a new AmalgamationDialog with the given parent.
    // AmalgamationDialogs are always modal.
    private AmalgamationDialog(javax.swing.JFrame parent) 
            throws java.io.IOException {
        super(parent, true);
        setLocationRelativeTo(parent);
        initLayout();
    }
    
    // <editor-fold desc="GUI Layout Init"> defaultstate="collapsed">
    // Initializes the layout of th dialog.
    private void initLayout() throws java.io.IOException {
        // Set the title.
        setTitle("Select an Amalgamation");
        // Set the layout.
        setLayout(new java.awt.BorderLayout());
        // Set the size of the dialog box.
        setSize(500, 500);
        // Set the background color of the dialog.
        getContentPane().setBackground(Color.WHITE);
        
        // Display instructions label.
        javax.swing.JLabel instructionsLabel = new javax.swing.JLabel();
        instructionsLabel.setText("Click the Amalgamation you would like to "
                + "select");
        instructionsLabel.setFont(new java.awt.Font("Berlin Sans FB Demi", 
                java.awt.Font.BOLD, 18));
        instructionsLabel.setForeground(new Color(244,67,54));
        instructionsLabel.setHorizontalAlignment(
                javax.swing.SwingConstants.CENTER);
        add(instructionsLabel, java.awt.BorderLayout.NORTH);
        
        // Create the panel containing all of the panels displaying the
        // Amalgamations.
        javax.swing.JPanel selectPanel = new javax.swing.JPanel();
        selectPanel.setBackground(java.awt.Color.WHITE);
        selectPanel.setLayout(new java.awt.GridBagLayout());
        
        // Create the scroll pane for the select panel.
        javax.swing.JScrollPane selectScrollPane = 
                new javax.swing.JScrollPane(selectPanel);
        selectScrollPane.setBorder(
                javax.swing.BorderFactory.createEmptyBorder());
        selectScrollPane.getVerticalScrollBar().setUnitIncrement(10);
        add(selectScrollPane);
        
        // Fill the selectPanel with AmalgamationNamePanels.
        Amalgamation[] amals = util.Amalgamations.getAmalgamations(
                util.Amalgamations.AMAL_RES_DIR);
        for (int i = 0; i < amals.length; i++) {
            // Create an AmalgamationNamePanel for each Amalgamation.
            AmalgamationNamePanel panel = new AmalgamationNamePanel(amals[i]);
            panel.setPreferredSize(new java.awt.Dimension(
                    selectPanel.getWidth() / COLUMNS, 
                    150));
            
            // Set the clickAction for the panel.
            final int index = i;
            panel.setClickAction(() -> {
                // Set the amalgamation to the chosen amalgamation.
                amal = amals[index];
                // Return control to the caller.
                setVisible(false);
            });
            
            // Create constraints for the panel.
            java.awt.GridBagConstraints constraints 
                    = new java.awt.GridBagConstraints();
            constraints.gridx = i % COLUMNS;
            constraints.gridy = i / COLUMNS;
            constraints.fill = java.awt.GridBagConstraints.BOTH;
            constraints.weightx = 1.0;
            
            // Add the panel to the select panel.
            selectPanel.add(panel, constraints);
        }
    }
    // </editor-fold>
    
    /**
     * Creates a dialog that allows the user to select an Amalgamation from the
     * list of Amalgamations in the local res/amal/ directory.
     * 
     * If the res/amal/ directory does not exist, an error message will be
     * displayed instead and the return value will be null.
     * 
     * @param parent the parent JFrame to place this dialog in. The dialog
     *               will center itself in this JFrame. This can be null.
     * @return the Amalgamation that the user selected. If the user closed the
     *         dialog box instead of selecting an Amalgamation, null will be
     *         returned. If the local Amalgamation resource directory cannot
     *         be found, an error message will appear instead of the dialog
     *         and null will be returned.
     */
    public static Amalgamation showDialog(javax.swing.JFrame parent) {
        try {
            AmalgamationDialog dialog = new AmalgamationDialog(parent);
            dialog.setVisible(true);
            return dialog.amal;
        } catch (java.io.IOException e) {
            javax.swing.JOptionPane.showMessageDialog(
                    parent,
                    String.format("Could not locate the %s directory!\n\n"
                            + "Please ensure this directory exists.\n\n%s", 
                            util.Amalgamations.AMAL_RES_DIR, 
                            e.getStackTrace().toString()),
                    "Could Not Locate Relative Amal Res Directory",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
            return null;
        }
    }
    
    public static void main(String[] args) {
        System.out.println(showDialog(null).getName());
    }
}

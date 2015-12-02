package menus.components;

import util.Amalgamations;
import java.awt.Color;

/**
 * The AmalgamationCreatorDialog allows the user to construct an Amalgamation
 * using all of the available Part resources. The Amalgamations created are
 * saved as Amalgamation resource files.
 * 
 * @author Caleb Rush
 */
public class AmalgamationCreatorDialog extends acomponent.ADialog {
    // The Body being constructed in he Dialog.
    private amalgamation.parts.Body body;
    
    /**
     * Creates new form AmalgamationCreatorDialog
     */
    private AmalgamationCreatorDialog(javax.swing.JFrame parent) {
        super(parent, true);
        getContentPane().setBackground(Color.WHITE);
        initComponents();
        // Center the dialog on the parent.
        setLocationRelativeTo(parent);
    }

    // <editor-fold desc="GUI Variables" defaultstate="collapsed">
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        InstructionsLabel = new javax.swing.JLabel();
        BodyPanel = new javax.swing.JPanel();
        NamePanel = new javax.swing.JPanel();
        NameLabel = new javax.swing.JLabel();
        NameField = new javax.swing.JTextField();
        DisplayPanel = new javax.swing.JPanel();
        SaveButton = new acomponent.AButton();
        PartsPane = new javax.swing.JTabbedPane();
        aButton1 = new acomponent.AButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Create an Amalgmation");
        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(java.awt.Color.white);

        InstructionsLabel.setFont(new java.awt.Font("Berlin Sans FB Demi", 0, 14)); // NOI18N
        InstructionsLabel.setForeground(new java.awt.Color(244, 67, 54));
        InstructionsLabel.setText("Design an Amalgamation by choosing all of the parts that will make it up. Changing the body type will change the type of other body parts that are available.");

        BodyPanel.setBackground(new java.awt.Color(30, 150, 243));
        BodyPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(30, 150, 243), 2), "Body Type", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Berlin Sans FB Demi", 1, 14), new java.awt.Color(30, 150, 243))); // NOI18N
        BodyPanel.setOpaque(false);
        // Add the list of Bodies to the BodyPanel.
        menus.components.PartListPanel BodyList
        = new menus.components.PartListPanel(
            util.Parts.TYPE_BODY);
        BodyPanel.add(BodyList);
        // Set the PartChangeListener for the BodyListPanel.
        BodyList.setPartChangeListener(part -> {
            // Set the body as the selected part.
            changeBody((amalgamation.parts.Body)part);
        });
        BodyPanel.setLayout(new java.awt.GridLayout(1, 0));

        NamePanel.setOpaque(false);

        NameLabel.setFont(new java.awt.Font("Berlin Sans FB", 1, 14)); // NOI18N
        NameLabel.setForeground(new java.awt.Color(244, 67, 54));
        NameLabel.setText("Name:");

        NameField.setBackground(new java.awt.Color(240, 240, 240));
        NameField.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 14)); // NOI18N
        NameField.setForeground(new java.awt.Color(244, 67, 54));
        NameField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        NameField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(244, 67, 54)));
        NameField.setCaretColor(new java.awt.Color(244, 67, 54));
        NameField.setDisabledTextColor(new java.awt.Color(255, 255, 254));
        NameField.setOpaque(false);
        NameField.setSelectionColor(new java.awt.Color(244, 67, 54));
        NameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NameFieldActionPerformed(evt);
            }
        });

        DisplayPanel.setBackground(new java.awt.Color(255, 255, 255));
        DisplayPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(244, 67, 54), 2));
        DisplayPanel d = new DisplayPanel();
        d.setOpaque(false);
        DisplayPanel.add(d);
        DisplayPanel.setLayout(new java.awt.GridLayout(1, 0));

        SaveButton.setBackground(new java.awt.Color(244, 67, 54));
        SaveButton.setActionListener(e -> save());
        SaveButton.setFont(new java.awt.Font("Berlin Sans FB Demi", 0, 14)); // NOI18N
        SaveButton.setText("Finish");

        javax.swing.GroupLayout SaveButtonLayout = new javax.swing.GroupLayout(SaveButton);
        SaveButton.setLayout(SaveButtonLayout);
        SaveButtonLayout.setHorizontalGroup(
            SaveButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        SaveButtonLayout.setVerticalGroup(
            SaveButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 23, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout NamePanelLayout = new javax.swing.GroupLayout(NamePanel);
        NamePanel.setLayout(NamePanelLayout);
        NamePanelLayout.setHorizontalGroup(
            NamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(DisplayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(NamePanelLayout.createSequentialGroup()
                .addComponent(NameLabel)
                .addGap(0, 315, Short.MAX_VALUE))
            .addGroup(NamePanelLayout.createSequentialGroup()
                .addComponent(NameField, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SaveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        NamePanelLayout.setVerticalGroup(
            NamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NamePanelLayout.createSequentialGroup()
                .addComponent(NameLabel)
                .addGroup(NamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(NamePanelLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(NameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, NamePanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SaveButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(DisplayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                .addContainerGap())
        );

        PartsPane.setBackground(new java.awt.Color(30, 150, 243));
        PartsPane.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(30, 150, 243), 2), "Parts", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Berlin Sans FB Demi", 1, 14), new java.awt.Color(30, 150, 243))); // NOI18N
        PartsPane.setForeground(new java.awt.Color(255, 255, 255));
        PartsPane.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        PartsPane.setTabPlacement(javax.swing.JTabbedPane.RIGHT);
        PartsPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        aButton1.setBackground(new java.awt.Color(255, 255, 255));
        aButton1.setBorder(null);
        aButton1.setForeground(new java.awt.Color(66, 66, 66));
        aButton1.setActionListener(e -> hideDialog());
        aButton1.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 18)); // NOI18N
        aButton1.setHighlightColor(new java.awt.Color(66, 66, 66));
        aButton1.setText("Cancel");

        javax.swing.GroupLayout aButton1Layout = new javax.swing.GroupLayout(aButton1);
        aButton1.setLayout(aButton1Layout);
        aButton1Layout.setHorizontalGroup(
            aButton1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 73, Short.MAX_VALUE)
        );
        aButton1Layout.setVerticalGroup(
            aButton1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(InstructionsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BodyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(NamePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PartsPane))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(aButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(InstructionsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(NamePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 37, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(PartsPane)
                            .addComponent(BodyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(aButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NameFieldActionPerformed
        SaveButton.doClick();
    }//GEN-LAST:event_NameFieldActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AmalgamationCreatorDialog dialog = new AmalgamationCreatorDialog(new javax.swing.JFrame());
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BodyPanel;
    private javax.swing.JPanel DisplayPanel;
    private javax.swing.JLabel InstructionsLabel;
    private javax.swing.JTextField NameField;
    private javax.swing.JLabel NameLabel;
    private javax.swing.JPanel NamePanel;
    private javax.swing.JTabbedPane PartsPane;
    private acomponent.AButton SaveButton;
    private acomponent.AButton aButton1;
    // End of variables declaration//GEN-END:variables
    // </editor-fold>

    // <editor-fold desc="Display Panel" defaultstate="collapsed">
    // The panel that displays the Body's image.
    private class DisplayPanel extends javax.swing.JPanel {
        @Override
        protected void paintComponent(java.awt.Graphics g){
            super.paintComponent(g);
            
            // Draw the Body's full image.
            if (body != null)
                g.drawImage(body.fullImage(), 0, 0, getWidth(), getHeight(), 
                        this);
        }
    }
    // </editor-fold>

    // Sets the body and updates the PartsPane.
    private void changeBody(amalgamation.parts.Body body) {
        this.body = body;
        
        // Remove all the tabs in the PartsPane.
        for (int i = PartsPane.getTabCount() - 1; i >= 0; i--)
            PartsPane.removeTabAt(i);
        
        // Set up the new tabs for the PartListPanels.
        for (int i = 0; i < body.getArmSlots().length; i++) {
            // Create a new tab containing the PartListPanel.
            menus.components.PartListPanel panel 
                    = new menus.components.PartListPanel(
                            util.Parts.TYPE_ARM,
                            (int)body.getArmSlots()[i].getRotationDegrees());
            PartsPane.addTab("Arm " + (i + 1), panel);
            
            // Set the PartChooseListener for the panel.
            final int index = i;
            panel.setPartChangeListener(part -> {
                // Set the specific slot to the chosen Part.
                body.getArmSlots()[index].setPart((amalgamation.parts.Arm)part);
                repaint();
            });
            
            // Set the rotation slider's listener for the panel.
            panel.setSliderListener(rotation -> {
                // Change the rotation for the specific slot.
                body.getArmSlots()[index].setRotationDegrees((double)rotation);
                repaint();
            });
        }
        
        // Set up the new tabs for the PartListPanels.
        for (int i = 0; i < body.getHeadSlots().length; i++) {
            // Create a new tab containing the PartListPanel.
            menus.components.PartListPanel panel 
                    = new menus.components.PartListPanel(
                            util.Parts.TYPE_HEAD,
                            (int)body.getHeadSlots()[i].getRotationDegrees());
            PartsPane.addTab("Head " + (i + 1), panel);
            
            // Set the PartChooseListener for the panel.
            final int index = i;
            panel.setPartChangeListener(part -> {
                // Set the specific slot to the chosen Part.
                body.getHeadSlots()[index].setPart(
                        (amalgamation.parts.Head)part);
                repaint();
            });
            
            // Set the rotation slider's listener for the panel.
            panel.setSliderListener(rotation -> {
                // Change the rotation for the specific slot.
                body.getHeadSlots()[index].setRotationDegrees((double)rotation);
                repaint();
            });
        }
        
        // Set up the new tabs for the PartListPanels.
        for (int i = 0; i < body.getLegSlots().length; i++) {
            // Create a new tab containing the PartListPanel.
            menus.components.PartListPanel panel 
                    = new menus.components.PartListPanel(
                            util.Parts.TYPE_LEG,
                            (int)body.getLegSlots()[i].getRotationDegrees());
            PartsPane.addTab("Leg " + (i + 1), panel);
            
            // Set the PartChooseListener for the panel.
            final int index = i;
            panel.setPartChangeListener(part -> {
                // Set the specific slot to the chosen Part.
                body.getLegSlots()[index].setPart((amalgamation.parts.Leg)part);
                repaint();
            });
            
            // Set the rotation slider's listener for the panel.
            panel.setSliderListener(rotation -> {
                // Change the rotation for the specific slot.
                body.getLegSlots()[index].setRotationDegrees((double)rotation);
                repaint();
            });
        }
        
        repaint();
    }
    
    /**
     * Displays a Dialog that allows the user to design and save their own
     * Amalgamation.
     * 
     * Calling this method will set up an AmalgamationCreatorDialog and cease
     * control to it until the user has either finished their creation or closed
     * the dialog.
     * 
     * The dialog will take care of the entire creation process. There is no
     * need for the caller to do anything besides call this method.
     * 
     * @param parent the JFrame this Dialog should be the child of. This JFrame
     *               will be unable to receive any input until the Dialog is
     *               closed.
     */
    public static void create(javax.swing.JFrame parent) {
        new AmalgamationCreatorDialog(parent).showDialog();
    }
    
    // Attmepts to save the creation.
    private void save() {
        // Check if the creation is complete.
        if (verifyCreation()) {
            // Save the creation to a file.
            Amalgamations.save(body, NameField.getText());
            // Close the dialog.
            hideDialog();
        }
       
    }
    
    // Returns true if the Amalgamation is complete.
    private boolean verifyCreation() {
        // Check the Body.
        if (body == null) {
            // Display an error message.
            acomponent.ADialog.createMessageDialog(
                    null,
                    "You haven't created anything yet!\n\n"
                            + "Choose a body type to get started."
            ).showDialog(
                    BodyPanel.getWidth() / 2 
                            + (int)BodyPanel.getLocationOnScreen().getX(), 
                    BodyPanel.getHeight() / 2
                            + (int)BodyPanel.getLocationOnScreen().getY()
            );
            return false;
        }
        // Check all of the Arm slots.
        for (amalgamation.parts.Slot s : body.getArmSlots())
            // Check if the slot is empty.
            if (s.getPart() == null) {
                // Display an error message.
                acomponent.ADialog.createMessageDialog(
                        null, 
                        "Your creation looks like it's missing an Arm!",
                        "Whoops"
                ).showDialog(
                        (int)DisplayPanel.getLocationOnScreen().getX() + s.getX(),
                        (int)DisplayPanel.getLocationOnScreen().getY() + s.getY()
                );
                return false;
            }
        
        // Check all of the Head slots.
        for (amalgamation.parts.Slot s : body.getHeadSlots())
            // Check if the slot is empty.
            if (s.getPart() == null) {
                // Display an error message.
                acomponent.ADialog.createMessageDialog(
                        null, 
                        "Your creation looks like it's missing a Head!",
                        "Whoops"
                ).showDialog(
                        (int)DisplayPanel.getLocationOnScreen().getX() + s.getX(),
                        (int)DisplayPanel.getLocationOnScreen().getY() + s.getY()
                );
                return false;
            }
        
        // Check all of the Leg slots.
        for (amalgamation.parts.Slot s : body.getLegSlots())
            // Check if the slot is empty.
            if (s.getPart() == null) {
                // Display an error message.
                acomponent.ADialog.createMessageDialog(
                        null, 
                        "Your creation looks like it's missing a Leg!",
                        "Whoops"
                ).showDialog(
                        (int)DisplayPanel.getLocationOnScreen().getX() + s.getX(),
                        (int)DisplayPanel.getLocationOnScreen().getY() + s.getY()
                );
                return false;
            }
        
        // Check the name.
        if ("".equals(NameField.getText())) {
            // Display an error message.
            acomponent.ADialog.createMessageDialog(
                    null,
                    "Your creation may want a name!",
                    "Maybe"
            ).showDialog(
                    (int)NameField.getLocationOnScreen().getX() 
                            + NameField.getWidth() / 2,
                    (int)NameField.getLocationOnScreen().getY() 
                            + NameField.getHeight() / 2
            );
            return false;
        }
        
        return true;
    }

}

package amalgamation.abilities;

/**
 * An AbilityPanel is a GUI component that can be used to visualize and display
 * an Ability.
 * 
 * @author Caleb Rush
 */
public class AbilityPanel extends acomponent.AComponent {
    // The color of the background when the Ability is unusable.
    private final static java.awt.Color BG_DISABLED 
            = new java.awt.Color(189, 189, 189);
    // The color of the background when the Ability is usable.
    private final static java.awt.Color BG_ENABLED
            = java.awt.Color.WHITE;
    // The color of the background when the panel is hovered over.
    private final static java.awt.Color BG_HOVERED
            = new java.awt.Color(139,195,74);
    // The color of the text when the Ability is unusable.
    private final static java.awt.Color TEXT_DISABLED
            = new java.awt.Color(97, 97, 97);
    // The color of the name when the Ability is a regular Ability.
    private final static java.awt.Color NAME_ABILITY 
            = new java.awt.Color(33, 150, 243);
    // The color of the name when the Ability is an Attack.
    private final static java.awt.Color NAME_ATTACK 
            = new java.awt.Color(244,67,54);
    // The color of the property text when the Ability is enabled.
    private final static java.awt.Color TEXT_ENABLED
            = new java.awt.Color(96,125,139);
    
    // The Ability this panel is creating a view for.
    private final Ability ability;
    // The Runnable to run when the panel is clicked.
    private Runnable clickAction;

    /**
     * Creates new form AbilityPanel
     */
    public AbilityPanel(Ability ability) {
        initComponents();
        
        // Set the ability.
        this.ability = ability;
        setHighlightColor(BG_HOVERED);
        NameLabel.setText(ability.getName());
        changePowerAccuracy(ability instanceof Attack? 
                ((Attack)ability).getDamage() : 0,
                ability.getAccuracy());
        if (ability.isUsable()) {
            changeCooldown(ability.getCooldown());
            setBackground(BG_ENABLED);
            NameLabel.setForeground(ability instanceof Attack?
                    NAME_ATTACK : NAME_ABILITY);
            PowerAccuracyLabel.setForeground(TEXT_ENABLED);
            CooldownLabel.setForeground(TEXT_ENABLED);
        }
        else {
            changeCooldown(ability.getCurrentCooldown());
            setBackground(BG_DISABLED);
            NameLabel.setForeground(TEXT_DISABLED);
            PowerAccuracyLabel.setForeground(TEXT_DISABLED);
            CooldownLabel.setForeground(TEXT_DISABLED);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        NameLabel = new javax.swing.JLabel();
        CooldownLabel = new javax.swing.JLabel();
        PowerAccuracyLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 2, 2, new java.awt.Color(102, 102, 102)));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                formMouseExited(evt);
            }
        });

        NameLabel.setBackground(new java.awt.Color(255, 255, 255));
        NameLabel.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 28)); // NOI18N
        NameLabel.setForeground(new java.awt.Color(244, 67, 54));
        NameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NameLabel.setText("Punch");

        CooldownLabel.setFont(new java.awt.Font("Berlin Sans FB Demi", 0, 14)); // NOI18N
        CooldownLabel.setForeground(new java.awt.Color(96, 125, 139));
        CooldownLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CooldownLabel.setText("Cooldown: 3 Turns");

        PowerAccuracyLabel.setFont(new java.awt.Font("Berlin Sans FB Demi", 0, 14)); // NOI18N
        PowerAccuracyLabel.setForeground(new java.awt.Color(97, 97, 97));
        PowerAccuracyLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PowerAccuracyLabel.setText("Power: 25    Accuracy: 100");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(NameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(CooldownLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PowerAccuracyLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(NameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PowerAccuracyLabel)
                .addGap(10, 10, 10)
                .addComponent(CooldownLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited
        exited(evt.getX(), evt.getY());
    }//GEN-LAST:event_formMouseExited

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
        entered(evt.getX(), evt.getY());
    }//GEN-LAST:event_formMouseEntered

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        javax.swing.SwingUtilities.invokeLater(this::clicked);
    }//GEN-LAST:event_formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CooldownLabel;
    private javax.swing.JLabel NameLabel;
    private javax.swing.JLabel PowerAccuracyLabel;
    // End of variables declaration//GEN-END:variables
    
    // Changes the value in the cooldown label.
    private void changeCooldown(int cooldown) {
        CooldownLabel.setText(String.format("Cooldown: %d Turns", cooldown));
    }
    
    // Changes the values in the power and accuracy label.
    private void changePowerAccuracy(int power, int accuracy) {
        // Set up the text for the label.
        String text = ability instanceof Attack?
                String.format("Power: %d    ", power):
                "";
        text += String.format("Accuracy: %d%%", accuracy);
        
        // Set the text.
        PowerAccuracyLabel.setText(text);
    }
    
    // Performs the given click action.
    private void clicked() {
        if (clickAction != null)
            clickAction.run();
    }
    
    // Changes the mouse icon and changes the background.
    private void entered(int x, int y) {
        if (ability.isUsable()) {
            stopAnimations();
            highlight(x, y, 10);
            setCursor(java.awt.Cursor.getPredefinedCursor(
                    java.awt.Cursor.HAND_CURSOR));
        }
    }
    
    // Changes the mouse icon and changes the background.
    private void exited(int x, int y) {
        if (ability.isUsable()) {
            stopAnimations();
            dehighlight(x, y, 0);
            setCursor(java.awt.Cursor.getDefaultCursor());
        }
    }
    
    /**
     * Sets the action to be run when this Panel is clicked.
     * 
     * The Panel can only be clicked when the contained Ability is usable, so
     * there is no need to check if the Ability is usable inside the passed
     * Runnable.
     * 
     * @param clickAction the Runnable to run when the panel is clicked.
     */
    public void setClickAction(Runnable clickAction) {
        this.clickAction = clickAction;
    }
}

package menus.components;

import amalgamation.Amalgamation;

/**
 * This class is used to display the currently selected Amalgamation's stats 
 * in a panel to the right of the display of the Amalgamation in the main menu
 *
 * @author Adam Meanor
 */
public class StatPanel extends acomponent.AComponent {
    
    Amalgamation amalgamation;

    /**
     * Creates new form StatPanel
     * 
     * @param amalgamation the currently displayed Amalgamation
     */
    public StatPanel(Amalgamation amalgamation) {
        initComponents();
        this.amalgamation = amalgamation;
        this.displayHealthStat(amalgamation);
        this.displayAttackStat(amalgamation);
        this.displayDefenseStat(amalgamation);
        this.displaySpeedStat(amalgamation);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        healthLabel = new javax.swing.JLabel();
        attackLabel = new javax.swing.JLabel();
        defenseLabel = new javax.swing.JLabel();
        speedLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 3, 3, new java.awt.Color(204, 204, 204)));
        setLayout(new java.awt.GridLayout(4, 0));

        healthLabel.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 18)); // NOI18N
        healthLabel.setForeground(new java.awt.Color(76, 175, 80));
        healthLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        healthLabel.setText("jLabel1");
        add(healthLabel);

        attackLabel.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 18)); // NOI18N
        attackLabel.setForeground(new java.awt.Color(244, 67, 54));
        attackLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        attackLabel.setText("jLabel2");
        add(attackLabel);

        defenseLabel.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 18)); // NOI18N
        defenseLabel.setForeground(new java.awt.Color(33, 150, 243));
        defenseLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        defenseLabel.setText("jLabel3");
        add(defenseLabel);

        speedLabel.setBackground(new java.awt.Color(66, 66, 66));
        speedLabel.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 18)); // NOI18N
        speedLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        speedLabel.setText("jLabel4");
        add(speedLabel);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel attackLabel;
    private javax.swing.JLabel defenseLabel;
    private javax.swing.JLabel healthLabel;
    private javax.swing.JLabel speedLabel;
    // End of variables declaration//GEN-END:variables
    
    /**
     * Retrieves the health stat for the currently displayed Amalgamation 
     * and displays it in an alabel
     * 
     * @param amalgamation the currently displayed Amalgamation
     */
    private void displayHealthStat(Amalgamation amalgamation) {
        healthLabel.setText("Health: " + amalgamation.getHealth());
    }
    
    /**
     * Retrieves the attack stat for the currently displayed Amalgamation 
     * and displays it in an alabel
     * 
     * @param amalgamation the currently displayed Amalgamation
     */
    private void displayAttackStat(Amalgamation amalgamation) {
        attackLabel.setText("Attack: " + amalgamation.getAttack());
    }
    
    /**
     * Retrieves the defense stat for the currently displayed Amalgamation 
     * and displays it in an alabel
     * 
     * @param amalgamation the currently displayed Amalgamation
     */
    private void displayDefenseStat(Amalgamation amalgamation) {
        defenseLabel.setText("Defense: " + amalgamation.getDefense());
    }
    
    /**
     * Retrieves the speed stat for the currently displayed Amalgamation 
     * and displays it in an alabel
     * 
     * @param amalgamation the currently displayed Amalgamation
     */
    private void displaySpeedStat(Amalgamation amalgamation) {
        speedLabel.setText("Speed: " + amalgamation.getSpeed());
    }
    
    public static void main(String[] args) {
        acomponent.ADialog dialog = new acomponent.ADialog(null, true);
        StatPanel sPanel = new StatPanel(util.Amalgamations.load("KILL ME"));
        dialog.add(sPanel);
        dialog.pack();
        dialog.showDialog();
    }

}

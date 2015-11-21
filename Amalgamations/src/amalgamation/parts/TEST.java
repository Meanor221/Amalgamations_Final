package amalgamation.parts;

import amalgamation.*;

/**
 * Testing Parts functionality.
 * 
 * @author Caleb Rush
 */
public class TEST extends javax.swing.JFrame {
    // The body to test.
    private Amalgamation test;
    // Image of the full body.
    private java.awt.Image image;
    // ImagePanel to draw the image.
    private final ImagePanel imagePanel = new ImagePanel();
    
    /**
     * Creates new form TEST
     */
    public TEST() {
        initComponents();    
        
        
        // Load the test Amalgamation.
        test = Amalgamations.load("Cthulu");
        // Display the Amalgamation's body and stats.
        image = test.getFullImage();
        HealthField.setText("" + test.getHealth());
        AttackField.setText("" + test.getAttack());
        DefenseField.setText("" + test.getDefense());
        SpeedField.setText("" + test.getSpeed());
        TarExpField.setText("" + test.getTargetExperience());
        DefExpField.setText("" + test.getDefeatedExperience());
        // Display the Amaglamation's Abilities.
        for (amalgamation.abilities.Ability a : test.getAbilities())
            if (a != null)
                AbilitiesPanel.add(new amalgamation.abilities.AbilityPanel(a));
        
        BodyPanel.add(imagePanel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BodyPanel = new javax.swing.JPanel();
        jSlider1 = new javax.swing.JSlider();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        HealthField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        AttackField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        DefenseField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        SpeedField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        TarExpField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        DefExpField = new javax.swing.JTextField();
        AbilitiesPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        BodyPanel.setBackground(new java.awt.Color(255, 255, 255));
        BodyPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout BodyPanelLayout = new javax.swing.GroupLayout(BodyPanel);
        BodyPanel.setLayout(BodyPanelLayout);
        BodyPanelLayout.setHorizontalGroup(
            BodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 748, Short.MAX_VALUE)
        );
        BodyPanelLayout.setVerticalGroup(
            BodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jSlider1.setMajorTickSpacing(60);
        jSlider1.setMaximum(360);
        jSlider1.setMinorTickSpacing(30);
        jSlider1.setPaintTicks(true);
        jSlider1.setValue(0);
        jSlider1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));

        jLabel1.setText("Health");
        jPanel1.add(jLabel1);

        HealthField.setEditable(false);
        HealthField.setColumns(10);
        HealthField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(HealthField);

        jLabel2.setText("Attack");
        jPanel1.add(jLabel2);

        AttackField.setEditable(false);
        AttackField.setColumns(10);
        AttackField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(AttackField);

        jLabel3.setText("Defense");
        jPanel1.add(jLabel3);

        DefenseField.setEditable(false);
        DefenseField.setColumns(10);
        DefenseField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(DefenseField);

        jLabel4.setText("Speed");
        jPanel1.add(jLabel4);

        SpeedField.setEditable(false);
        SpeedField.setColumns(10);
        SpeedField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(SpeedField);

        jLabel5.setText("Target Experience");
        jPanel1.add(jLabel5);

        TarExpField.setEditable(false);
        TarExpField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(TarExpField);

        jLabel6.setText("Defeated Experience");
        jPanel1.add(jLabel6);

        DefExpField.setEditable(false);
        DefExpField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(DefExpField);

        AbilitiesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Abilities"));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AbilitiesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BodyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSlider1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BodyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AbilitiesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider1StateChanged
        for (Slot s : test.getBody().getSlots())
            s.setRotationDegrees(jSlider1.getValue());
        image = test.getFullUpdatedImage();
        repaint();
    }//GEN-LAST:event_jSlider1StateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TEST.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TEST.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TEST.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TEST.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TEST().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AbilitiesPanel;
    private javax.swing.JTextField AttackField;
    private javax.swing.JPanel BodyPanel;
    private javax.swing.JTextField DefExpField;
    private javax.swing.JTextField DefenseField;
    private javax.swing.JTextField HealthField;
    private javax.swing.JTextField SpeedField;
    private javax.swing.JTextField TarExpField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSlider jSlider1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void paint(java.awt.Graphics g) {
        // Center the ImagePanel.
        imagePanel.center();
        super.paint(g);
    }
    
    // Panel to draw the image.
    private class ImagePanel extends javax.swing.JPanel {
        
        public void center() {
            setLocation(BodyPanel.getWidth() / 2 - image.getWidth(null) / 2,
                    BodyPanel.getHeight() / 2 - image.getHeight(null) / 2);
            setSize(image.getWidth(null), image.getHeight(null));
        }
        
        @Override
        protected void paintComponent(java.awt.Graphics g) {
            super.paintComponent(g);
            
            // Paint the image on the display panel.
            if (image != null)
                g.drawImage(
                        image,
                        0,
                        0,
                        null
                );
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tool;

/**
 *
 * @author Caleb Rush
 */
public class RandomNameEditor extends javax.swing.JFrame {
    // Add an adjective to the list.
    private void addAdjective() {
        // Retrieve the adjective from the user.
        String adjective = javax.swing.JOptionPane.showInputDialog(
                this,
                "Input the adjective you would like to add: "
        );
        
        // Check if the user entered anything.
        if (adjective == null || "".equals(adjective))
            return;
        
        // Add the name to the NamesList.
        ((javax.swing.DefaultListModel)AdjectivesList.getModel())
                .addElement(adjective);
        
        // Save the list of adjectives.
        saveAdjectives();
    }
    
    // Add a name to the list.
    private void addName() {
        // Retrieve the name from the user.
        String name = javax.swing.JOptionPane.showInputDialog(
                this,
                "Input the name you would like to add: "
        );
        
        // Check if the user entered anything.
        if (name == null || "".equals(name))
            return;
        
        // Add the name to the NamesList.
        ((javax.swing.DefaultListModel)NamesList.getModel()).addElement(name);
        
        // Save the list of names.
        saveNames();
    }
    
    // Deletes the currently selected adjectives.
    private void deleteAdjectives() {
        for (int index : AdjectivesList.getSelectedIndices())
            ((javax.swing.DefaultListModel)AdjectivesList.getModel())
                    .remove(AdjectivesList.getSelectedIndex());
        
        saveAdjectives();
    }
    
    // Deletes the currently selected names.
    private void deleteNames() {
        for (int index : NamesList.getSelectedIndices())
            ((javax.swing.DefaultListModel)NamesList.getModel())
                    .remove(NamesList.getSelectedIndex());
        
        saveNames();
    }
    
    // Save the list of adjectives.
    private void saveAdjectives() {
        try {
            // Retrieve the names from the list.
            String[] adjectives = new String[AdjectivesList.getModel().getSize()];
            for (int i = 0; i < adjectives.length; i++)
                adjectives[i] = (String)AdjectivesList.getModel().getElementAt(i);
            util.Randoms.saveAdjectives(adjectives);
        } catch (java.io.IOException e) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Could not save to the adjectives resource file!",
                    "Save failed!",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    // Save the list of names.
    private void saveNames() {
        try {
            // Retrieve the names from the list.
            String[] names = new String[NamesList.getModel().getSize()];
            for (int i = 0; i < names.length; i++)
                names[i] = (String)NamesList.getModel().getElementAt(i);
            util.Randoms.saveNames(names);
        } catch (java.io.IOException e) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Could not save to the names resource file!",
                    "Save failed!",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    // Test the random name generator.
    private void test() {
        // Ensure the lists are not empty.
        if (NamesList.getModel().getSize() == 0) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "There are no names to choose from!",
                    "No names",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        // Ensure the lists are not empty.
        if (AdjectivesList.getModel().getSize() == 0) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "There are no adjectives to choose from!",
                    "No adjectives",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        
        // Generate a random name.
        try {
            TestField.setText(util.Randoms.randomName());
        } catch (java.io.IOException e) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "The resource files could not be loaded!",
                    "Test failed",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    // <editor-fold desc="GUI Code" defaultstate="collapsed">
    /**
     * Creates new form RandomNameEditor
     */
    public RandomNameEditor() {
        initComponents();
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TestField = new javax.swing.JTextField();
        TestButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        NamesList = new javax.swing.JList();
        NamesList.setModel(new javax.swing.DefaultListModel());
        try {
            for (String s : util.Randoms.loadNames())
            ((javax.swing.DefaultListModel)NamesList.getModel()).addElement(s);
        } catch (java.io.IOException e) {
            javax.swing.JOptionPane.showMessageDialog(
                this,
                util.Randoms.NAMES_RES_PATH + " does not exist!",
                "Could not load names!",
                javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
        NamesAddButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        AdjectivesList = new javax.swing.JList();
        AdjectivesList.setModel(new javax.swing.DefaultListModel());
        try {
            for (String s : util.Randoms.loadAdjectives())
            ((javax.swing.DefaultListModel)AdjectivesList.getModel()).addElement(s);
        } catch (java.io.IOException e) {
            javax.swing.JOptionPane.showMessageDialog(
                this,
                util.Randoms.ADJS_RES_PATH + " does not exist!",
                "Could not load adjectives!",
                javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
        AdjectivesAddButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        TestField.setEditable(false);
        TestField.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        TestButton.setText("Test");
        TestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TestButtonActionPerformed(evt);
            }
        });

        jPanel1.setLayout(new java.awt.GridLayout(1, 2, 10, 0));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Names"));

        NamesList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamesListKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(NamesList);

        NamesAddButton.setText("Add");
        NamesAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NamesAddButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(NamesAddButton, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NamesAddButton))
        );

        jPanel1.add(jPanel2);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Adjectives"));

        AdjectivesList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AdjectivesListKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(AdjectivesList);

        AdjectivesAddButton.setText("Add");
        AdjectivesAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AdjectivesAddButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(AdjectivesAddButton, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AdjectivesAddButton))
        );

        jPanel1.add(jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(TestField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TestButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TestField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TestButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NamesAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NamesAddButtonActionPerformed
        addName();
    }//GEN-LAST:event_NamesAddButtonActionPerformed

    private void AdjectivesAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdjectivesAddButtonActionPerformed
        addAdjective();
    }//GEN-LAST:event_AdjectivesAddButtonActionPerformed

    private void TestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TestButtonActionPerformed
        test();
    }//GEN-LAST:event_TestButtonActionPerformed

    private void NamesListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamesListKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_DELETE)
            deleteNames();
    }//GEN-LAST:event_NamesListKeyPressed

    private void AdjectivesListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AdjectivesListKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_DELETE)
            deleteAdjectives();
    }//GEN-LAST:event_AdjectivesListKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RandomNameEditor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AdjectivesAddButton;
    private javax.swing.JList AdjectivesList;
    private javax.swing.JButton NamesAddButton;
    private javax.swing.JList NamesList;
    private javax.swing.JButton TestButton;
    private javax.swing.JTextField TestField;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
    // </editor-fold>
}

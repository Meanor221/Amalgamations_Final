package menus.components;

import amalgamation.Amalgamation;
import amalgamation.battle.Battle;
import amalgamation.battle.Controller;
import java.awt.Color;

/**
 * A BattleDialog allows a user to compete in a Battle.
 * 
 * @author Caleb Rush
 */
public class BattleDialog extends acomponent.ADialog implements Controller {
    // The amount of time each animation lasts.
    private static final int    ANIMATION_TIME = 400;
    // The amount of time a line of script stays on the screen.
    private static final int    SCRIPT_PAUSE = 2500;
    // The move chosen to be returned by the chooseMove method.
    private int                 moveChosen;
    // Whether or not the player is ready to advance the script.
    private boolean             scriptAdvance;
    // The most recent script formatted into a single string.
    private String              lastScript;
    // The saved locations of the Statuses.
    private java.awt.Point      playerStatusLoc;
    private java.awt.Point      opponentStatusLoc;
    // The saved locations of the Ability panels.
    private java.awt.Point      abilLoc1;
    private java.awt.Point      abilLoc2;
    private java.awt.Point      abilLoc3;
    private java.awt.Point      abilLoc4;
    // The saved bounds of the buttons.
    private java.awt.Rectangle  doNothingBounds;
    private java.awt.Rectangle  eventsBounds;
    private java.awt.Rectangle  forfeitBounds;
    
    // Animates the AbilityPanels and buttons back in.
    private void animateIn() {
        // Have the buttons re-enter.
        DoNothingButton.enter(doNothingBounds).await();
        EventsButton.enter(eventsBounds).await();
        ForfeitButton.enter(forfeitBounds).await();
        
        // Update the abilities.
        updateAbilities();
        
        // Return the ability panels to their original positions.
        AbilPanel1.translate((int)abilLoc1.getX(), (int)abilLoc1.getY(), 
                ANIMATION_TIME);
        AbilPanel2.translate((int)abilLoc2.getX(), (int)abilLoc2.getY(), 
                ANIMATION_TIME);
        AbilPanel3.translate((int)abilLoc3.getX(), (int)abilLoc3.getY(), 
                ANIMATION_TIME);
        AbilPanel4.translate((int)abilLoc4.getX(), (int)abilLoc4.getY(), 
                ANIMATION_TIME);
        
         // Return the status panels to their original positions.
        PlayerStatus.translate((int)playerStatusLoc.getX(), 
                (int)playerStatusLoc.getY(), 
                ANIMATION_TIME);
        OpponentStatus.translate((int)opponentStatusLoc.getX(), 
                (int)opponentStatusLoc.getY(), 
                ANIMATION_TIME).await();
        
        // Enable all the components again.
        setComponentsEnabled(true);
    }
    
    // Animates the AbilityPanels and buttons out and prepares to read the
    // script.
    private void animateOut() {
        // Disable the component.
        setComponentsEnabled(false);
        
        // Save the bounds of the buttons.
        doNothingBounds = DoNothingButton.getBounds();
        eventsBounds = EventsButton.getBounds();
        forfeitBounds = ForfeitButton.getBounds();
        
        // Have the buttons exit the screen.
        DoNothingButton.exit().await();
        EventsButton.exit().await();
        ForfeitButton.exit().await();
        
        // Save the locations of the AbilityPanels.
        abilLoc1 = AbilPanel1.getLocation();
        abilLoc2 = AbilPanel2.getLocation();
        abilLoc3 = AbilPanel3.getLocation();
        abilLoc4 = AbilPanel4.getLocation();
        
        // Slide the AbilityPanels to the left of the screen.
        AbilPanel1.translate(-300, AbilPanel1.getY(), ANIMATION_TIME);
        AbilPanel2.translate(-300, AbilPanel2.getY(), ANIMATION_TIME);
        AbilPanel3.translate(-300, AbilPanel3.getY(), ANIMATION_TIME);
        AbilPanel4.translate(-300, AbilPanel4.getY(), ANIMATION_TIME);
        
        // Save the locations of the StatusPanels.
        playerStatusLoc = PlayerStatus.getLocation();
        opponentStatusLoc = OpponentStatus.getLocation();
        
        // Translate the StatusPanels to be right below their respective panels.
        PlayerStatus.translate(PlayerPanel.getX(), 
                PlayerPanel.getY() + PlayerPanel.getHeight() + 6, 
                ANIMATION_TIME);
        OpponentStatus.translate(OpponentPanel.getX(), 
                OpponentPanel.getY() + OpponentPanel.getHeight() + 6, 
                ANIMATION_TIME).await();
    }
    
    @Override
    public int chooseMove(Amalgamation player, Amalgamation opponent, 
            String[] script) {
        
        // Set the chosen move to an invalid value.
        moveChosen = Integer.MAX_VALUE;
        
        // Wait until the chosen move is changed.
        while (moveChosen == Integer.MAX_VALUE)
            // Sleep for a little bit and check again later.
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        
        // Animate the moves out and prepare for the script to be read.
        animateOut();
        
        return moveChosen;
    }
    
    // Displays the script one line at a time and animates health changes.
    private void enactScript(String[] script, String player, String opponent) {
        // Do nothing if the script is empty.
        if (script.length == 0)
            return;
        
        // Build the modified script to be reread later.
        StringBuilder formattedScript = new StringBuilder();
        
        // Go through the script one line at a time.
        for (String line : script) {
            // Create an ALabel to display the line.
            acomponent.ALabel label = new acomponent.ALabel(
                    util.Abilities.cutDelimiter(line));
            
            // Add the label to the dialog underneath the bottom edge.
            add(label);
            label.setBackground(Color.WHITE);
            label.setBounds(0, getHeight(), getWidth(), 50);
            label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            
            // Animate the label to the center of the screen.
            label.slideY(-getHeight() / 2 - label.getHeight() / 2, 
                    ANIMATION_TIME).await();
            
            // Check if either amalgamation is defeated.
            if (PlayerStatus.getHealth() == 0) {
                PlayerStatus.slideY(1000, ANIMATION_TIME);
                PlayerPanel.exit().await();
            }
            if (OpponentStatus.getHealth() == 0) {
                OpponentStatus.slideY(1000, ANIMATION_TIME);
                OpponentPanel.exit().await();
            }
            
            // Check if either the user or the opponent changed health.
            int p = util.Abilities.healthChanged(line, player);
            int o = util.Abilities.healthChanged(line, opponent);
            if (p != 0)
                PlayerStatus.changeHealth(p);
            if (o != 0)
                OpponentStatus.changeHealth(o);
            
            // Pause until the user presses a key.
            scriptAdvance = false;
            while (!scriptAdvance)
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            // Animate the line off of the top of the screen.
            label.slideY(-getHeight() / 2 - label.getHeight() / 2, 
                    ANIMATION_TIME).then(() -> remove(label));
            
            // Add the line to the formatted script.
            formattedScript.append(util.Abilities.cutDelimiter(line))
                    .append("\n");
        }
        
        // Set the last script to the formatted script.
        lastScript = formattedScript.toString();
    }
    
    @Override
    public void endBattle(Amalgamation player, Amalgamation opponent,
            String[] script) {
        // Enact the script so the user can see how the battle ended.
        enactScript(script, player.getName(), opponent.getName());
        // Close the dialog.
        hideDialog();
    }
    
    @Override
    public void readScript(Amalgamation player, Amalgamation opponent,
            String[] script) {
        // Display the script to the user.
        enactScript(script, player.getName(), opponent.getName());
        // Animate the moves in so the user can select a move.
        if (doNothingBounds != null) 
            animateIn();
    }
    
    // Sets whether or not all components are enabled.
    private void setComponentsEnabled(boolean enabled) {
        PlayerPanel.setEnabled(enabled);
        OpponentPanel.setEnabled(enabled);
        AbilPanel1.setEnabled(enabled);
        AbilPanel2.setEnabled(enabled);
        AbilPanel3.setEnabled(enabled);
        AbilPanel4.setEnabled(enabled);
        DoNothingButton.setEnabled(enabled);
        EventsButton.setEnabled(enabled);
        ForfeitButton.setEnabled(enabled);
    }
    
    @Override
    public void startBattle(Amalgamation player, Amalgamation opponent) {
        // Wait for the dialog to show.
        while (!isShowing()) {
            try {
                // Sleep for a little bit and check again later.
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
                
        // Set the Amalgamations.
        PlayerPanel.setAmalgamation(player);
        PlayerStatus.setAmalgamation(player);
        OpponentPanel.setAmalgamation(opponent);
        OpponentStatus.setAmalgamation(opponent);
        
        // Set the player's Abilities.
        if (player.getAbilities()[0] != null)
            AbilPanel1.setAbility(player.getAbilities()[0]);
        else
            AbilPanel1.setVisible(false);
        
        if (player.getAbilities()[1] != null)
            AbilPanel2.setAbility(player.getAbilities()[1]);
        else
            AbilPanel2.setVisible(false);
        
        if (player.getAbilities()[2] != null)
            AbilPanel3.setAbility(player.getAbilities()[2]);
        else
            AbilPanel3.setVisible(false);
        
        if (player.getAbilities()[3] != null)
            AbilPanel4.setAbility(player.getAbilities()[3]);
        else
            AbilPanel4.setVisible(false);
        
        lastScript = "Nothing has happened yet.";
        
        // Animate in.
        setLocationRelativeTo(null);
        showDialog();
    }
    
    /**
     * Creates and displays a BattleDialog and starts a Battle with the given
     * Controllers and Amalgamations. The BattleDialog will have full control
     * until the Battle is over.
     * 
     * @param opponent the Controller that will oppose the user interacting with
     *                 the BattleDialog.
     * @param playerAmalgamation the Amalgamation the user interacting with the
     *                 BattleDialog will control in the Battle.
     * @param opponentAmalgamation the Amalgamation the opponent will control in
     *                             the Battle.
     */
    public static void startBattle(Controller opponent,
            Amalgamation playerAmalgamation, Amalgamation opponentAmalgamation) {
        // Create the dialog.
        BattleDialog dialog = new BattleDialog();
        
        // Create a new Battle with the dialog as the player controller.
        new Battle(playerAmalgamation, opponentAmalgamation, dialog, opponent);
        
        // Place the dialog underneath the screen until its ready to reveal
        // itself.
        dialog.setLocationRelativeTo(null);
        dialog.setLocation(dialog.getX(), 
                (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize()
                        .getHeight());
        // Make the dialog visible.
        dialog.setVisible(true);
    }
    
    // Updates the AbilityPanels.
    private void updateAbilities() {
        if (AbilPanel1.getAbility() != null)
            AbilPanel1.updateView();
        if (AbilPanel2.getAbility() != null)
            AbilPanel2.updateView();
        if (AbilPanel3.getAbility() != null)
            AbilPanel3.updateView();
        if (AbilPanel4.getAbility() != null)
            AbilPanel4.updateView();
    }
    
    // <editor-fold desc="GUI Code" defaultstate="collapsed" >
    /**
     * Creates new form BattleDialog
     */
    private BattleDialog() {
        super(null, true);
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

        PlayerPanel = new menus.components.AmalgamationPanel();
        OpponentPanel = new menus.components.AmalgamationPanel();
        PlayerStatus = new menus.components.StatusPanel();
        OpponentStatus = new menus.components.StatusPanel();
        OpponentStatus.setDisplayNumbers(false);
        AbilPanel1 = new menus.components.AbilityPanel();
        AbilPanel4 = new menus.components.AbilityPanel();
        AbilPanel2 = new menus.components.AbilityPanel();
        AbilPanel3 = new menus.components.AbilityPanel();
        DoNothingButton = new acomponent.AButton();
        EventsButton = new acomponent.AButton();
        ForfeitButton = new acomponent.AButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout PlayerPanelLayout = new javax.swing.GroupLayout(PlayerPanel);
        PlayerPanel.setLayout(PlayerPanelLayout);
        PlayerPanelLayout.setHorizontalGroup(
            PlayerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 327, Short.MAX_VALUE)
        );
        PlayerPanelLayout.setVerticalGroup(
            PlayerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout OpponentPanelLayout = new javax.swing.GroupLayout(OpponentPanel);
        OpponentPanel.setLayout(OpponentPanelLayout);
        OpponentPanelLayout.setHorizontalGroup(
            OpponentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 327, Short.MAX_VALUE)
        );
        OpponentPanelLayout.setVerticalGroup(
            OpponentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        AbilPanel1.setClickAction(() -> moveChosen = 0);

        AbilPanel4.setClickAction(() -> moveChosen = 3);

        AbilPanel2.setClickAction(() -> moveChosen = 1);

        AbilPanel3.setClickAction(() -> moveChosen = 2);

        DoNothingButton.setBackground(new java.awt.Color(33, 150, 243));
        DoNothingButton.setActionListener(e -> moveChosen = MOVE_DO_NOTHING
        );
        DoNothingButton.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 18)); // NOI18N
        DoNothingButton.setStretchImage(true);
        DoNothingButton.setText("Do Nothing");

        javax.swing.GroupLayout DoNothingButtonLayout = new javax.swing.GroupLayout(DoNothingButton);
        DoNothingButton.setLayout(DoNothingButtonLayout);
        DoNothingButtonLayout.setHorizontalGroup(
            DoNothingButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 168, Short.MAX_VALUE)
        );
        DoNothingButtonLayout.setVerticalGroup(
            DoNothingButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );

        EventsButton.setBackground(new java.awt.Color(76, 175, 80));
        EventsButton.setActionListener(e -> {
            // Show a dialog containing the last script.
            createMessageDialog(
                null,
                lastScript,
                "Done"
            ).showDialog(
                EventsButton.getWidth() / 2
                + (int)EventsButton.getLocationOnScreen().getX(),
                EventsButton.getHeight() / 2
                + (int)EventsButton.getLocationOnScreen().getY()
            );
        });
        EventsButton.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 18)); // NOI18N
        EventsButton.setText("Last Turn's Events");

        javax.swing.GroupLayout EventsButtonLayout = new javax.swing.GroupLayout(EventsButton);
        EventsButton.setLayout(EventsButtonLayout);
        EventsButtonLayout.setHorizontalGroup(
            EventsButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 168, Short.MAX_VALUE)
        );
        EventsButtonLayout.setVerticalGroup(
            EventsButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );

        ForfeitButton.setBackground(new java.awt.Color(244, 67, 54));
        ForfeitButton.setActionListener(e -> moveChosen = MOVE_FORFEIT);
        ForfeitButton.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 18)); // NOI18N
        ForfeitButton.setText("Forfeit");

        javax.swing.GroupLayout ForfeitButtonLayout = new javax.swing.GroupLayout(ForfeitButton);
        ForfeitButton.setLayout(ForfeitButtonLayout);
        ForfeitButtonLayout.setHorizontalGroup(
            ForfeitButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 168, Short.MAX_VALUE)
        );
        ForfeitButtonLayout.setVerticalGroup(
            ForfeitButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(PlayerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(OpponentStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(PlayerStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(OpponentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 50, Short.MAX_VALUE)
                .addComponent(AbilPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(AbilPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(AbilPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(AbilPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(DoNothingButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(EventsButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ForfeitButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(PlayerStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 174, Short.MAX_VALUE)
                        .addComponent(OpponentStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(PlayerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(OpponentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AbilPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AbilPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AbilPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AbilPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DoNothingButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EventsButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ForfeitButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        scriptAdvance = true;
    }//GEN-LAST:event_formKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                startBattle(
                        new amalgamation.battle.AIController(),
                        util.Amalgamations.load("Bowleg"),
                        util.Amalgamations.load("Horsey")
                );
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private menus.components.AbilityPanel AbilPanel1;
    private menus.components.AbilityPanel AbilPanel2;
    private menus.components.AbilityPanel AbilPanel3;
    private menus.components.AbilityPanel AbilPanel4;
    private acomponent.AButton DoNothingButton;
    private acomponent.AButton EventsButton;
    private acomponent.AButton ForfeitButton;
    private menus.components.AmalgamationPanel OpponentPanel;
    private menus.components.StatusPanel OpponentStatus;
    private menus.components.AmalgamationPanel PlayerPanel;
    private menus.components.StatusPanel PlayerStatus;
    // End of variables declaration//GEN-END:variables
    // </editor-fold>
}

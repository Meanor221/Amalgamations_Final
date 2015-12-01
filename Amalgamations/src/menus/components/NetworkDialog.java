package menus.components;

import acomponent.ADialog;

import amalgamation.Amalgamation;
import amalgamation.battle.Battle;

import java.io.IOException;

import network.*;

/**
 * A NetworkDialog prompts the user to connect to another user over a shared
 * network.
 * 
 * The NetworkDialog will take care of setting up a network connection and
 * starting a Battle. All that needs to be supplied is the Amalgamation.
 * 
 * @author Caleb Rush
 */
public class NetworkDialog extends ADialog {
    private Amalgamation amalgamation;
    
    private NetworkDialog(Amalgamation amalgamation) {
        super(null, true);
        this.amalgamation = amalgamation;
        
        // Add the text to the dialog.
        addText(
            "Would you like to <i>Host</i> a battle or <i>Join</i> a battle?\n\n"
            + "You should <i>Host</i> a battle if you are waiting on the other player.\n\n"
            + "You should <i>Join</i> a battle if the other player is waiting on you."
        );
        
        // Add a button to select Host.
        addButton("Host",
                e -> {
                    host();
                },
                new java.awt.Color(76, 175, 80) // Green
        );
        
        // Add a button to select Join.
        addButton("Join",
                e -> {
                    
                },
                new java.awt.Color(33, 150, 243) // Blue
        );
        
        // Add a cancel button.
        addButton("Cancel",
                e -> {
                    hideDialog();
                }
        );
        
        pack();
        setLocationRelativeTo(null);
    }
    
    // Sets up a NetworkAdapter and waits for another player to connect.
    private void host() {
        // Show a new HostDialog.
        new HostDialog();
    }
    
    /**
     * Creates a new NetworkDialog that will walk the user through the steps
     * of connecting to another player across a shared network.
     * 
     * @param amalgamation the Amalgamation the user will battle with when they
     *                     connect to the other player
     * @return the created NetworkDialog.
     */
    public static NetworkDialog createNetworkDialog(Amalgamation amalgamation) {
        return new NetworkDialog(amalgamation);
    }
    
    public static void main(String[] args) {
        createNetworkDialog(null).showDialog();
    }
    
    private class HostDialog extends acomponent.ADialog {
        public HostDialog() {
            super(null, true);
            
            // Attempt to create a NetworkController to be connected to by a
            // NetworkAdapter.
            try (NetworkController controller = new NetworkController()) {
                // Add directions.
                addText(
                    "Waiting for a player to join the battle.\n\n"
                    + "When the other player is ready, tell them to choose \"Join\""
                    + " and give them the following information:\n\n"
                    + "Host:     " + controller.getHost() + "\n"
                    + "Port:     " + controller.getPort() + "\n"
                );
                
                // Add a cancel button.
                addButton("Cancel", e -> {
                    hideDialog();
                    try {
                        controller.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
                
                // Show the dialog.
                pack();
                setLocationRelativeTo(null);
                showDialog();
                
                // Wait for a connection for the controller.
                controller.connect();
                
                // If the controller successfully connected, retrieve the
                // other Amalgamation.
                Amalgamation opponent = controller.retrieveAmalgamation();
                
                // Create a Battle with the NetworkController as the opposing
                // controller.
                menus.components.BattleDialog.startBattle(controller, 
                        amalgamation, opponent);
                
                hideDialog();
            } catch (IOException e) {
                hideDialog();
            }
        }
    }
}

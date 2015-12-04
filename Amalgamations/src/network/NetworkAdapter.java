package network;

import amalgamation.Amalgamation;
import amalgamation.battle.Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;
import util.Amalgamations;

/**
 * A NetworkAdapter can make a socket connection with a NetworkController and
 * connect to a specified Controller. Essentially, this allows the connected
 * Controller to function as the Controller in place of the NetworkController,
 * allowing the Controller to control a battle across a network connection.
 * 
 * @author Caleb Rush
 */
public class NetworkAdapter implements AutoCloseable {
    // The connected Socket that the Adapter receives information from.
    private final Socket socket;
    
    /**
     * Constructs a new NetworkAdapter that attempts to connect to the server
     * at the specified port on the specified host machine.
     * 
     * @param hostName the name of the host machine. This will be the result of
     *                 NetworkController::getHost
     * @param portNumber the port number that the connection should be made on.
     *                 This will be the result of NetworkController::getPort
     * @throws IOException if the connection cannot be made
     */
    public NetworkAdapter(String hostName, int portNumber) throws IOException {
        socket = new Socket(hostName, portNumber);
    }
    
    @Override
    public void close() throws IOException {
        socket.close();
    }
    
    /**
     * Connects the given controller to the NetworkAdapter and allows it to
     * act as a controller for a Battle hosted on the host machine.
     * 
     * @param amal the Amalgamation the player will be controlling
     * @param controller the controller to connect to the network
     */
    public void connectController(Controller controller, Amalgamation amal) {
        new Thread(() -> {
            // Create a new ObjectInputStream to read the Amalgamations sent over
            // the connection.
            try (ObjectOutputStream out
                    = new ObjectOutputStream(
                            socket.getOutputStream());
                ObjectInputStream in 
                    = new ObjectInputStream(
                            socket.getInputStream())) {
                // Send the amalgamation.
                out.writeObject(amal);
                out.flush();

                // Begin the battle by receiving the Amalgamations.
                Amalgamation player = (Amalgamation)in.readObject();
                Amalgamation opponent = (Amalgamation)in.readObject();
                controller.startBattle(player, opponent);

                // Continue reading in Amalgamations and scripts until a signal
                // signifying the end of the battle is received.
                boolean endBattle;
                String[] script;

                do {
                    // Read the endBattle signal.
                    endBattle = in.readBoolean();
                    // Read in the Amalgamations.
                    player = (Amalgamation)in.readObject();
                    opponent = (Amalgamation)in.readObject();
                    // Read in the script.
                    script = (String[])in.readObject();

                    // Check if the Controller should end the battle or choose a 
                    // move.
                    if (endBattle)
                    {
                        controller.endBattle(player, opponent, script);
                        if(script[script.length - 1].equals(opponent.getName() 
                                + "was defeated!"))
                        {
                            player.gainExp(opponent.getDefeatedExperience());
                            util.Amalgamations.save(player);
                        }
                    }
                    else {
                        // Have the controller read the script.
                        controller.readScript(player, opponent, script);
                        // Send the controller's move across the network.
                        out.write(controller.chooseMove(player, opponent, script));
                        out.flush();
                    }
                } while (!endBattle);   
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

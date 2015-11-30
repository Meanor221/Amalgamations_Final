package network;

import amalgamation.Amalgamation;
import amalgamation.battle.Controller;

import java.io.IOException;
import java.io.ObjectOutputStream;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * A NetworkController is a controller that sends all of the data given to it
 * by the Battle across a Network and uses its response to dictate the move it
 * chooses.
 * 
 * @author Caleb Rush
 */
public class NetworkController implements AutoCloseable, Controller {
    // The server socket.
    private final ServerSocket      server;
    // The socket connection.
    private Socket                  socket;
    
    /**
     * Constructs a new NetworkController which constructs a new ServerSocket.
     * 
     * @throws IOException if there is an IOException when constructing the
     *                     ServerSocket.
     */
    public NetworkController() throws IOException {
        // Create a new ServerSocket.
        server = new ServerSocket(0);
    }
    
    @Override
    public int chooseMove(Amalgamation player, Amalgamation opponent, 
            String[] script) {
        try {
            // Retrieve the move from the network.
            return socket.getInputStream().read();
        } catch (IOException e) {
            // Assume the opponent forfeited.
            return MOVE_FORFEIT;
        }
    }
    
    /**
     * Opens the ServerSocket and waits for a Socket to connect.
     * 
     * Note that this will block the thread until a Socket connects.
     * 
     * @throws IOException if the ServerSocket runs into an IOException when
     *                     attempting to connect.
     */
    public void connect() throws IOException {
        socket = server.accept();
    }
    
    @Override
    public void close() throws IOException {
        server.close();
        if (socket != null)
            socket.close();
    }
    
    @Override
    public void endBattle(Amalgamation player, Amalgamation opponent, 
            String[] script) {
        // Send the player, opponent, and script across the connection.
        try (ObjectOutputStream out 
                = new ObjectOutputStream(socket.getOutputStream())) {
            // Send a signal that this is the end of the battle.
            out.writeBoolean(true);
            out.writeObject(player);
            out.writeObject(opponent);
            out.writeObject(script);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Returns the textual representation of the host.
     * 
     * This is required to connect to the server. It is best to retrieve this
     * number before calling the connect method.
     * 
     * @return the textual representation of the host or a blank String if the
     *         host is unknown.
     */
    public String getHost() {
        try {
            return server.getInetAddress().getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "";
        }
    }
    
    /**
     * Returns the local port for the ServerSocket.
     * 
     * This is the port number that a connecting port will need to connect to
     * this. It is best to retrieve this number before calling the connect
     * method.
     * 
     * @return the local port for the ServerSocket
     */
    public int getPort() {
        return server.getLocalPort();
    }
    
    @Override
    public void readScript(Amalgamation player, Amalgamation opponent, 
            String[] script) {
        // Send the player, opponent, and script across the connection.
        try (ObjectOutputStream out 
                = new ObjectOutputStream(socket.getOutputStream())) {
            // Send a signal that this is not the end of the battle.
            out.writeBoolean(false);
            out.writeObject(player);
            out.writeObject(opponent);
            out.writeObject(script);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void startBattle(Amalgamation player, Amalgamation opponent) {
        // Send the player and opponent across the connection.
        try (ObjectOutputStream out 
                = new ObjectOutputStream(socket.getOutputStream())) {
            out.writeObject(player);
            out.writeObject(opponent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        try (NetworkController controller = new NetworkController()) {
            System.out.println(controller.getPort());
            System.out.println(controller.getHost());
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}

package network;

import amalgamation.Amalgamation;
import amalgamation.battle.Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
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
    // The input stream to receive the amalgamation through the connection.
    private ObjectInputStream       in;
    // The output stream to send objects through the socket connection.
    private ObjectOutputStream      out;
    
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
            return in.read();
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
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
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
        try {
            // Send a signal that this is the end of the battle.
            out.writeBoolean(true);
            out.flush();
            out.writeObject(player);
            out.flush();
            out.writeObject(opponent);
            out.flush();
            out.writeObject(script);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Retrieves the Amalgamation that the connected Controller wishes to
     * control.
     * 
     * Whether this Amalgamation will be used or not, this method should be
     * called before the battle starts, as the Amalgamation will always be the
     * first thing sent over by the NetworkAdapter. If this method is not
     * called before the battle, an IOException will almost certainly be thrown.
     * 
     * This method should not be called at any other time than this first time.
     * Doing so will result in the return value being null.
     * 
     * @return the Amalgamation the connected Controller wishes to control if
     *         this method was called in between the connect method and the
     *         Battle starting. Otherwise, this will be null.
     */
    public Amalgamation retrieveAmalgamation() {
        try {
            return (Amalgamation)in.readObject();
        } catch (ClassNotFoundException | IOException e) {
            return null;
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
            return server.getInetAddress().getLocalHost().getHostAddress();
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
        try {
            // Reset the OutputStream, the sent Amalgamations will simply be
            // references to the older versions sent earlier.
            out.reset();
            
            // Send a signal that this is not the end of the battle.
            out.writeBoolean(false);
            out.flush();
            out.writeObject(player);
            out.flush();
            out.writeObject(opponent);
            out.flush();
            out.writeObject(script);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void startBattle(Amalgamation player, Amalgamation opponent) {
        // Send the player and opponent across the connection.
        try {
            out.writeObject(player);
            out.flush();
            out.writeObject(opponent);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

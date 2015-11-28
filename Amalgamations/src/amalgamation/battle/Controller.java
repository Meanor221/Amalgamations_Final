package amalgamation.battle;

import amalgamation.Amalgamation;

/**
 * A Controller is used to determine the Ability that is performed by an
 * Amalgamation in a Battle. 
 * 
 * A Controller may interface with a user to make the decisions, or it may
 * implement an algorithm to decide what to do. 
 * 
 * @author Caleb Rush
 */
public interface Controller {
    // The move to do nothing on the next turn.
    int MOVE_DO_NOTHING = -1;
    // The move to forfeit the Battle on the next turn.
    int MOVE_FORFEIT    = -2;
    
    /**
     * Decides what move to do for the next turn in the Battle.
     * 
     * @param player the Amalgamation being controlled by the Controller
     * @param opponent the Amalgamation opposing the player.
     * @param script the script for the most recent turn. This can be scanned
     *               to make more complex decisions on the move to be made
     * @return the move to do for the next turn in the Battle. This should
     *         be either the index of one of the player's Abilities or the ID
     *         of one of the special actions.
     */
    int chooseMove(Amalgamation player, Amalgamation opponent, String[] script);
    
    /**
     * Called at the end of the Battle. This is used to make any finishing
     * adjustments. If the Controller interacts with the end user, this is the
     * opportunity to display the rest of the script.
     * 
     * @param player the Amalgamation being controlled by the Controller
     * @param opponent the Amalgamation opposing the player.
     * @param script the script for the end of the Battle.
     */
    void endBattle(Amalgamation player, Amalgamation opponent, String[] script);
    
    /**
     * Called just before chooseMove each turn.
     * 
     * When this method is called, it will be run on a separate thread,
     * meaning that the chooseMove method will also be able to run concurrently
     * with this method. The reason for this is to allow for both Controllers
     * in a Battle to view the previous turn's script simultaneously instead
     * of one having to wait for the other to finish.
     * 
     * @param player the Amalgamation being controlled by the Controller
     * @param opponent the Amalgamation opposing the player.
     * @param script the script for the most recent turn. This can be scanned
     *               to make more complex decisions on the move to be made
     */
    void readScript(Amalgamation player, Amalgamation opponent, String[] script);
    
    /**
     * Called at the start of the Battle. This is used to make any preparations
     * for the Battle that must be made at the start.
     * 
     * @param player the Amalgamation that the Controller will control
     * @param opponent the Amalgamation that the Opponent will control
     */
    void startBattle(Amalgamation player, Amalgamation opponent);
}

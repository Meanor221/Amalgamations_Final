package amalgamation.battle;

import amalgamation.Amalgamation;

import java.util.ArrayList;

/**
 * This class is the battle system 
 * which is used to allow two Amalgamations to do combat.
 * 
 * @author Adam Meanor, Caleb Rush
 */
public class Battle {
    // The battling Amalgamations.
    private final Amalgamation playerAmalgamation;
    private final Amalgamation opponentAmalgamation;
    // The Controllers for the Amalgamations.
    private final Controller player;
    private final Controller opponent;
    // The script for the most recent turn.
    private final ArrayList<String> script;
    // Whether or not the player won.
    private boolean playerWon;
    // Whether or not the opponent won.
    private boolean opponentWon;
    
    /**
     * Constructs a new Battle between the specified Amalgamations with the 
     * specified Controllers.
     * 
     * @param playerAmalgamation the player Amalgamation
     * @param opponentAmalgamation the opponent Amalgamation
     * @param player the Controller for the player
     * @param opponent the Controller for the opponent
     */
    public Battle(Amalgamation playerAmalgamation, 
            Amalgamation opponentAmalgamation, Controller player, 
            Controller opponent) {
        this.playerAmalgamation = playerAmalgamation;
        this.opponentAmalgamation = opponentAmalgamation;
        this.player = player;
        this.opponent = opponent;
        script = new ArrayList<>();
    }
    
    /**
     * Checks whether or not the Battle should end.
     * 
     * @return whether or not the Battle should end.
     */
    public boolean checkEndCondition() {
        return playerWon || opponentWon;
    }
    
    /**
     * Performs the given move for the combination of Amalgamations.
     * 
     * @param user the Amalgamation performing the move
     * @param target the Amalgamation opposing the user
     * @param move the move to perform
     */
    public void doMove(Amalgamation user, Amalgamation target, int move) {
        // Check if the move was a forfeit.
        if (move == Controller.MOVE_FORFEIT) {
            // Check if the player or opponent forfeited.
            if (user == playerAmalgamation) {
                opponentWon = true;
                return;
            }
            
            playerWon = true;
            return;
        }
        
        // Check if the move was do nothing or if the index of the Ability was
        // invalid.
        if (move == Controller.MOVE_DO_NOTHING
                || move < 0
                || move >= user.getAbilities().length
                || !user.getAbilities()[move].isUsable()) {
            script.add(String.format("%s did nothing.", user.getName()));
            return;
        }
        
        // If the Ability is valid, have the user perform the Ability.
        script.addAll(java.util.Arrays.asList(
                user.getAbilities()[move].affect(user, target)));
    }
    
    /**
     * Retrieves the moves from the Controllers and enacts the moves.
     */
    public void doTurn() {
        // Retrieve the moves from the controllers.
        int playerMove = player.chooseMove(playerAmalgamation, 
                opponentAmalgamation, script.toArray(new String[0]));
        int opponentMove = opponent.chooseMove(opponentAmalgamation, 
                playerAmalgamation, script.toArray(new String[0]));
        
        // Clear the script.
        script.clear();
        
        // Determine which Amalgamation is faster.
        if (playerAmalgamation.getCurrentSpeed() 
                > opponentAmalgamation.getCurrentSpeed()) {
            // Do the player's move first.
            doMove(playerAmalgamation, opponentAmalgamation, playerMove);
            
            // Check the end condition
            if (checkEndCondition()) {
                // End the battle.
                endBattle();
                return;
            }
            
            // Do the opponent's move.
            doMove(opponentAmalgamation, playerAmalgamation, opponentMove);
        }
        else {
            // Do the opponent's move first.
            doMove(opponentAmalgamation, playerAmalgamation, opponentMove);
            
            // Check the end condition
            if (checkEndCondition()) {
                // End the battle.
                endBattle();
                return;
            }
            
            // Do the player's move.
            doMove(playerAmalgamation, opponentAmalgamation, playerMove);
        }
        
        // Check the win condition.
        if (!checkEndCondition())
            // Do another turn.
            doTurn();
    }
    
    /**
     * Ends the battle when an Amalgamation reaches 0 Health, 
     * the player forfeits, or exits the program.
     */
    public void endBattle() {
        // Check who won.
        if (playerWon) {
            script.add(String.format("%s was defeated!", 
                    opponentAmalgamation.getName()));
            // Raise the player's experience.
            playerAmalgamation.gainExp(
                    opponentAmalgamation.getDefeatedExperience());
            script.add(String.format("%s gained %d EXP!", 
                    playerAmalgamation.getName(), 
                    opponentAmalgamation.getDefeatedExperience()));
            
        }
        else
            script.add(String.format("%s was defeated!", 
                    playerAmalgamation.getName()));
        
        // Alert the controllers that the Battle has ended.
        player.endBattle(playerAmalgamation, opponentAmalgamation, 
                script.toArray(new String[0]));
        opponent.endBattle(opponentAmalgamation, playerAmalgamation, 
                script.toArray(new String[0]));
    }
    
    /**
     * Starts the Battle.
     */
    public void startBattle() {
        // Alert the controllers that the Battle has started.
        player.startBattle(playerAmalgamation, opponentAmalgamation);
        opponent.startBattle(opponentAmalgamation, playerAmalgamation);
        
        // Enact the first turn.
        doTurn();
    }
}

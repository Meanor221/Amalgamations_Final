package amalgamation.battle;

import amalgamation.Amalgamation;

/**
 * An AIController is a Controller that determines the move to make
 * programmatically without displaying any of the given information.
 * 
 * @author Caleb Rush
 */
public class AIController implements Controller {
    @Override
    public void endBattle(Amalgamation player, Amalgamation opponent, 
            String[] script) {}
    
    @Override
    public int chooseMove(Amalgamation player, Amalgamation opponent, 
            String script[]) {
        // Try to find an available move.
        for (int i = 0; i < player.getAbilities().length; i++) 
            // Check if the Ability at the current index is available.
            if (player.getAbilities()[i] != null)
                // Check if the Ability is usable.
                if (player.getAbilities()[i].isUsable())
                    return i;
        
        // If there are no available moves, do nothing.
        return MOVE_DO_NOTHING;
    }
    
    @Override
    public void readScript(Amalgamation player, Amalgamation opponent,
            String[] script) {}
    
    @Override
    public void startBattle(Amalgamation player, Amalgamation opponent) {}
}

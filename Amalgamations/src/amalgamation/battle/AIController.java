package amalgamation.battle;

import amalgamation.Amalgamation;

import java.util.ArrayList;
import java.util.Random;

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
        // See what Abilities are available.
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < player.getAbilities().length; i++)
            // Check if the ability at the index exists and is usable.
            if (player.getAbilities()[i] != null 
                    && player.getAbilities()[i].isUsable())
                indices.add(i);
        
        // If there are no usable indices, don't do anything.
        if (indices.isEmpty())
            return MOVE_DO_NOTHING;
        
        // Choose a random index.
        return indices.get(new Random().nextInt(indices.size()));
    }
    
    @Override
    public void readScript(Amalgamation player, Amalgamation opponent,
            String[] script) {}
    
    @Override
    public void startBattle(Amalgamation player, Amalgamation opponent) {}
}

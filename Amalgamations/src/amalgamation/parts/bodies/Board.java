package amalgamation.parts.bodies;

import amalgamation.parts.*;

/**
 * A test Head implementation. It's basically a Board.
 * 
 * This was hastily thrown together and is primarily for testing.
 *
 * @author Caleb Rush
 */
public class Board extends Body {
    // All final implementations should have an empty contructor.
    // There should be a better way to handle the IOException.
    public Board() throws java.io.IOException {
        super("Board", "Board.png",
                // Base Stats
                5, 5, 20, 5,
                // Arm slots
                new Slot[] { 
                    new Slot<Arm>(130, 153, 1), 
                    new Slot<Arm>(217, 156, 1) 
                },
                // Head slots
                new Slot[] { 
                    new Slot<Head>(170, 72, 1) 
                },
                // Leg slots
                new Slot[] { 
                    new Slot<Leg>(136, 261, 1), 
                    new Slot<Leg>(209, 262, 1) 
                }
        );
    }
}
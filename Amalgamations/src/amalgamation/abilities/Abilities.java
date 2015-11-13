package amalgamation.abilities;

import java.io.Serializable;

/**
 * Abilities is a utility class that contains many convenient constants and
 * functions to help manage Abilities.
 * 
 * All methods in this class are class functions, so they can be called without
 * an instance of this class.
 * 
 * @author Caleb Rush
 */
public class Abilities implements Serializable {
    // File extension for Ability files.
    public static final String  ABILITIES_FILE_EXT  = ".abil";
    // Directory for stored Ability files.
    public static final String  ABILITY_RES_DIR     = "res/abil/";
    // Types of Abilities.
    public static final int     TYPE_ABILITY        = 0;
    public static final int     TYPE_ATTACK         = 1;
    
    /**
     * Deletes the Ability file with the specified name.
     * 
     * This delete operation cannot be reversed and does not prompt the user,
     * so be sure to warn the user before performing this operation.
     * 
     * @param abilityFileName the name of the Ability file to delete (should not
     *                        include file extension or path)
     */
    public static void delete(String abilityFileName) {
        // Delete the file.
        new java.io.File(ABILITY_RES_DIR + abilityFileName + ABILITIES_FILE_EXT)
                .delete();
    }
    
    /**
     * Loads the Ability from the file with the specified name.
     * 
     * @param abilityFileName the name of the Ability file to delete (should not
     *                        include file extension or path)
     * @return the Ability loaded from the file.
     * @throws IllegalArgumentException if the abilityFileName is not the name
     *                                  of a valid file
     */
    public static Ability load(String abilityFileName) 
            throws IllegalArgumentException {
        // Attempt to open the file and load the ability.
        Ability ability;
        
        try (java.io.ObjectInputStream in = new java.io.ObjectInputStream(
                    new java.io.FileInputStream(
                            new java.io.File(ABILITY_RES_DIR 
                                    + abilityFileName + ABILITIES_FILE_EXT)
                    ))) {
            // Load the ability from the file.
            ability = (Ability)in.readObject();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(String.format(
                    "The ABILITY file %s does not exist in the %s directory", 
                    abilityFileName, ABILITY_RES_DIR));
        } catch (ClassNotFoundException e) {
            // If the class is not found, something is seriously wrong.
            e.printStackTrace();
            return null;
        }
        
        return ability;
    }
    
    /**
     * Retrieves the list of Abilities from the resource directory.
     * 
     * @return the list of Abilities from the resource directory
     */
    public static Ability[] getAbilities() {
        // Load the directory.
        java.io.File directory = new java.io.File(ABILITY_RES_DIR);
        
        // Load only files with the .part file extension.
        java.io.File[] files = directory.listFiles((dir, name) ->
                ABILITIES_FILE_EXT.equals( 
                    name.substring(name.lastIndexOf(".")))
        );
        
        // Load the Parts from each file.
        Ability[] abilities = new Ability[files.length];
        for (int i = 0; i < files.length; i++) {
            try (java.io.ObjectInputStream in = new java.io.ObjectInputStream(
                        new java.io.FileInputStream(files[i]))) {
                abilities[i] = (Ability)in.readObject();
            } catch (ClassNotFoundException | java.io.IOException e) {
                // If the class is not found, something is seriously wrong.
                e.printStackTrace();
                return null;
            }
        }
        
        return abilities;
    }
    
    /**
     * Retrieves the list of Ability files' names from the resource directory.
     * 
     * @return the list of Ability files from the resource directory
     */
    public static String[] getAbilityNames() {
        // Load the directory. 
        java.io.File directory = new java.io.File(ABILITY_RES_DIR); 
          
        // Load only files with the .abil file extension. 
        String[] fileNames = directory.list((dir, name) -> 
                ABILITIES_FILE_EXT.equals( 
                    name.substring(name.lastIndexOf("."))) 
        ); 
          
        // Remove the file extensions from the file names. 
        String[] names = new String[fileNames.length]; 
        for (int i = 0; i < names.length; i++) { 
            // Retrieve the last index of a period in the filename. 
            int index = fileNames[i].lastIndexOf('.'); 
            // Set the arm name to everything before the period. 
            names[i] = fileNames[i].substring(0, index); 
        } 
          
        return names; 
    }
    
    /**
     * Saves the Ability to a file with the given name.
     * 
     * @param type the type of Ability to save. The type should be one of the
     *             constants defined in this class.
     * @param name the name of the Ability to save
     * @param cooldown the number of turns the Ability should need to cool down
     * @param accuracy the accuracy of the move (out of 100)
     * @param damage the base damage of the move. This will only be used if the 
     *               type is TYPE_ATTACk.
     * @param modifiers an array of StatModifiers (should not be null)
     * @throws IllegalArgumentException if the type is an invalid type
     */
    public static void save(int type, String name, int cooldown, int accuracy, 
            int level, int damage, StatModifier[] modifiers) 
            throws IllegalArgumentException {
        // Create the Ability object.
        Ability ability = null;
        switch(type) {
            case TYPE_ABILITY:
                ability = new Ability(name, cooldown, accuracy, level, 
                        modifiers);
                break;
            case TYPE_ATTACK:
                ability = new Attack(name, cooldown, accuracy, level, damage, 
                        modifiers);
        }
        
        // Attempt to save the Ability to the specified file.
        try (java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(
                    new java.io.FileOutputStream(
                            new java.io.File(ABILITY_RES_DIR 
                                    + name + ABILITIES_FILE_EXT)
                    ))) {
            // Write the ability to the file.
            out.writeObject(ability);
        } catch (java.io.IOException e) {
            // If the directory doesn't exist, there's a problem.
            e.printStackTrace();
        }
    }
}

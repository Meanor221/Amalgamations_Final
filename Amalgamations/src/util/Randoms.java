package util;

import java.util.Random;

/**
 * A utility class to easily load from and save to the random resource files.
 * 
 * @author Caleb Rush
 */
public class Randoms {
    // The file extension for rand files.
    public static String RAND_FILE_EXT = ".rand";
    // The path to the random names file.
    public static String NAMES_RES_PATH = "res/rand/Names";
    // The path to the random adjectives file.
    public static String ADJS_RES_PATH = "res/rand/Adjectives";
    
    /**
     * Loads the random adjectives from the random adjectives resource file.
     * 
     * @return the array of names from the random adjectives resource file
     * @throws java.io.IOException if the random adjectives file does not exist 
     *                             or if the file is corrupted.
     */
    public static String[] loadAdjectives() throws java.io.IOException {
        try (java.io.ObjectInputStream in = new java.io.ObjectInputStream(
                    new java.io.FileInputStream(
                            ADJS_RES_PATH + RAND_FILE_EXT))) {
            // Read the String[] from the file.
            return (String[])in.readObject();
        } catch (ClassNotFoundException e) {
            throw new java.io.IOException("The random names resource file is "
                    + "corrupted!");
        }
    }
    
    /**
     * Loads the random names from the random name resource file.
     * 
     * @return the array of names from the random name resource file
     * @throws java.io.IOException if the random names file does not exist or if
     *                             the file is corrupted.
     */
    public static String[] loadNames() throws java.io.IOException {
        try (java.io.ObjectInputStream in = new java.io.ObjectInputStream(
                    new java.io.FileInputStream(
                            NAMES_RES_PATH + RAND_FILE_EXT))) {
            // Read the String[] from the file.
            return (String[])in.readObject();
        } catch (ClassNotFoundException e) {
            throw new java.io.IOException("The random names resource file is "
                    + "corrupted!");
        }
    }
    
    /**
     * Returns a randomly generated names using the list of names and adjectives
     * saved in the respective rand resource files.
     * 
     * This method constructs a name of the format "{NAME) the {ADJECTIVE}"
     * where {NAME} is a random name and {ADJECTIVE} is a random adjective.
     * 
     * @return the randomly generated name
     * @throws java.io.IOException if either of the resource files do not exist
     *                             or are corrupted
     * @throws IllegalArgumentException if either of the resource files are
     *                                  empty
     */
    public static String randomName() 
            throws java.io.IOException, IllegalArgumentException {
        return randomName(loadNames(), loadAdjectives());
    }
    
    /**
     * Returns a randomly generated name using the given list of names and
     * adjectives.
     * 
     * This method constructs a name of the format "{NAME) the {ADJECTIVE}"
     * where {NAME} is a random name and {ADJECTIVE} is a random adjective.
     * 
     * @param names the list of names to choose from.
     * @param adjectives the list of adjectives to choose from.
     * @return the randomly generated name
     * @throws IllegalArgumentException if either of the arrays are empty or 
     *                                  null
     */
    public static String randomName(String[] names, String[] adjectives) 
            throws IllegalArgumentException {
        // Ensure the arrays are not empty or null.
        if (names == null)
            throw new IllegalArgumentException(
                    "The array of names cannot be null!");
        else if (names.length == 0)
            throw new IllegalArgumentException(
                    "The array of names cannot be empty!");
        if (adjectives == null)
            throw new IllegalArgumentException(
                    "The array of adjectives cannot be null!");
        else if (adjectives.length == 0)
            throw new IllegalArgumentException(
                    "The array of adjectives cannot be empty!");
        
        // Create a random number generator.
        Random rand = new Random();
        
        // Choose a random name.
        String name = names[rand.nextInt(names.length)];
        
        // Add preposition to the name.
        name += " the ";
        
        // Add a random adjective to the name.
        name += adjectives[rand.nextInt(adjectives.length)];
        
        return name;
    }
    
    /**
     * Saves the given array of adjectives to the random adjectives resource 
     * file.
     * 
     * Note that this will overwrite the list of adjectives already stored in
     * the file.
     * 
     * @param adjectives the array of adjectives to save to the file
     * @throws java.io.IOException if the rand resource directory does not exist
     *                             or if it does not have write permissions.
     */
    public static void saveAdjectives(String[] adjectives) 
            throws java.io.IOException {
        try (java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(
                    new java.io.FileOutputStream(
                            ADJS_RES_PATH + RAND_FILE_EXT))) {
            // Write the string array to the file.
            out.writeObject(adjectives); 
        }
    }
    
    /**
     * Saves the given array of names to the random names resource file.
     * 
     * Note that this will overwrite the list of names already stored in the 
     * file.
     * 
     * @param names the array of names to save to the file
     * @throws java.io.IOException if the rand resource directory does not exist
     *                             or if it does not have write permissions.
     */
    public static void saveNames(String[] names) 
            throws java.io.IOException {
        try (java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(
                    new java.io.FileOutputStream(
                            NAMES_RES_PATH + RAND_FILE_EXT))) {
            // Write the string array to the file.
            out.writeObject(names); 
        }
    }
}

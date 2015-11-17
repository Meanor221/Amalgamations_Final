package amalgamation;

import amalgamation.parts.Body;

/**
 *
 * @author Adam Meanor
 */
public class Amalgamations {
    // File extension
    public static final String  AMAL_FILE_EXT  = ".amal";
    
    // The directory that holds this type of file
    public static final String  AMAL_RES_DIR   = "res/amal/";
    
    /**
     * Attempts to load an Amalgamation file
     * @param amalFileName
     * @return null and an error message if the load fails,
     * and the Amalgamation if the load is successful
     * @throws IllegalArgumentException 
     */
    public static Amalgamation load(String amalFileName) 
            throws IllegalArgumentException {
        String resDirectory = AMAL_RES_DIR;
        
        // Attempt to load the Amalgamation file.
        Amalgamation amalgamation;
        try (java.io.ObjectInputStream in = new java.io.ObjectInputStream(
                    new java.io.FileInputStream(
                            resDirectory + amalFileName + AMAL_FILE_EXT
                    ))) {
            // Load the Amalgamation from the file.
            amalgamation = (Amalgamation)in.readObject();
        } catch (java.io.IOException e) {
            throw new IllegalArgumentException(String.format(
                    "The AMAL file %s does not exist in the %s directory", 
                    amalFileName, resDirectory));
        } catch (ClassNotFoundException e) {
            // If the class is not found, something is seriously wrong.
            e.printStackTrace();
            return null;
        }
        
        return amalgamation;
    }
    
    /**
     * Gets an array of Amalgamation objects
     * @param dirPath the path to the directory
     * @return the array of Amalgamation objects
     * @throws java.io.IOException 
     */
    public static Amalgamation[] getAmalgamations(String dirPath) 
            throws java.io.IOException {
        // Load the directory.
        java.io.File directory = new java.io.File(dirPath);
        
        // Load only files with the .amal file extension.
        java.io.File[] files = directory.listFiles((dir, name) ->
                AMAL_FILE_EXT.equals( 
                    name.substring(name.lastIndexOf(".")))
        );
        
        // Load the Amalgamation from each file.
        Amalgamation[] amalgamations = new Amalgamation[files.length];
        for (int i = 0; i < files.length; i++) {
            try (java.io.ObjectInputStream in = new java.io.ObjectInputStream(
                        new java.io.FileInputStream(files[i]))) {
                amalgamations[i] = (Amalgamation)in.readObject();
            } catch (ClassNotFoundException e) {
                // If the class is not found, something is seriously wrong.
                e.printStackTrace();
                return null;
            }   
        }
        
        return amalgamations;
    }
    
    /**
     * Attempts to save an Amalgamation file to the directory
     * @param body the Body object of the Amalgamation
     * @param name the name of the Amalgamation and thus the name of the file
     * @throws IllegalArgumentException 
     */
    public static void save(Body body, String name) 
                throws IllegalArgumentException {
        Amalgamation amalgamation = new Amalgamation(name, body);
        String resDirectory = AMAL_RES_DIR;
        
         // Attempt to create the resource file.
        try (java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(
                    new java.io.FileOutputStream(
                            resDirectory + name + AMAL_FILE_EXT))) {
            // Write the created Part to the file.
            out.writeObject(amalgamation);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}

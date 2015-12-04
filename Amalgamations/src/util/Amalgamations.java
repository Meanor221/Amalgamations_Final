package util;

import amalgamation.Amalgamation;
import amalgamation.parts.Arm;
import amalgamation.parts.Body;
import amalgamation.parts.Head;
import amalgamation.parts.Leg;
import amalgamation.parts.Slot;

import java.util.Random;

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
     * Deletes the Amal file with the specified name.
     * 
     * This delete operation cannot be reversed and does not prompt the user,
     * so be sure to warn the user before performing this operation.
     * 
     * @param amalFileName the name of the Amal file to delete (should not
     *                        include file extension or path)
     */
    public static void delete(String amalFileName) {
        // Delete the file.
        new java.io.File(AMAL_RES_DIR + amalFileName + AMAL_FILE_EXT)
                .delete();
    }
    
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
     * Randomly generates an Amalgamation using any of the parts available in
     * the parts resource folder.
     * 
     * @return the randomly generated Amalgamation
     * @throws java.io.IOException if any of the part or rand resource folders
     *                             cannot be loaded or if any are empty
     */
    public static Amalgamation randomAmalgamation() throws java.io.IOException, 
            IllegalArgumentException {
        return new Amalgamation(
                util.Randoms.randomName(),
                util.Parts.randomBody());
    }
    
    /**
     * Randomly generates an Amalgamation using the given parts.
     * 
     * @param arms the Arms the Amalgamation can have
     * @param bodies the Bodies the Amalgamation can have
     * @param heads the Heads the Amalgamation can have
     * @param legs the Legs the Amalgamation can have
     * @return the randomly generated Amalgamation
     * @throws java.io.IOException if the names or adjectives resource files
     *                             cannot be loaded to generate a name
     * @throws IllegalArgumentException if any of the arrays are empty or null
     */
    public static Amalgamation randomAmalgamation(Arm[] arms, Body[] bodies, 
            Head[] heads, Leg[] legs) throws java.io.IOException, 
            IllegalArgumentException {
        return new Amalgamation(
                util.Randoms.randomName(),
                util.Parts.randomBody(arms, bodies, heads, legs));
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
    
    /**
     * Saves the given Amalgamation to a file.
     * 
     * @param amal the Amalgamation to save to a file.
     */
    public static void save(Amalgamation amal) {
        System.out.println(amal.getTargetExperience());
         // Attempt to create the resource file.
        try (java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(
                    new java.io.FileOutputStream(
                            AMAL_RES_DIR + amal.getName() + AMAL_FILE_EXT))) {
            // Write the created Part to the file.
            out.writeObject(amal);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}

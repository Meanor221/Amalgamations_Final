package util;

/**
 * CampaignLevels is a utility class that makes it simple to save and load
 * CampaignLevel resource files.
 * 
 * @author Caleb Rush
 */
public class CampaignLevels {
    // The file extension for CampaignLevels.
    public static final String LEVEL_FILE_EXT = ".clvl";
    // The path to the CampaignLevel resource directory.
    public static final String LEVEL_RES_DIR = "res/clvl/";
    
    /**
     * Deletes the CampaignLevel file with the specified name.
     * 
     * This delete operation cannot be reversed and does not prompt the user,
     * so be sure to warn the user before performing this operation.
     * 
     * @param name the name of the CampaignLevel file to delete (should not
     *                        include file extension or path)
     */
    public static void delete(String name) {
        // Delete the file.
        new java.io.File(LEVEL_RES_DIR + name + LEVEL_FILE_EXT)
                .delete();
    }
    
    /**
     * Retrieves the list of CampaignLevel files' names from the resource 
     * directory.
     * 
     * @return the list of CampaignLevel files from the resource directory
     */
    public static String[] getCampaignLevelNames() {
        // Load the directory. 
        java.io.File directory = new java.io.File(LEVEL_RES_DIR); 
          
        // Load only files with the .abil file extension. 
        String[] fileNames = directory.list((dir, name) -> 
                LEVEL_FILE_EXT.equals( 
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
     * Loads a CampaignLevel from the CampaignLevel res file with the given
     * name.
     * 
     * @param name the name of the res file to load the CampaignLevel from
     * @return the CampaignLevel from the CampaignLevel res file
     * @throws java.io.IOException if the file does not exist.
     */
    public static campaign.CampaignLevel loadCampaignLevel(String name) 
            throws java.io.IOException {
        try (java.io.ObjectInputStream in = new java.io.ObjectInputStream(
                    new java.io.FileInputStream(
                            LEVEL_RES_DIR + name + LEVEL_FILE_EXT))) {
            return (campaign.CampaignLevel)in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Saves the given CampaignLevel to a resource file.
     * 
     * @param level the level to save.
     * @throws java.io.IOException if the directory for level resources cannot
     *                             be opened.
     */
    public static void saveCampaignLevel(campaign.CampaignLevel level) 
            throws java.io.IOException {
        try (java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(
                    new java.io.FileOutputStream(
                            LEVEL_RES_DIR + level.getName() + LEVEL_FILE_EXT))) {
            out.writeObject(level);
        }
    }
}

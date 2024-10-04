package prelim;

import java.util.ArrayList;

public class FileManager {
    private Folder rootFolder;
    private ArrayList<Folder> defaultFolders;

    // Constructor
    public FileManager() throws Exception {
        // Create a root folder where all default folders will reside (simulating the root directory)
        rootFolder = new Folder("root");

        // Initialize the list of default folders
        defaultFolders = new ArrayList<>();

        // Create default folders
        createDefaultFolders();
    }

    // Method to create default folders
    private void createDefaultFolders() throws Exception {
        // Create the default folders: Desktop, Documents, Pictures, Music, Videos
        Folder desktop = new Folder("root/Desktop");
        Folder documents = new Folder("root/Documents");
        Folder pictures = new Folder("root/Pictures");
        Folder music = new Folder("root/Music");
        Folder videos = new Folder("root/Videos");

        // Add these folders to the root folder's subfolder list
        rootFolder.addSubfolder(desktop);
        rootFolder.addSubfolder(documents);
        rootFolder.addSubfolder(pictures);
        rootFolder.addSubfolder(music);
        rootFolder.addSubfolder(videos);

        // Also store them in the defaultFolders list for easy access
        defaultFolders.add(desktop);
        defaultFolders.add(documents);
        defaultFolders.add(pictures);
        defaultFolders.add(music);
        defaultFolders.add(videos);
    }

    // Get a specific default folder by its name
    public Folder getDefaultFolder(String folderName) {
        for (Folder folder : defaultFolders) {
            if (folder.getFolderName().equalsIgnoreCase(folderName)) {
                return folder;
            }
        }
        return null; // Folder not found
    }

    // Display the default folder names (can be used later in GUI to show folder names)
    public void displayDefaultFolders() {
        System.out.println("Default Folders:");
        for (Folder folder : defaultFolders) {
            System.out.println("- " + folder.getFolderName());
        }
    }

    // Getters
    public Folder getRootFolder() {
        return rootFolder;
    }

    public ArrayList<Folder> getDefaultFolders() {
        return defaultFolders;
    }
}

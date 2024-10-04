package prelim;

/**
 * The {@code FileManager} class is responsible for managing file directories and providing
 * access to the default root folder and its subfolders such as Desktop, Documents, Pictures,
 * Music, and Videos.
 */
public class FileManager {
    private Folder rootFolder;

    /**
     * Constructs a new {@code FileManager} and initializes the default folders in the root directory.
     *
     * @throws ListOverflowException if the maximum number of subfolders is exceeded during initialization.
     */
    public FileManager() throws ListOverflowException {
        rootFolder = new Folder("root");
        initializeDefaultFolders();
    }

    /**
     * Initializes the default directories under the root folder. These directories include:
     * Desktop, Documents, Pictures, Music, and Videos.
     *
     * @throws ListOverflowException if the maximum number of subfolders is exceeded.
     */
    private void initializeDefaultFolders() throws ListOverflowException {
        String[] defaultFolders = {"Desktop", "Documents", "Pictures", "Music", "Videos"};
        for (String folderName : defaultFolders) {
            Folder newFolder = new Folder(folderName);
            rootFolder.addSubfolder(newFolder);
        }
    }

    /**
     * Returns the root folder which contains all the default subfolders.
     *
     * @return The root {@code Folder} object.
     */
    public Folder getRootFolder() {
        return rootFolder;
    }

    /**
     * Retrieves the default folder matching the specified folder name.
     *
     * @param folderName The name of the folder to retrieve.
     * @return The {@code Folder} object if found, or {@code null} if the folder does not exist.
     */
    public Folder getDefaultFolder(String folderName) {
        Folder defaultFolder = new Folder(folderName);
        for (Folder folder : rootFolder.getSubfolders()) {
            if (folder.getFolderName().equalsIgnoreCase(defaultFolder.getFolderName())) {
                return folder;
            }
        }
        return null; // Folder not found
    }

    /**
     * Displays the names of all default folders initialized in the root directory.
     */
    public void displayDefaultFolders() {
        System.out.println("Default Folders:");
        for (Folder folder : rootFolder.getSubfolders()) {
            System.out.println(" - " + folder.getFolderName());
        }
    }
}

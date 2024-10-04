package prelim;
/**
 * TODO
 * */
public class FileManager {
    private Folder rootFolder;
    /**
     * Default constructor for the {@code FileManager} class.
     * */
    public FileManager() throws ListOverflowException {
        rootFolder = new Folder("root");
        initializeDefaultFolders();
    }
    /**
     * Initializes a {@code Folder} for each specific directories:
     * <p>
     * Desktop, Documents, Pictures, Music and Videos
     * */
    private void initializeDefaultFolders() throws ListOverflowException {
        String[] defaultFolders = {"Desktop", "Documents", "Pictures", "Music", "Videos"};
        for (String folderName : defaultFolders) {
            Folder newFolder = new Folder(folderName);
            rootFolder.addSubfolder(newFolder);
        }
    }
    /**
     * @return The root folder from a {@code Folder}.
     * */
    public Folder getRootFolder() {
        return rootFolder;
    }
    /**
     * TODO
     * */
    public Folder getDefaultFolder(String folderName) {
        for (Folder folder : rootFolder.getSubfolders()) {
            if (folder.getFolderName().equalsIgnoreCase(folderName)) {
                return folder;
            }
        }
        return null; // Folder not found
    }
    /**
     * TODO
     * */
    public void displayDefaultFolders() {
        System.out.println("Default Folders:");
        for (Folder folder : rootFolder.getSubfolders()) {
            System.out.println(" - " + folder.getFolderName());
        }
    }
}
package prelim;

public class FileManager {
    private Folder rootFolder;

    public FileManager() throws ListOverflowException {
        rootFolder = new Folder("root");
        initializeDefaultFolders();
    }

    private void initializeDefaultFolders() throws ListOverflowException {
        String[] defaultFolders = {"Desktop", "Documents", "Pictures", "Music", "Videos"};
        for (String folderName : defaultFolders) {
            Folder newFolder = new Folder(folderName);
            rootFolder.addSubfolder(newFolder);
        }
    }

    public Folder getRootFolder() {
        return rootFolder;
    }

    public Folder getDefaultFolder(String folderName) {
        for (Folder folder : rootFolder.getSubfolders()) {
            if (folder.getFolderName().equalsIgnoreCase(folderName)) {
                return folder;
            }
        }
        return null; // Folder not found
    }

    public void displayDefaultFolders() {
        System.out.println("Default Folders:");
        for (Folder folder : rootFolder.getSubfolders()) {
            System.out.println(" - " + folder.getFolderName());
        }
    }
}
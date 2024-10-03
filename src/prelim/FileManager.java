package prelim;


import java.util.Date;

/**
 * Class that facilitates operation; creation, deletion, etc. of files or folders
 */
public class FileManager {
    private String currentDirectory;
    private LinkedList<LinkedList<FileSystemEntity>> source;

    LinkedList<FileSystemEntity> desktop;
    LinkedList<FileSystemEntity> downloads;
    LinkedList<FileSystemEntity> documents;
    LinkedList<FileSystemEntity> pictures;
    LinkedList<FileSystemEntity> music;
    LinkedList<FileSystemEntity> videos;


    {
        currentDirectory = "source";
    }


    /**
     * Default Constructor; chains it with the parameterized constructor
     */
    public FileManager() {
        this(new LinkedList<LinkedList<FileSystemEntity>>());
    }

    /**
     * Constructor with arguments, declares values on datafields
     * @param source the source
     */
    public FileManager(LinkedList<LinkedList<FileSystemEntity>> source) {
        this.source = source;
        this.source.insert(desktop = new LinkedList<>());
        this.source.insert(downloads = new LinkedList<>());
        this.source.insert(documents = new LinkedList<>());
        this.source.insert(pictures = new LinkedList<>());
        this.source.insert(music = new LinkedList<>());
        this.source.insert(videos = new LinkedList<>());
    }


    /**
     * @return the String representation of the current directory
     */
    public String getDirectory() {
        return currentDirectory;
    }

    /**
     * Navigates through a folder
     * @param folderName the name of the folder to navigate through
     */
    public void navigateToFolder(String folderName) {
        currentDirectory = currentDirectory + "\\" + folderName;
    }

    /**
     * Navigates out from a folder
     */
    public void navigateBack() {
        if (currentDirectory.equals("source"))
            return;

        String[] path = currentDirectory.split("\\\\");

        StringBuilder newPath = new StringBuilder();
        for (int i = 0; i < path.length - 1; i++)  // don't include the last now
            newPath.append(path[i]);

        currentDirectory = newPath.toString();
    }

    /**
     * @param folderName the name of the folder to retrieve (path)
     * @return the path of the folder
     */
    public String getFolderPath(String folderName) {
        return currentDirectory + "\\" + folderName;
    }

    /**
     * @param fileName the name of the file to retrieve (path)
     * @return the path of the file
     */
    public String getFilePath(String fileName) {
        return currentDirectory + "\\" + fileName;
    }


    /**
     * Most used method. Call this every time it clicks a folder or goes back.
     * @return the contents of the current directory (folders, files).
     */
    public Object getCurrentDirectoryContents() {
        if (this.currentDirectory.equals("source"))
            return source;  // Return immediately

        String[] path = currentDirectory.split("\\\\");
        LinkedList<FileSystemEntity> contents = getDirectoryContents(path[1]);

        // Traverse, well if it's just "source/documents" or any same level, this for loop won't excute
        for (int i = 2; i < path.length; i++) {
            int index = contents.search(new Folder(path[i]));
            if (index != -1)
                contents = ((Folder) contents.getElement(index)).getSubContents();
            else
                return null; // This should not execute, For CLI-debugging purposes
        }

        return contents;
    }

    private Folder getCurrentFolder() {
        String[] path = currentDirectory.split("\\\\");
        LinkedList<FileSystemEntity> toReturn = getDirectoryContents(path[1]);

        // Traverse, well if it's just "source/documents" or any same level, this for loop won't excute
        for (int i = 2; i < path.length - 1; i++) {
            int index = toReturn.search(new Folder(path[i]));
            toReturn = ((Folder) toReturn.getElement(index)).getSubContents();
        }

        int index = toReturn.search(new Folder(path[path.length - 1]));
        return (Folder) toReturn.getElement(index);
    }


    private LinkedList<FileSystemEntity> getDirectoryContents(String dirType) {
        return switch (dirType) {
            case "Desktop" -> desktop;
            case "Documents" -> documents;
            case "Pictures" -> pictures;
            case "Music" -> music;
            case "Videos" -> videos;
            default -> downloads;
        };
    }


    /**
     * Creates a file in this current Directory
     * @param fileName the name of the file to Create
     * @param extension the extension of the file to Create
     * @param size the size of the file to Create
     */
    public void createFile(String fileName, String extension, int size) {
        File newFile = new File(fileName, extension, size, new Date());
        Folder currentFolderContainer = getCurrentFolder();
        currentFolderContainer.addFile(newFile);
    }

    /**
     * Creates a folder in this current directory.
     * @param folderName the name of the folder to create
     */
    public void createFolder(String folderName) {
        Folder newFolder = new Folder(folderName);
        Folder currentFolderContainer = getCurrentFolder();
        currentFolderContainer.addFolder(newFolder);
    }


    /**
     * Deletes a file in the current directory.
     *
     * @param fileName  the file name
     * @param extension the extension
     * @throws ListEmptyException when the current folder container is empty.
     */
    public void deleteFile(String fileName, String extension) throws ListEmptyException {
        File file = new File(fileName, extension);
        Folder currentFolderContainer = getCurrentFolder();
        currentFolderContainer.removeFile(file);
    }


    /**
     * Deletes a folder in the current directory.
     * @param folderName the name of the folder to be deleted
     * @throws ListEmptyException when the current folder container is empty.
     */
    public void deleteFolder(String folderName) throws ListEmptyException {
        Folder folder = new Folder(folderName);
        Folder currentFolderContainer = getCurrentFolder();
        currentFolderContainer.removeFolder(folder);
    }

    /**
     * Renames a file in this current directory
     * @param oldFileName the current name of the file to be renamed
     * @param oldExtension the current extension of the file to be renamed
     * @param newFileName the new name of the file
     * @param newExtension the new extension of the file
     */
    public void renameFile(String oldFileName, String oldExtension, String newFileName, String newExtension) {
        File oldFileMock = new File(oldFileName, oldExtension);
        Folder currentFolderContainer = getCurrentFolder();
        currentFolderContainer.renameAFileInsideThisFolder(oldFileMock, newFileName, newExtension);
    }

    /**
     * Renames a folder in this current directory
     * @param oldFolderName the current name of the folder to be changed
     * @param newFolderName the new name of the folder
     */
    public void renameFolder(String oldFolderName, String newFolderName) {
        Folder oldFolderMock = new Folder(oldFolderName);
        Folder currentFolderContainer = getCurrentFolder();
        currentFolderContainer.renameAFolderInsideThisFolder(oldFolderMock, newFolderName);
    }


    // TODO: make the File and Folder have a super class

    /**
     * Searches a file and returns a list of files that matches the fileName to Search
     * @param filename the fileName to search
     * @return an arraylist of files that matches the fileName to search
     */
    public MyGrowingArrayList<File> searchFile(String filename) {
        MyGrowingArrayList<File> foundFiles = new MyGrowingArrayList<>();
        searchFileRecursion(filename, foundFiles, (LinkedList<FileSystemEntity>) getCurrentDirectoryContents());
        return foundFiles;
    }

    // Depth-first search traversion
    private void searchFileRecursion(String filenameToSearch, MyGrowingArrayList<File> filesFound, LinkedList<FileSystemEntity> currentContents) {
        for (int i = 0; i < currentContents.getSize(); i++) {
            Object currentFileFolder = currentContents.getElement(i);

            if (currentFileFolder instanceof File file && file.getName().contains(filenameToSearch))
                filesFound.insert(file);
            else if (currentFileFolder instanceof Folder folder)
                searchFileRecursion(filenameToSearch, filesFound, folder.getSubContents());
        }
    }


}
package prelim;


import prelim.DataStructures.LinkedList;
import prelim.DataStructures.MyGrowingArrayList;
import prelim.Exceptions.ListEmptyException;
import prelim.Exceptions.SpecialFolderCreationException;
import prelim.Exceptions.SpecialFolderDeletionException;
import prelim.Objects.CustomFile;
import prelim.Objects.FileSystemEntity;
import prelim.Objects.Folder;
import prelim.Exceptions.NoSuchElementException;
import prelim.Exceptions.InvalidFileEntityNameException;
import prelim.Exceptions.InvalidFileExtensionException;

/**
 * Class that facilitates operation; creation, deletion, etc. of files or folders
 */
public class FileManager implements FileManagerInterface{
    private String currentPath;
    private final Folder source;

    // Special Folders
    private final Folder desktop;
    private final Folder downloads;
    private final Folder documents;
    private final Folder pictures;
    private final Folder music;
    private final Folder videos;


    {
        currentPath = "source";
    }


    /**
     * Default Constructor; chains it with the parameterized constructor
     */
    public FileManager() {
        this(new Folder("source"));
    }


    /**
     * Constructs the predefined root and its predefined folders such as Desktop, downloads, etc.
     * @param source the source
     */
    public FileManager(Folder source) {
        this.source = source;
        this.source.getSubContents().insert(desktop = new Folder("Desktop"));
        this.source.getSubContents().insert(downloads = new Folder("Downloads"));
        this.source.getSubContents().insert(documents = new Folder("Documents"));
        this.source.getSubContents().insert(pictures = new Folder("Pictures"));
        this.source.getSubContents().insert(music = new Folder("Music"));
        this.source.getSubContents().insert(videos = new Folder("Videos"));
    }


    /**
     * @return the String representation of the current directory
     */
    @Override
    public String getCurrentPath() {
        return currentPath;
    }


    /**
     * Navigates through a folder
     * @param folderName the name of the folder to navigate through
     */
    @Override
    public void navigateToFolder(String folderName) {
        if (getCurrentPathContents().search(new Folder(folderName)) == -1 )
            throw new NoSuchElementException("Folder not found. ");
        currentPath = currentPath + "\\" + folderName;
    }


    /**
     * Navigates out from a folder
     */
    @Override
    public void navigateBack() {
        if (currentPath.equals("source"))
            return;

        String[] path = currentPath.split("\\\\");

        StringBuilder newPath = new StringBuilder();
        newPath.append("source");
        for (int i = 1; i < path.length - 1; i++)  // don't include the last now
            newPath.append("\\").append(path[i]);

        currentPath = newPath.toString();
    }


    /**
     * @param folderName the name of the folder to retrieve (path)
     * @return the path of the folder
     */
    @Override
    public String getFolderPath(String folderName) { // For GUI
        return currentPath + "\\" + folderName;
    }


    /**
     * @param fileName the name of the file to retrieve (path)
     * @return the path of the file
     */
    @Override
    public String getFilePath(String fileName) {
        return currentPath + "\\" + fileName;
    }


    /**
     * Most used method. Call this every time it clicks a folder or goes back.
     * @return the contents of the current directory (folders, files).
     */
    @Override
    public LinkedList<FileSystemEntity> getCurrentPathContents() {
        if (this.currentPath.equals("source"))
            return source.getSubContents();  // Return immediately

        String[] path = currentPath.split("\\\\");
        LinkedList<FileSystemEntity> contents = source.getSubContents();

        // Traverse, well if it's just "source/documents" or any same level, this for loop won't excute
        for (int i = 1; i < path.length; i++) {
            int index = contents.search(new Folder(path[i]));
            if (index != -1)
                contents = ((Folder) contents.getElement(index)).getSubContents();
            else
                throw new NoSuchElementException("File not found"); // This should not execute, For CLI-debugging purposes
        }

        return contents;
    }


    private Folder getCurrentFolder() {
        String[] path = currentPath.split("\\\\");
        LinkedList<FileSystemEntity> toReturn = source.getSubContents();

        // Traverse
        for (int i = 1; i < path.length - 1; i++) {
            int index = toReturn.search(new Folder(path[i]));
            toReturn = ((Folder) toReturn.getElement(index)).getSubContents();
        }

        int index = toReturn.search(new Folder(path[path.length - 1]));
        return (Folder) toReturn.getElement(index);
    }


    /**
     * Creates a file in this current Directory
     * @param fileName the name of the file to Create
     * @param extension the extension of the file to Create
     */
    @Override
    public void createFileWithName(String fileName, String extension) {
        CustomFile newFile = new CustomFile(fileName, extension);
        createFile(newFile);
    }

    /**
     * Creates a file in this current Directory
     * @param newFile the file to be added in this directory
     */
    @Override
    public void createFile(CustomFile newFile) {
        if (currentPath.equals("source"))
            throw new SpecialFolderCreationException("Cannot create file at the same level as special folders. ");

        newFile.setPath(currentPath);
        getCurrentFolder().addFile(newFile);
    }


    /**
     * Creates a folder in this current directory.
     * @param folderName the name of the folder to create
     */
    public void createFolderWithName(String folderName) {
        if (currentPath.equals("source"))
            throw new SpecialFolderCreationException("Cannot create Folder at the same level as special folders. ");

        Folder newFolder = new Folder(folderName);
        newFolder.setPath(currentPath);
        Folder parentFolder = getCurrentFolder();
        parentFolder.addFolder(newFolder);
    }

    /**
     * Creates a folder in this current directory.
     * @param newFolder the folder to be added
     */
    @Override
    public void createFolder(Folder newFolder) {
        if (currentPath.equals("source"))
            throw new SpecialFolderCreationException("Cannot create Folder at the same level as special folders. ");

        Folder parentFolder = getCurrentFolder();
        newFolder.setPath(getCurrentPath());
        parentFolder.addFolder(newFolder);
    }


    /**
     * Deletes a file in the current path.
     *
     * @param fileName  the file name
     * @param extension the extension
     * @throws ListEmptyException when the current path is empty.
     * @throws NoSuchElementException when the file is not found in this current path.
     */
    @Override
    public void deleteFile(String fileName, String extension) throws ListEmptyException {
        if (currentPath.equals("source"))
            throw new SpecialFolderDeletionException("File deletion failed: No files found");

        CustomFile file = new CustomFile(fileName, extension);
        Folder parentFolder = getCurrentFolder();
        parentFolder.removeFile(file);
    }


    /**
     * Deletes a folder in the current path
     * @param folderName the name of the folder to be deleted in this current path
     * @throws ListEmptyException when the current path is empty.
     * @throws NoSuchElementException when the folder is not found in this current path.
     * @throws SpecialFolderDeletionException when it attempts to delete a folder in same level as the special folders.
     */
    @Override
    public void deleteFolder(String folderName) throws ListEmptyException {
        if (currentPath.equals("source"))
            throw new SpecialFolderDeletionException("Folder deletion failed: Cannot delete a Special Folder / Within the Special Folders");

        Folder folder = new Folder(folderName);
        Folder parentFolder = getCurrentFolder();
        parentFolder.removeFolder(folder);
    }


    /**
     * Renames a file in this current directory
     * @param oldFileName the current name of the file to be renamed
     * @param extension the extension of the file to be renamed
     * @param newFileName the new name of the file
     * @throws SpecialFolderDeletionException when tries to rename a file when in current path is "source"
     * @throws InvalidFileEntityNameException when the passed oldFileName or the newFileName has backslash or period.
     * @throws InvalidFileExtensionException when the passed extension is not valid.
     * @throws NoSuchElementException when the file to rename is not found in this current path.
     */
    @Override
    public void renameFile(String oldFileName, String extension, String newFileName) {
        if (currentPath.equals("source"))
            throw new SpecialFolderDeletionException("Renaming file failed: Files cannot be created in same level with Special Folders");

        CustomFile oldFileMock = new CustomFile(oldFileName, extension);
        Folder parentFolder = getCurrentFolder();
        parentFolder.renameAFileInsideThisFolder(oldFileMock, newFileName);
    }


    /**
     * Renames a folder in this current directory
     * @param oldFolderName the current name of the folder to be changed
     * @param newFolderName the new name of the folder
     */
    @Override
    public void renameFolder(String oldFolderName, String newFolderName) {
        if (currentPath.equals("source"))
            throw new SpecialFolderDeletionException("Renaming folder failed: Special Folders cannot be renamed. ");

        Folder oldFolderMock = new Folder(oldFolderName);
        Folder parentFolder = getCurrentFolder();
        parentFolder.renameAFolderInsideThisFolder(oldFolderMock, newFolderName);
    }



    /**
     * Searches a file and returns a list of files that matches the fileName to Search
     * @param filename the fileName to search
     * @return an arraylist of files that matches the fileName to search
     */
    @Override
    public MyGrowingArrayList<CustomFile> searchFiles(String filename) {
        Folder parentFolder = getCurrentFolder();
        return parentFolder.getMatchingFiles(filename);
    }

    /**
     * Searches a file and returns a list of files that matches the fileName to Search
     * @param filename the fileName to search
     * @return an arraylist of files that matches the fileName to search
     * @throws NoSuchElementException when it tries to acc
     * @throws InvalidFileExtensionException when the fileExtension is not a valid fileExtension
     * @throws InvalidFileEntityNameException when the fileName have a backslash or empty
     */
    @Override
    public CustomFile getFile(String filename, String fileExtension) {
        CustomFile fileToSearch = new CustomFile(filename, fileExtension);
        if (currentPath.equals("source"))
            source.searchFile(fileToSearch);

        return getCurrentFolder().searchFile(fileToSearch);
    }

    private boolean isInSpecialFolders(String currentPath) {
        return switch (currentPath) {
            case "source\\Desktop",
                    "source\\Downloads",
                    "source\\Documents",
                    "source\\Pictures",
                    "source\\Music",
                    "source\\Videos" -> true;
            default -> false;
        };
    }


    public void editFile(CustomFile fileToModify, String newContents) {
        fileToModify.setContents(newContents);
    }
}
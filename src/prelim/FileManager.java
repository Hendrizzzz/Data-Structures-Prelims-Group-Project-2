package prelim;


import prelim.DataStructures.LinkedList;
import prelim.DataStructures.MyGrowingArrayList;
import prelim.Exceptions.ListEmptyException;
import prelim.Exceptions.SpecialFolderDeletionException;
import prelim.Objects.CustomFile;
import prelim.Objects.FileSystemEntity;
import prelim.Objects.Folder;

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

import java.util.Date;

/**
 * Class that facilitates operation; creation, deletion, etc. of files or folders
 */
public class FileManager implements FileManagerInterface{
    private String currentPath;
    private LinkedList<LinkedList<FileSystemEntity>> source;

    LinkedList<FileSystemEntity> desktop;
    LinkedList<FileSystemEntity> downloads;
    LinkedList<FileSystemEntity> documents;
    LinkedList<FileSystemEntity> pictures;
    LinkedList<FileSystemEntity> music;
    LinkedList<FileSystemEntity> videos;


    {
        currentPath = "source";
    }


    /**
     * Default Constructor; chains it with the parameterized constructor
     */
    public FileManager() {
        this(new LinkedList<LinkedList<FileSystemEntity>>());
    }


    /**
     * Constructs the predefined root and its predefined folders such as Desktop, downloads, etc.
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
        for (int i = 0; i < path.length - 1; i++)  // don't include the last now
            newPath.append(path[i]);

        currentPath = newPath.toString();
    }


    /**
     * @param folderName the name of the folder to retrieve (path)
     * @return the path of the folder
     */
    @Override
    public String getFolderPath(String folderName) {
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
    public Object getCurrentPathContents() {
        if (this.currentPath.equals("source"))
            return source;  // Return immediately

        String[] path = currentPath.split("\\\\");
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
        String[] path = currentPath.split("\\\\");
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
        newFile.setPath(getCurrentPath());
        if (isInSpecialFolders(currentPath)) {
            LinkedList<FileSystemEntity> specialFolder = getSpecialFolder();
            specialFolder.insert(newFile);
        }
        else
            getCurrentFolder().addFile(newFile);
    }

    private LinkedList<FileSystemEntity> getSpecialFolder() {
        String[] currentPath = getCurrentPath().split("\\\\");
        String specialFolderString = currentPath[1];

        return switch (specialFolderString) {
            case "Desktop" -> desktop;
            case "Downloads" -> downloads;
            case "Documents" -> documents;
            case "Pictures" -> pictures;
            case "Music" -> music;
            default -> videos;
        };
    }


    /**
     * Creates a folder in this current directory.
     * @param folderName the name of the folder to create
     */
    public void createFolderWithName(String folderName) {
        Folder newFolder = new Folder(folderName);
        newFolder.setPath(getCurrentPath());
        Folder currentFolderContainer = getCurrentFolder();
        currentFolderContainer.addFolder(newFolder);
    }

    /**
     * Creates a folder in this current directory.
     * @param newFolder the folder to be added
     */
    @Override
    public void createFolder(Folder newFolder) {
        Folder currentFolderContainer = getCurrentFolder();
        newFolder.setPath(getCurrentPath());
        currentFolderContainer.addFolder(newFolder);
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
            throw new ListEmptyException("File deletion failed: No files found");

        CustomFile file = new CustomFile(fileName, extension);
        Folder currentFolderContainer = getCurrentFolder();
        currentFolderContainer.removeFile(file);
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
    @Override
    public void renameFile(String oldFileName, String oldExtension, String newFileName, String newExtension) {
        CustomFile oldFileMock = new CustomFile(oldFileName, oldExtension);
        Folder currentFolderContainer = getCurrentFolder();
        currentFolderContainer.renameAFileInsideThisFolder(oldFileMock, newFileName, newExtension);
    }


    /**
     * Renames a folder in this current directory
     * @param oldFolderName the current name of the folder to be changed
     * @param newFolderName the new name of the folder
     */
    @Override
    public void renameFolder(String oldFolderName, String newFolderName) {
        Folder oldFolderMock = new Folder(oldFolderName);
        Folder currentFolderContainer = getCurrentFolder();
        currentFolderContainer.renameAFolderInsideThisFolder(oldFolderMock, newFolderName);
    }


    /**
     * Searches a file and returns a list of files that matches the fileName to Search
     * @param filename the fileName to search
     * @return an arraylist of files that matches the fileName to search
     */
    @Override
    public MyGrowingArrayList<CustomFile> getMatchingFiles(String filename) {
        Folder currentFolderContainer = getCurrentFolder();
        return currentFolderContainer.getMatchingFiles(filename);
    }

    /**
     * Searches a file and returns a list of files that matches the fileName to Search
     * @param filename the fileName to search
     * @return an arraylist of files that matches the fileName to search
     */
    @Override
    public CustomFile searchFile(String filename) {
        if (isInSpecialFolders(currentPath)) {
            LinkedList<FileSystemEntity> specialFolder = getSpecialFolder();
            for (int i = 0; i < specialFolder.getSize(); i++) {
                FileSystemEntity currentFileSystemEntity = specialFolder.getElement(i);
                if (currentFileSystemEntity instanceof CustomFile file && file.getName().equalsIgnoreCase(filename))
                    return (CustomFile) currentFileSystemEntity;
            }
        }
        else
            return getCurrentFolder().searchFile(filename);

        throw new NoSuchElementException("File not found");
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


}
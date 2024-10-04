package prelim;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Folder} class represents a folder object with attributes such as folder name,
 * a list of files, a list of subfolders, and a reference to its parent folder. It supports
 * operations like adding files, creating subfolders, and retrieving the full path of the folder.
 */
public class Folder implements Comparable<Folder> {
    private String folderName;
    private MyDoublyLinkedCircularList<CustomFile> files;
    private MyDoublyLinkedCircularList<Folder> subfolders;
    private Folder parentFolder; // New field for parent folder

    /**
     * Constructs a {@code Folder} with a specified name and no parent folder.
     *
     * @param folderName The name of the folder.
     */
    public Folder(String folderName) {
        this.folderName = folderName;
        this.files = new MyDoublyLinkedCircularList<>();
        this.subfolders = new MyDoublyLinkedCircularList<>();
        this.parentFolder = null; // Default parent to null
    }

    /**
     * Constructs a {@code Folder} with a specified name and parent folder.
     *
     * @param folderName The name of the folder.
     * @param parentFolder The parent folder of this folder.
     */
    public Folder(String folderName, Folder parentFolder) {
        this(folderName);
        this.parentFolder = parentFolder;
    }

    /**
     * Sets the name of the {@code Folder}.
     *
     * @param folderName The new name of the folder.
     */
    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    /**
     * Adds a {@code CustomFile} to the folder.
     *
     * @param file The file to be added.
     * @throws ListOverflowException if the list of files exceeds its maximum size.
     */
    public void addFile(CustomFile file) throws ListOverflowException {
        files.insert(file);
    }

    /**
     * Creates and adds a subfolder within this folder.
     *
     * @param folder The subfolder to be added.
     * @throws ListOverflowException if the list of subfolders exceeds its maximum size.
     */
    public void addSubfolder(Folder folder) throws ListOverflowException {
        folder.parentFolder = this; // Set the parent folder
        subfolders.insert(folder);
    }

    /**
     * Sets the parent folder for this folder.
     *
     * @param parentFolder The new parent folder.
     */
    public void setParentFolder(Folder parentFolder) {
        this.parentFolder = parentFolder;
    }

    /**
     * Returns the name of the folder.
     *
     * @return The name of the folder.
     */
    public String getFolderName() {
        return folderName;
    }

    /**
     * Returns a list of all {@code CustomFile} objects within this folder.
     *
     * @return A list of files in the folder.
     */
    public List<CustomFile> getFiles() {
        List<CustomFile> fileList = new ArrayList<>();
        for (int i = 0; i < files.getSize(); i++) {
            fileList.add(files.getElement(i));
        }
        return fileList;
    }

    /**
     * Returns a list of subfolders within this folder.
     *
     * @return A list of subfolders in the folder.
     */
    public List<Folder> getSubfolders() {
        List<Folder> folderList = new ArrayList<>();
        for (int i = 0; i < subfolders.getSize(); i++) {
            folderList.add(subfolders.getElement(i));
        }
        return folderList;
    }

    /**
     * Returns the parent folder of this folder.
     *
     * @return The parent folder, or {@code null} if this folder has no parent.
     */
    public Folder getParentFolder() {
        return parentFolder;
    }

    /**
     * Returns the full path of the folder, from the root to this folder.
     *
     * @return The full path of the folder.
     */
    public String getFullPath() {
        if (parentFolder == null) {
            return folderName; // If no parent, return just the folder name
        }
        return parentFolder.getFullPath() + "/" + folderName; // Append folder name to parent's path
    }

    /**
     * Compares this folder with another folder based on their names.
     *
     * @param other The folder to compare against.
     * @return A negative integer, zero, or a positive integer if this folder's name is less than,
     *         equal to, or greater than the other folder's name.
     */
    @Override
    public int compareTo(Folder other) {
        return this.folderName.compareTo(other.folderName);
    }
}
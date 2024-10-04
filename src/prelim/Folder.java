package prelim;

import java.util.ArrayList;
import java.util.List;
/**
 * Reference class for an object called {@code Folder} that is utilized by the {@code FileExplorerMain}.
 * A folder consists of several fields/ attributes such as the folder name, list of files, list of subfolders, and a parent folder
 * */
public class Folder implements Comparable<Folder> {
    private String folderName;
    private MyDoublyLinkedCircularList<CustomFile> files;
    private MyDoublyLinkedCircularList<Folder> subfolders;
    private Folder parentFolder; // New field for parent folder
    /**
     * Default constructor for the {@code Folder} class.
     * */
    public Folder(String folderName) {
        this.folderName = folderName;
        this.files = new MyDoublyLinkedCircularList<>();
        this.subfolders = new MyDoublyLinkedCircularList<>();
        this.parentFolder = null; // Default parent to null
    }
    /**
     * Constructor for a {@code Folder} that have a specified name and parent folder.
     *
     * @param folderName The name of the {@code Folder}.
     * @param parentFolder The name of the parent folder.
     * */
    public Folder(String folderName, Folder parentFolder) {
        this(folderName);
        this.parentFolder = parentFolder;
    }
    /**
     * Sets the name of a {@code Folder}.
     * */
    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
    /**
     * Inserts a file within a {@code Folder}.
     * */
    public void addFile(CustomFile file) throws ListOverflowException {
        files.insert(file);
    }
    /**
     * Creates a subfolder within a {@code Folder}.
     * */
    public void addSubfolder(Folder folder) throws ListOverflowException {
        folder.parentFolder = this; // Set the parent folder
        subfolders.insert(folder);
    }
    /**
     * Sets the parent folder for a {@code Folder}.
     * */
    public void setParentFolder(Folder parentFolder) {
        this.parentFolder = parentFolder;
    }
    /**
     * @return The name of a {@code Folder}.
     * */
    public String getFolderName() {
        return folderName;
    }
    /**
     * @return A list of {@code CustomFile} within a {@code Folder}.
     * */
    public List<CustomFile> getFiles() {
        List<CustomFile> fileList = new ArrayList<>();
        for (int i = 0; i < files.getSize(); i++) {
            fileList.add(files.getElement(i));
        }
        return fileList;
    }
    /**
     * @return A list of subfolders within {@code Folder}.
     * */
    public List<Folder> getSubfolders() {
        List<Folder> folderList = new ArrayList<>();
        for (int i = 0; i < subfolders.getSize(); i++) {
            folderList.add(subfolders.getElement(i));
        }
        return folderList;
    }
    /**
     * @return The parent folder of a {@code Folder}.
     * */
    public Folder getParentFolder() {
        return parentFolder;
    }

    /**
     * @return A string representation of the full path of the {@code Folder}
     * */
    public String getFullPath() {
        if (parentFolder == null) {
            return folderName; // If no parent, return just the folder name
        }
        return parentFolder.getFullPath() + "/" + folderName; // Append folder name to parent's path
    }
    /**
     * Compares two {@code Folder} by their name.
     * @return 1 if the {@code CustomFile} is higher in order than the other {@code Folder}
     * <p>
     * -1 {@code CustomFile} if the {@code CustomFile} is lower in order than the other {@code Folder}
     * <p>
     * 0 if both{@code Folder} are the same.
     * */
    @Override
    public int compareTo(Folder other) {
        return this.folderName.compareTo(other.folderName);
    }
}

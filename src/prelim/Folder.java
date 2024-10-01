package prelim;

import java.util.Objects;
/**
 * TODO
 * */
public class Folder implements Comparable<Folder> {
    private String folderName;
    private LinkedList<Object> contents;
    /**
     * Default constructor for the Folder class.
     * */
    public Folder() {
        this.folderName = "";
        this.contents = null;
    }
    /**
     * Constructor for an empty Folder.
     *
     * @param folderName The name of the folder to be constructed.
     * */
    public Folder(String folderName) {
        this.folderName = folderName;
        this.contents = null;
    }
    /**
     * @return The name of a folder.
     * */
    public String getFolderName() {
        return folderName;
    }
    /**
     * @return The contents of a folder.
     * */
    public LinkedList<Object> getContents() {
        return contents;
    }
    /**
     * @return True/ False if two folders are the same.
     * */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Folder folder = (Folder) o;

        return Objects.equals(folderName, folder.folderName);
    }
    /**
     * Generates a hash code for a folder.
     * @return An integer to represent the hash code.
     * */
    @Override
    public int hashCode() {
        return folderName != null ? folderName.hashCode() : 0;
    }
    /**
     * Sets the name for a folder.
     * @param folderName The specified name of a folder.
     * */
    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
    /**
     * Set the contents within a folder.
     * @param contents The content to be placed within a folder.
     * */
    public void setContents(LinkedList<Object> contents) {
        this.contents = contents;
    }
    /**
     * @return A string representation for a folder.
     * */
    @Override
    public String toString() {
        return "Folder{" +
                "folderName='" + folderName + '\'' +
                ", contents=" + contents +
                '}';
    }
    /**
     * Compares two folders
     *
     * @param o the other folder to compare to.
     * @return An integer 1 if greater, -1 if less than, and 0 if equals.
     * */
    @Override
    public int compareTo(Folder o) {
        return 0; // Fix later
    }
}

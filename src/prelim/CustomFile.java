package prelim;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

/**
 * The {@code CustomFile} class represents a file with attributes such as the file name,
 * file extension, size, creation date, last modification date, content, existence status, and file path.
 * It supports operations like reading content from an existing file, creating new files,
 * and deleting files.
 */
public class CustomFile implements Comparable<CustomFile> {
    private String fileName;
    private String extension;
    private long size;
    private Date creationDate;
    private Date lastModifiedDate;
    private String content; // To hold the content of the file
    private boolean isExistingFile; // New field to indicate if the file is existing
    private String desktopPath;

    /**
     * Constructs a {@code CustomFile} object from an existing file on disk.
     *
     * @param filePath The path to the existing file.
     * @throws Exception if the file is not found or is invalid.
     */
    public CustomFile(String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("Invalid file: " + filePath);
        }
        this.fileName = file.getName();
        this.extension = getFileExtension(file);
        this.size = file.length();
        this.content = new String(Files.readAllBytes(file.toPath())); // Read file content
        this.desktopPath = filePath;

        BasicFileAttributes attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
        this.creationDate = new Date(attrs.creationTime().toMillis());
        this.lastModifiedDate = new Date(file.lastModified());
        this.isExistingFile = true; // Mark as an existing file
    }

    /**
     * Constructs a new {@code CustomFile} and writes the specified content to it.
     *
     * @param fileName The name of the new file.
     * @param extension The extension of the new file (e.g., ".txt").
     * @param content The content to write to the new file.
     * @throws IOException if there is an issue creating or writing to the file.
     */
    public CustomFile(String fileName, String extension, String content) throws IOException {
        this.fileName = fileName;
        this.extension = extension;
        this.content = content;
        // Create the file and write content
        File file = new File(fileName + extension);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
            this.size = content.length(); // Set size based on content length
        } catch (IOException e) {
            throw new IOException("Error creating file: " + e.getMessage());
        }
        // Set creation and last modified dates
        this.creationDate = new Date(); // Set current date as creation date
        this.lastModifiedDate = this.creationDate; // Last modified is also current date
        this.isExistingFile = false; // Mark as not an existing file
    }

    /**
     * Checks if this {@code CustomFile} exists on the disk.
     *
     * @return True if the file exists, otherwise false.
     */
    public boolean isExistingFile() {
        return isExistingFile;
    }

    /**
     * Gets the file extension from a specified {@code File}.
     *
     * @param file The file from which to retrieve the extension.
     * @return The file extension as a string.
     */
    private String getFileExtension(File file) {
        String name = file.getName();
        int lastDot = name.lastIndexOf('.');
        return (lastDot == -1) ? "" : name.substring(lastDot); // Return dot + extension
    }

    /**
     * Deletes the file from the disk.
     *
     * @return True if the file was successfully deleted, otherwise false.
     */
    public boolean deleteFile() {
        File file = new File(fileName + extension);
        boolean deleted = file.delete();
        if (deleted) {
            System.out.println("File deleted: " + fileName + extension);
            return true;
        } else {
            System.out.println("Error deleting file: " + fileName + extension);
            return false;
        }
    }

    // Setter methods

    /**
     * Sets the file name for this {@code CustomFile}.
     *
     * @param fileName The new file name.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Sets the file extension for this {@code CustomFile}.
     *
     * @param extension The new file extension.
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

    /**
     * Sets the content for this {@code CustomFile}.
     *
     * @param content The new file content.
     */
    public void setContent(String content) {
        this.content = content;
        this.lastModifiedDate = new Date(); // Update last modified date
    }

    /**
     * Sets the size of the file.
     *
     * @param size The new size of the file.
     */
    public void setSize(long size) {
        this.size = size;
    }

    /**
     * Sets the creation date of the file.
     *
     * @param creationDate The new creation date.
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Sets the last modified date of the file.
     *
     * @param lastModifiedDate The new last modified date.
     */
    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    /**
     * Sets whether this file is an existing file or not.
     *
     * @param existingFile True if the file exists, otherwise false.
     */
    public void setExistingFile(boolean existingFile) {
        this.isExistingFile = existingFile;
    }

    /**
     * Sets the path to the desktop for this file.
     *
     * @param desktopPath The new path to the desktop.
     */
    public void setDesktopPath(String desktopPath) {
        this.desktopPath = desktopPath;
    }

    // Getter methods

    /**
     * Gets the file name of this {@code CustomFile}.
     *
     * @return The file name.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Gets the file extension of this {@code CustomFile}.
     *
     * @return The file extension.
     */
    public String getExtension() {
        return extension;
    }

    /**
     * Gets the size of this {@code CustomFile}.
     *
     * @return The file size in bytes.
     */
    public long getSize() {
        return size;
    }

    /**
     * Gets the creation date of this {@code CustomFile}.
     *
     * @return The creation date.
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Gets the last modified date of this {@code CustomFile}.
     *
     * @return The last modified date.
     */
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * Gets the content of this {@code CustomFile}.
     *
     * @return The file content as a string.
     */
    public String getContent() {
        return content;
    }

    /**
     * Gets the desktop path of this {@code CustomFile}.
     *
     * @return The file path.
     */
    public String getDesktopPath() {
        return desktopPath;
    }

    // Comparable and toString methods

    /**
     * Compares two {@code CustomFile} objects based on their file names.
     *
     * @param otherFile The other {@code CustomFile} to compare.
     * @return A negative integer, zero, or a positive integer if this file name is less than,
     *         equal to, or greater than the other file name.
     */
    @Override
    public int compareTo(CustomFile otherFile) {
        return this.fileName.compareTo(otherFile.fileName);
    }

    /**
     * Returns a string representation of this {@code CustomFile} object.
     *
     * @return A string representation of the file, including its name, extension, size,
     *         creation date, last modified date, and optionally its content.
     */
    @Override
    public String toString() {
        return "CustomFile{" +
                "fileName='" + fileName + '\'' +
                ", extension='" + extension + '\'' +
                ", size=" + size +
                ", creationDate=" + creationDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", content='" + content + '\'' +
                '}';
    }
}
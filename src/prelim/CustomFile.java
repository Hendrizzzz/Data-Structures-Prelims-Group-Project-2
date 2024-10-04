package prelim;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
/**
 * Reference class for an object called {@code CustomFile} that is utilized by the {@code FileExplorerMain}.
 * Each custom file comprises various fields/ attributes such as the file name, file extension, size, creation date, last
 * modification date, file content, file existing status, and the file path.
 * */
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
     * Constructor that creates a {@code CustomFile} from an existing file.
     *
     * @param filePath The specified existing file.
     * */
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
     * Constructor for creating a {@code CustomFile} with the fields:
     *
     * @param fileName The name of the file.
     * @param extension The extension of the file.
     * @param content The content within the file.
     * */
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
     * Method used to check if the file is an existing {@code CustomFile}.
     * @return True if the {@code CustomFile} exists/ False if not.
     * */
    public boolean isExistingFile() {
        return isExistingFile;
    }
    /**
     * @return The file extension of a specified {@code File}
     * */
    private String getFileExtension(File file) {
        String name = file.getName();
        int lastDot = name.lastIndexOf('.');
        return (lastDot == -1) ? "" : name.substring(lastDot); // Return dot + extension
    }
    /**
     * Method that removes a {@code File}.
     * @return True if the file was deleted/ False if not.
     * */
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
    /**
     * Sets the name of the {@code CustomFile}.
     * */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    /**
     * Sets the extension of the {@code CustomFile}.
     * */
    public void setExtension(String extension) {
        this.extension = extension;
    }
    /**
     * Sets the content of the {@code CustomFile}.
     * */
    public void setContent(String content) {
        this.content = content;
        this.lastModifiedDate = new Date(); // Update last modified date
    }
    /**
     * Sets the size of the {@code CustomFile}.
     * */
    public void setSize(long size) {
        this.size = size;
    }
    /**
     * Sets the creation date of the {@code CustomFile}.
     * */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    /**
     * Sets the date of the last modification done to a {@code CustomFile}.
     * */
    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
    /**
     * Sets if the {@code CustomFile} is existing or not.
     * */
    public void setExistingFile(boolean existingFile) {
        isExistingFile = existingFile;
    }
    /**
     * Sets the path of the {@code CustomFile}/
     * */
    public void setDesktopPath(String desktopPath) {
        this.desktopPath = desktopPath;
    }
    /**
     * @return The name of a {@code CustomFile}.
     * */
    public String getFileName() {
        return fileName;
    }
    /**
     * @return The extension of a {@code CustomFile}.
     * */
    public String getExtension() {
        return extension;
    }
    /**
     * @return The size of a {@code CustomFile}.
     * */
    public long getSize() {
        return size;
    }
    /**
     * @return The creation date of a {@code CustomFile}.
     * */
    public Date getCreationDate() {
        return creationDate;
    }
    /**
     * @return The date of the last modification to a {@code CustomFile}.
     * */
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }
    /**
     * @return The content of a {@code CustomFile}.
     * */
    public String getContent() {
        return content;
    }
    /**
     * @return The path of a {@code CustomFile}.
     * */
    public String getDesktopPath() {
        return desktopPath;
    }
    /**
     * Compares two {@code CustomFiles} based on their names
     * @return 1 if the {@code CustomFile} is higher in order than the other {@code CustomFile}
     * <p>
     * -1 {@code CustomFile} if the {@code CustomFile} is lower in order than the other {@code CustomFile}
     * <p>
     * 0 if both{@code CustomFiles} are the same.
     * */
    @Override
    public int compareTo(CustomFile otherFile) {
        return this.fileName.compareTo(otherFile.fileName);
    }
    /**
     * @return A string representation of a CustomFile.
     * */
    @Override
    public String toString() {
        return "CustomFile{" +
                "fileName='" + fileName + '\'' +
                ", extension='" + extension + '\'' +
                ", size=" + size +
                ", creationDate=" + creationDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", content='" + content + '\'' + // Displaying content is optional, remove if necessary
                '}';
    }
}
package prelim;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

public class CustomFile implements Comparable<CustomFile> {
    private String fileName;
    private String extension;
    private long size;
    private Date creationDate;
    private Date lastModifiedDate;
    private String content; // To hold the content of the file

    // Constructor to create a CustomFile from an existing file
    public CustomFile(String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("Invalid file: " + filePath);
        }

        this.fileName = file.getName();
        this.extension = getFileExtension(file);
        this.size = file.length();
        this.content = new String(Files.readAllBytes(file.toPath())); // Read file content

        BasicFileAttributes attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
        this.creationDate = new Date(attrs.creationTime().toMillis());
        this.lastModifiedDate = new Date(file.lastModified());
    }

    // New constructor for creating a CustomFile with filename, extension, and content
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
    }

    // Method to get the file extension
    private String getFileExtension(File file) {
        String name = file.getName();
        int lastDot = name.lastIndexOf('.');
        return (lastDot == -1) ? "" : name.substring(lastDot); // Return dot + extension
    }

    // Method to delete the file
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

    // Getter and Setter methods
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public long getSize() {
        return size;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        this.lastModifiedDate = new Date(); // Update last modified date
    }

    // compareTo method (compare by file name)
    @Override
    public int compareTo(CustomFile otherFile) {
        return this.fileName.compareTo(otherFile.fileName);
    }

    // toString for displaying file info
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
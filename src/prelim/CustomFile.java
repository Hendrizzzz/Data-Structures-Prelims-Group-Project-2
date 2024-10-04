package prelim;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

public class CustomFile implements Comparable<CustomFile> {
    private String fileName;
    private String extension;
    private long size;
    private Date creationDate;
    private Date lastModifiedDate;

    // Constructor
    public CustomFile(String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("Invalid file: " + filePath);
        }

        this.fileName = file.getName();
        this.extension = getFileExtension(file);
        this.size = file.length();

        BasicFileAttributes attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
        this.creationDate = new Date(attrs.creationTime().toMillis());
        this.lastModifiedDate = new Date(file.lastModified());
    }

    // Method to get the file extension
    private String getFileExtension(File file) {
        String name = file.getName();
        int lastDot = name.lastIndexOf('.');
        return (lastDot == -1) ? "" : name.substring(lastDot + 1);
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

    public void setSize(long size) {
        this.size = size;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    // compareTo method (compare by file name, could be extended to compare by size or dates)
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
                '}';
    }
}
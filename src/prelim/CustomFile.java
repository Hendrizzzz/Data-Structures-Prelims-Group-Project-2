package prelim;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomFile {
    private String fileName;
    private String extension;
    private long size;
    private Date lastModified;
    private Date creationDate;
    private String filePath;

    // Constructor
    public CustomFile(String filePath) throws Exception {
        File file = new File(filePath);

        if (!file.exists()) {
            throw new IllegalArgumentException("File does not exist: " + filePath);
        }

        this.fileName = file.getName();
        this.extension = getFileExtension(file);
        this.size = file.length();
        this.lastModified = new Date(file.lastModified());
        this.filePath = file.getAbsolutePath();
        this.creationDate = getCreationDate(file.toPath());
    }

    // Helper method to extract file extension
    private String getFileExtension(File file) {
        String name = file.getName();
        int lastIndex = name.lastIndexOf('.');
        return (lastIndex > 0) ? name.substring(lastIndex + 1) : "";
    }

    // Helper method to get creation date
    private Date getCreationDate(Path filePath) throws Exception {
        BasicFileAttributes attrs = Files.readAttributes(filePath, BasicFileAttributes.class);
        return new Date(attrs.creationTime().toMillis());
    }

    // Getters
    public String getFileName() {
        return fileName;
    }

    public String getExtension() {
        return extension;
    }

    public long getSize() {
        return size;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getFilePath() {
        return filePath;
    }

    // String representation for displaying file info
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return String.format(
                "File: %s\nExtension: %s\nSize: %d bytes\nLast Modified: %s\nCreation Date: %s\nPath: %s",
                fileName, extension, size, dateFormat.format(lastModified), dateFormat.format(creationDate), filePath
        );
    }
}

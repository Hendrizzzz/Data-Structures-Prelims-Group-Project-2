package prelim;

import java.util.Objects;
/**
 * TODO
 * */
public class File implements Comparable<File> {
    private String fileName;
    private String extension;
    private int size; // or double
    /**
     * Default constructor for the File class.
     * */
    public File() {
        this.fileName = "";
        this.extension = "";
        this.size = 0;
    }
    /**
     * Constructor for a file with specified name, extension, and size
     * @param fileName Name of the file.
     * @param extension Extension of a file (i.e. exe, txt, csv, etc...).
     * @param size The size of a file.
     * */
    public File(String fileName, String extension, int size) {
        this.fileName = fileName;
        this.extension = extension;
        this.size = size;
    }
    /**
     * Constructor for a file with specified name and extension only.
     * @param fileName Name of the file.
     * @param extension Extension of a file (i.e. exe, txt, csv, etc...).
     * */
    public File(String fileName, String extension) {
        this.fileName = fileName;
        this.extension = extension;
    }
    /**
     * @return The name of a file.
     * */
    public String getFileName() {
        return fileName;
    }
    /**
     * @return The extension of a file.
     * */
    public String getExtension() {
        return extension;
    }
    /**
     * @return The size of a file.
     * */
    public int getSize() {
        return size;
    }
    /**
     * Sets the name of a file.
     * @param fileName The specified name of a file.
     * */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    /**
     * Sets the extension of a file.
     * @param extension The specified extension of a file.
     * */
    public void setExtension(String extension) {
        this.extension = extension;
    }
    /**
     * Sets the size of file.
     * @param size The specified size of a file.
     */
    public void setSize(int size) {
        this.size = size;
    }
    /**
     * @return A string representation of a file.
     * */
    @Override
    public String toString() {
        return "File{" +
                "fileName='" + fileName + '\'' +
                ", extension='" + extension + '\'' +
                ", size=" + size +
                '}';
    }
    /**
     * @return True/ False if two files are the same.
     * */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        File file = (File) o;

        if (!Objects.equals(fileName, file.fileName)) return false;
        return Objects.equals(extension, file.extension);
    }
    /**
     * Generates a hash code for a file.
     * @return An integer to represent the hash code.
     * */
    @Override
    public int hashCode() {
        int result = fileName != null ? fileName.hashCode() : 0;
        result = 31 * result + (extension != null ? extension.hashCode() : 0);
        return result;
    }
    /**
     * Compares two Files.
     * @return An integer representing 1 if the File is greater, -1 if lesser, and 0 if equals.
     * */
    @Override
    public int compareTo(File o) {
        return 0;
    }
}

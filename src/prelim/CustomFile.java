package prelim;

import java.util.Date;
import java.util.Objects;

public class CustomFile extends FileSystemEntity implements Comparable<CustomFile> {
    private String extension;

    /**
     * Default constructor for the CustomFile class.
     * */
    public CustomFile() {

    }


    /**
     * Constructor for a file with specified name, extension, and size
     * @param fileName Name of the file.
     * @param extension Extension of a file (i.e. exe, txt, csv, etc...).
     * @param size The size of a file.
     * */
    public CustomFile(String fileName, String extension, int size, Date creationDate) {
        super (fileName, creationDate);
        this.extension = extension;
        this.setSize(size);
    }


    /**
     * Constructor for a file with specified name and extension only.
     * @param fileName Name of the file.
     * @param extension Extension of a file (i.e. exe, txt, csv, etc...).
     * */
    public CustomFile(String fileName, String extension, Date creationDate) {
        super(fileName, creationDate);
        this.extension = extension;
    }

    public CustomFile(String fileName, String extension) {
        setName(fileName);
        this.extension = extension;
    }


    /**
     * @return The extension of a file.
     * */
    public String getExtension() {
        return extension;
    }

    /**
     * Sets the extension of a file.
     * @param extension The specified extension of a file.
     * */
    public void setExtension(String extension) {
        this.extension = extension;
    }


    /**
     * @return The size of a file.
     * */
    @Override
    public int getSize() {
        return super.getSize();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomFile customFile = (CustomFile) o;

        return Objects.equals(getName(), customFile.getName()) &&
                Objects.equals(extension, customFile.extension);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), extension);
    }


    /**
     * @return A string representation of a file.
     * */
    @Override
    public String toString() {
        return getName() + extension;
    }




    /**
     * Compares two Files.
     * @return An integer representing 1 if the CustomFile is greater, -1 if lesser, and 0 if equals.
     * */
    @Override
    public int compareTo(CustomFile o) {
        return 0;
    }
}
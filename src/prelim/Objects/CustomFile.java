package prelim.Objects;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Objects;

public class CustomFile extends FileSystemEntity implements Comparable<CustomFile> {
    private String extension;
    private String contents;
    private String desktopPath;


    /**
     * Default constructor for the CustomFile class.
     * */
    public CustomFile() {

    }


    /**
     * Constructor for a file with specified name, extension, and size
     * @param fileName Name of the file.
     * @param extension Extension of a file (i.e. exe, txt, csv, etc...).
     * @param contents the contents of the file
     * */
    public CustomFile(String fileName, String extension, String contents) {
        super (fileName, new Date());
        this.extension = extension;
        this.contents = contents;
        setSize(contents);
    }

    /**
     * Constructor for a file with specified name, extension, and size
     * @param fileName Name of the file.
     * @param extension Extension of a file (i.e. exe, txt, csv, etc...).
     * @param contents the contents of the file
     * */
    public CustomFile(String fileName, String extension, String contents, String desktopPath) {
        super (fileName, new Date());
        this.extension = extension;
        this.contents = contents;
        this.desktopPath = desktopPath;
        setSize(contents);
    }


    /**
     * Constructor for a file with specified name and extension only.
     * @param fileName Name of the file.
     * @param extension Extension of a file (i.e. exe, txt, csv, etc...).
     * */
    public CustomFile(String fileName, String extension) {
        super(fileName, new Date());
        this.extension = extension;
        setSize(0);
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


    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
        setModificationDate(new Date());
        setSize(contents);
    }

    public String getDesktopPath() {
        return desktopPath;
    }


    /**
     * @return The size of a file.
     * */
    @Override
    public int getSize() {
        return super.getSize();
    }

    // Method to read contents from file, can only read .txt and garbage contents for the others (i.e images and pdf)
    public static String readFileContents(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    private void setSize(String contents) {
        this.setSize(contents.getBytes().length);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomFile file = (CustomFile) o;

        return Objects.equals(getName(), file.getName()) &&
                Objects.equals(extension, file.extension);
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
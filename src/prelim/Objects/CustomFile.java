package prelim.Objects;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Pattern;

import prelim.Exceptions.InvalidFileEntityNameException;
import prelim.Exceptions.InvalidFileExtensionException;

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
     * @throws InvalidFileExtensionException when the extension passed is not valid
     * @throws InvalidFileEntityNameException when the fileName passed contains a backslash
     * */
    public CustomFile(String fileName, String extension, String contents) {
        super (fileName, new Date());
        setExtension(extension);
        this.contents = contents;
        setSize(contents);
    }


    /**
     * Constructor for a file with specified name, extension, and size
     * @param fileName Name of the file.
     * @param extension Extension of a file (i.e. exe, txt, csv, etc...).
     * @param contents the contents of the file
     * @throws InvalidFileExtensionException when the extension passed is not valid
     * @throws InvalidFileEntityNameException when the fileName passed contains a backslash
     * */
    public CustomFile(String fileName, String extension, String contents, String desktopPath) {
        super (fileName, new Date());
        setExtension(extension);
        this.contents = contents;
        this.desktopPath = desktopPath;
        setSize(contents);
    }


    /**
     * Constructor for a file with specified name and extension only.
     * @param fileName Name of the file.
     * @param extension Extension of a file (i.e. exe, txt, csv, etc...).
     * @throws InvalidFileEntityNameException when the fileName passed contains a backslash
     * */
    public CustomFile(String fileName, String extension) {
        super(fileName, new Date());
        setExtension(extension);
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
     * @throws InvalidFileExtensionException when the file extension is not valid
     * */
    private void setExtension(String extension) {
        if (!isValidExtension(extension))
            throw new InvalidFileExtensionException("Invalid file extension. A valid extension must start with a dot and contain 2 to 10 alphanumeric characters.");
        this.extension = extension;
    }

    private boolean isValidExtension(String extension) {
        String regex = "^\\.[a-zA-Z0-9]{2,10}$";

        // Compile the pattern
        Pattern pattern = Pattern.compile(regex);

        // Check if the extension matches the pattern
        return pattern.matcher(extension).matches();
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
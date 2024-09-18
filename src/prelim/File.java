package prelim;

import java.util.Objects;

public class File implements Comparable<File> {
    private String fileName;
    private String extension;
    private int size; // or double


    public File() {
        this.fileName = "";
        this.extension = "";
        this.size = 0;
    }

    public File(String fileName, String extension, int size) {
        this.fileName = fileName;
        this.extension = extension;
        this.size = size;
    }


    public File(String fileName, String extension) {
        this.fileName = fileName;
        this.extension = extension;
    }

    public String getFileName() {
        return fileName;
    }

    public String getExtension() {
        return extension;
    }

    public int getSize() {
        return size;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "File{" +
                "fileName='" + fileName + '\'' +
                ", extension='" + extension + '\'' +
                ", size=" + size +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        File file = (File) o;

        if (!Objects.equals(fileName, file.fileName)) return false;
        return Objects.equals(extension, file.extension);
    }

    @Override
    public int hashCode() {
        int result = fileName != null ? fileName.hashCode() : 0;
        result = 31 * result + (extension != null ? extension.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(File o) {
        return 0;
    }
}

package prelim;

public class File<T> implements Comparable<T> {
    private String fileName;
    private String extension;
    private int size;

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
    public int compareTo(T o) {
        return 0;
    }
}

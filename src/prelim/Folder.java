package prelim;

public class Folder<T> implements Comparable<T> {
    private String folderName;
    private LinkedList<File> files;

    public Folder() {
        this.folderName = "";
        this.files = null;
    }

    public Folder(String folderName) {
        this.folderName = folderName;
        this.files = null;
    }

    public String getFolderName() {
        return folderName;
    }

    public LinkedList<File> getFiles() {
        return files;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public void setFiles(LinkedList<File> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "folderName='" + folderName + '\'' +
                ", files=" + files +
                '}';
    }


    @Override
    public int compareTo(T o) {
        return 0; // Fix later
    }
}

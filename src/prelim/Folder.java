package prelim;

import java.util.Objects;

public class Folder implements Comparable<Folder> {
    private String folderName;
    private LinkedList<Object> contents;

    public Folder() {
        this.folderName = "";
        this.contents = null;
    }

    public Folder(String folderName) {
        this.folderName = folderName;
        this.contents = null;
    }

    public String getFolderName() {
        return folderName;
    }

    public LinkedList<Object> getContents() {
        return contents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Folder folder = (Folder) o;

        return Objects.equals(folderName, folder.folderName);
    }

    @Override
    public int hashCode() {
        return folderName != null ? folderName.hashCode() : 0;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public void setContents(LinkedList<Object> contents) {
        this.contents = contents;
    }



    @Override
    public String toString() {
        return "Folder{" +
                "folderName='" + folderName + '\'' +
                ", contents=" + contents +
                '}';
    }


    @Override
    public int compareTo(Folder o) {
        return 0; // Fix later
    }
}

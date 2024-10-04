package prelim;

import java.util.ArrayList;
import java.util.List;

public class Folder implements Comparable<Folder> {
    private String folderName;
    private MyDoublyLinkedCircularList<CustomFile> files;
    private MyDoublyLinkedCircularList<Folder> subfolders;

    public Folder(String folderName) {
        this.folderName = folderName;
        this.files = new MyDoublyLinkedCircularList<>();
        this.subfolders = new MyDoublyLinkedCircularList<>();
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public void addFile(CustomFile file) throws ListOverflowException {
        files.insert(file);
    }

    public void addSubfolder(Folder folder) throws ListOverflowException {
        subfolders.insert(folder);
    }

    public List<CustomFile> getFiles() {
        List<CustomFile> fileList = new ArrayList<>();
        for (int i = 0; i < files.getSize(); i++) {
            fileList.add(files.getElement(i));
        }
        return fileList;
    }

    public List<Folder> getSubfolders() {
        List<Folder> folderList = new ArrayList<>();
        for (int i = 0; i < subfolders.getSize(); i++) {
            folderList.add(subfolders.getElement(i));
        }
        return folderList;
    }

    @Override
    public int compareTo(Folder other) {
        return this.folderName.compareTo(other.folderName);
    }
}
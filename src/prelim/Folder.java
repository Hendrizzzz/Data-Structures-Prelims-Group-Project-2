package prelim;

import java.io.File;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class Folder {
    private String folderName;
    private String folderPath;
    private ArrayList<CustomFile> files;   // Dynamic array of files in the folder
    private MyDoublyLinkedCircularList<Folder> subfolders;  // Circular list of subfolders

    // Constructor
    public Folder(String folderPath) throws Exception {
        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory()) {
            throw new IllegalArgumentException("Folder does not exist or is not a directory: " + folderPath);
        }

        this.folderName = folder.getName();
        this.folderPath = folder.getAbsolutePath();
        this.files = new ArrayList<>();
        this.subfolders = new MyDoublyLinkedCircularList<>();

        // Load files into the folder
        loadFiles(folder);
    }

    // Method to load files into the folder's dynamic array
    private void loadFiles(File folder) throws Exception {
        File[] allContents = folder.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                if (file.isFile()) {
                    files.add(new CustomFile(file.getAbsolutePath()));
                }
            }
        }
    }

    // Add a subfolder to the folder (using your custom circular linked list)
    public void addSubfolder(Folder subfolder) throws ListOverflowException {
        subfolders.insert(subfolder);
    }

    // Traverse through the subfolders in a circular manner (forward)
    public void traverseSubfoldersForward() {
        if (subfolders.getSize() == 0) {
            System.out.println("No subfolders.");
            return;
        }

        for (int i = 0; i < subfolders.getSize(); i++) {
            System.out.println("Subfolder: " + subfolders.getElement(i).getFolderName());
        }
    }

    // Traverse through the subfolders in a circular manner (backward)
    public void traverseSubfoldersBackward() {
        if (subfolders.getSize() == 0) {
            System.out.println("No subfolders.");
            return;
        }

        for (int i = subfolders.getSize() - 1; i >= 0; i--) {
            System.out.println("Subfolder: " + subfolders.getElement(i).getFolderName());
        }
    }

    // Getters
    public String getFolderName() {
        return folderName;
    }

    public ArrayList<CustomFile> getFiles() {
        return files;
    }

    public int getSubfolderCount() {
        return subfolders.getSize();
    }

    public String getFolderPath() {
        return folderPath;
    }

    // String representation for displaying folder info
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Folder: ").append(folderName).append("\n");
        sb.append("Files:\n");
        for (CustomFile file : files) {
            sb.append(" - ").append(file.getFileName()).append("\n");
        }
        sb.append("Number of Subfolders: ").append(subfolders.getSize()).append("\n");
        return sb.toString();
    }
}

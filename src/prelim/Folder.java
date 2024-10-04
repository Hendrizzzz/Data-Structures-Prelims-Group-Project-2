package prelim;

import java.io.File;
import java.util.ArrayList;

public class Folder implements Comparable<Folder> {
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

    // CRUD Setters
    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public void setFiles(ArrayList<CustomFile> files) {
        this.files = files;
    }

    public void setSubfolders(MyDoublyLinkedCircularList<Folder> subfolders) {
        this.subfolders = subfolders;
    }

    // Getters
    public String getFolderName() {
        return folderName;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public ArrayList<CustomFile> getFiles() {
        return files;
    }


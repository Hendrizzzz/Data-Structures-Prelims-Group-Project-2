package prelim;

/**
 * Class that facilitates operation; creation, deletion, etc. of files or folders
 */
public class FileManager {
    private String currentDirectory;
    private prelim.LinkedList<prelim.LinkedList<Object>> fileStructure;

    prelim.LinkedList<Object> documents;
    prelim.LinkedList<Object> pictures;
    prelim.LinkedList<Object> music;
    prelim.LinkedList<Object> videos;
    prelim.LinkedList<Object> downloads;


    {
        currentDirectory = "source";
    }


    /**
     * Default Constructor; chains it with the parameterized constructor
     */
    public FileManager() {
        this(new prelim.LinkedList<prelim.LinkedList<Object>>());
    }

    /**
     * Constructor with arguments, declares values on datafields
     * @param fileStructure the fileStructure
     */
    public FileManager(prelim.LinkedList<prelim.LinkedList<Object>> fileStructure) {
        this.fileStructure = fileStructure;
        this.fileStructure.insert(documents = new prelim.LinkedList<>());
        this.fileStructure.insert(pictures = new prelim.LinkedList<>());
        this.fileStructure.insert(music = new prelim.LinkedList<>());
        this.fileStructure.insert(videos = new prelim.LinkedList<>());
        this.fileStructure.insert(downloads = new LinkedList<>());
    }


    public String getDirectory() {
        return currentDirectory;
    }

    public void setDirectory(String currentDirectory) {
        this.currentDirectory = currentDirectory;
    }


    /**
     * SIMILAR TO LS COMMAND IN BASH.
     * Gives the files and folders under the current directory in a type singlylinkedlist.
     *
     * Usage in GUI (Example)
     *
     * Object contents = fileManager.getContents();
     *
     * if (contents == null) {
     *     then the current directory is dead end, no files or folder yet
     * }
     *
     * else if (content instanceof Folder) {
     *     // Then this is a folder
     * }
     *
     * else if (content instanceof LinkedList<?>) {
     *     // Check if it's a LinkedList of LinkedLists
     *     LinkedList<?> list = (LinkedList<?>) content;
     *
     *     // If the first element is also a LinkedList, it's a list of lists
     *     if (!list.isEmpty() && list.getElement(0) instanceof LinkedList<?>) {
                // THEN WE ARE ON THE SOURCE ROOT
     *     } else {
     *         // WE ARE ON EITHER DOCUMENTS, DOWNLOADS, MUSIC, VIDEOS, PICTURES, ETC
     *     }*
     * }
     *
     * @return
     */
    public Object getContents() {
        if (this.currentDirectory.equals("source"))
            return fileStructure;  // Return immediately

        String[] dir = currentDirectory.split("/");
        LinkedList<Object> toReturn = getDirectoryContents(dir[1]);

        // Traverse, well if it's just "source/documents" or any same level, this for loop won't excute
        for (int i = 2; i < dir.length; i++) {
            int index = toReturn.search(dir[i]);
            if (index != -1)
                toReturn = ((Folder) toReturn.getElement(index)).getContents();
            else
                return null; // This should not execute, For CLI-debugging purposes
        }

        return toReturn;
    }

    // TODO: add more
    private LinkedList<Object> getDirectoryContents(String dirType) {
        return switch (dirType) {
            case "documents" -> documents;
            case "pictures" -> pictures;
            case "music" -> music;
            case "videos" -> videos;
            default -> downloads;
        };
    }


    // This assumes that you can't create a folder nor a file in the source directory
    public boolean createFile(String fileName, String extension, int size) {
        File newFile = new File(fileName, extension, size);
        LinkedList<Object> folderList = (LinkedList<Object>) getContents();
        int index = folderList.search(newFile); // return the index of the file or -1 if it does not exist

        // Add it to the current directory
        if (index == -1) {
            folderList.insert(newFile);
            return true;
        }
        return false; // The file already exists. Failed to create a new File.
    }

    // This assumes that you can't create a folder nor a file in the source directory
    public boolean createFolder(String folderName) {
        Folder newFolder = new Folder(folderName);
        LinkedList<Object> folderList = (LinkedList<Object>) getContents();
        int index = folderList.search(newFolder); // returns the index of the folder or -1 if it does not exist

        // Add it to the current directory
        if (index == -1) {
            folderList.insert(newFolder);
            return true;
        }
        return false; // The folder already exists.
    }


    public void deleteFile(String fileName, String extension) {
        LinkedList<Object> folderList = (LinkedList<Object>) getContents();
        if (folderList.delete(new File(fileName, extension)))
            System.out.println(folderList + " is deleted");
        else
            System.out.println(folderList + " is not deleted"); // For CLI debugging
    }


    public void deleteFolder(String folderName) {
        LinkedList<Object> folderList = (LinkedList<Object>) getContents();
        if (folderList.delete(new Folder(folderName)))
            System.out.println(folderName + " is deleted");
        else
            System.out.println(folderName + " is not deleted"); // For CLI debugging
    }


    // May be changed depending on the design
    // whether the extension of the file is modifiable upon creation
    // I recommend adding more file datafields if this method is different from renaming the file
    public void updateFile(String oldFileName, String oldExtension, String newFileName, String newExtension, int size) {
        File oldFile = new File(oldFileName, oldExtension);
        File updatedFile = new File(newFileName, newExtension, size);
        LinkedList<Object> folderList = (LinkedList<Object>) getContents();
        folderList.delete(oldFile);
        folderList.insert(updatedFile);
    }

    public void renameFile(String oldName, String extension, String newName) {
         File oldFile = new File(oldName, extension);
        LinkedList<Object> folderList = (LinkedList<Object>) getContents();
        int index = folderList.search(oldFile);
        if (index != -1) {
            File fileToRename = (File) folderList.getElement(index);
            fileToRename.setFileName(newName);
            System.out.println("File '" + oldName + "' renamed to '" + newName + "'");
        } else {
            System.out.println("File '" + oldName + "' not found");
        }
    }


    // TODO: make the File and Folder have a super class
    public MyGrowingArrayList<File> searchFile(String filename) {
        MyGrowingArrayList<File> foundFiles = new MyGrowingArrayList<>();
        searchFileRecursion(filename, foundFiles, (LinkedList<Object>) getContents());
        return foundFiles;
    }

    // Depth-first search traversion
    private void searchFileRecursion(String filenameToSearch, MyGrowingArrayList<File> filesFound, LinkedList<Object> currentContents) {
        for (int i = 0; i < currentContents.getSize(); i++) {
            Object currentFileFolder = currentContents.getElement(i);

            if (currentFileFolder instanceof File file && file.getFileName().contains(filenameToSearch))
                filesFound.insert(file);
            else if (currentFileFolder instanceof Folder folder)
                searchFileRecursion(filenameToSearch, filesFound, folder.getContents());
        }
    }


}

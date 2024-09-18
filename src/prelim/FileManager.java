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
        if (this.currentDirectory.equals("source")) {
            return fileStructure;  // Return immediately
        }

        String[] dir = currentDirectory.split("/");

        Object toReturn = switch (dir[1]) {
            case "documents" -> documents;
            case "pictures" -> pictures;
            case "music" -> music;
            case "videos" -> videos;
            default -> downloads;
        };

        // Traverse, well if it's just "source/documents", this for loop won't excute
        for (int i = 2; i < dir.length; i++) {
            if (toReturn instanceof LinkedList<?>) { // if it's on the
                LinkedList<Object> folderList = (LinkedList<Object>) toReturn;
                int index = folderList.search(dir[i]);
                toReturn = folderList.getElement(index);
            }
            else if (toReturn instanceof Folder folder)
                toReturn = folder.getContents();
        }

        return toReturn;
    }

    // This assumes that you can't create a folder nor a file in the source directory
    public boolean createFile(String fileName, String extension, int size) {
        Object content = getContents();
        File newFile = new File(fileName, extension);

        // Check if type Folder, and if the fileName and extension don't exist yet in that directory
        if (content instanceof Folder && ((Folder) content).getContents().search(newFile) == -1) {
            newFile.setSize(size);
            ((Folder) content).getContents().insert(newFile);
            return true;
        }
        // Check if type LinkedList (Documents, Downloads, etc.),
        // and if the fileName and extension don't exist yet in that directory
        else if (content instanceof LinkedList<?> && ((LinkedList<Object>) content).search(newFile) == -1) {
            newFile.setSize(size);
            ((LinkedList<Object>) content).insert(newFile);
            return true;
        }
        else
            return false; // there is a duplicate file in the same level / directory
    }

    // This assumes that you can't create a folder nor a file in the source directory
    public boolean createFolder(String folderName) {
        Object content = getContents();
        Folder newFolder = new Folder(folderName);

        // Check if type Folder, and if the folderName don't exist yet in that directory
        if (content instanceof Folder && ((Folder) content).getContents().search(newFolder) == -1) {
            ((Folder) content).getContents().insert(newFolder);
            return true;
        }
        // Check if type LinkedList (Documents, Downloads, etc.),
        // and if the folderName and extension don't exist yet in that directory
        else if (content instanceof LinkedList<?> && ((LinkedList<Object>) content).search(newFolder) == -1) {
            ((LinkedList<Object>) content).insert(newFolder);
            return true;
        }
        else
            return false; // there is a duplicate folder in the same level / directory
    }




}

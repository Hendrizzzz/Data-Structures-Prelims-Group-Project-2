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
        currentDirectory = "source/";
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
     *     }
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
                int index;

                if ((index = folderList.search(dir[i])) != -1)
                    toReturn = folderList.getElement(index);
                else
                    return null;

            }
            else if (toReturn instanceof Folder folder)
                toReturn = folder.getContents();

            // This works if in the GUI, you click a file, the path will change to 'currentDirectory/fileName'
            // if not then remove this else if block
            else if (toReturn instanceof File)
                return toReturn;
        }

        return toReturn;
    }


}
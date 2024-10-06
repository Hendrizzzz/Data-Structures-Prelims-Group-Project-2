package prelim.Objects;

import prelim.DataStructures.LinkedList;
import prelim.DataStructures.MyGrowingArrayList;
import prelim.Exceptions.ListEmptyException;
import prelim.Exceptions.NoSuchElementException;

import java.util.Date;
import java.util.Objects;


public class Folder extends FileSystemEntity implements Comparable<Folder> {
    private final LinkedList<FileSystemEntity> subContents;


    public Folder() {
        super("New Folder", new Date());
        this.subContents = new LinkedList<>();
    }

    public Folder(String name) {
        super (name, new Date());
        this.subContents = new LinkedList<>();
    }


    public Folder(String name, LinkedList<FileSystemEntity> subContents) {
        super(name, new Date());
        this.subContents = subContents;

        // Get the sum of all the files' sizes
        int size = 0;
        for (int i = 0; i < subContents.getSize(); i++)
            size += subContents.getElement(i).getSize();

        this.setSize(size);
    }



    public LinkedList<FileSystemEntity> getSubContents() {
        return subContents;
    }


    /**
     * Adds a file to this Folder
     * @param file the file to be added in this Folder.
     */
    public void addFile(CustomFile file) {
        addFileSystemEntity(file);
        setModificationDate(new Date());

        // Update size
        int initialSize = getSize();
        initialSize += file.getSize();
        setSize(initialSize);
    }

    /**
     * Adds a folder to this Folder
     * @param folder the folder to be added in this Folder.
     */
    public void addFolder(Folder folder) {
        addFileSystemEntity(folder);
        setModificationDate(new Date());

        int initialSize = getSize();
        initialSize += folder.getSize();
        setSize(initialSize);
    }

    private void addFileSystemEntity(FileSystemEntity newEntity) { // modify if the file has a datafield of contents too
        // verify if the name of the newFile doesn't exist in the folder yet
        int fileNameAppearance = 0;
        for (int i = 0; i < subContents.getSize(); i++)
            if (isSameEntity(subContents.getElement(i), newEntity))
                fileNameAppearance++;

        // Update the fileName if there exists a fileName already
        if (fileNameAppearance != 0)
            newEntity.setName(getNameWithDuplicateIdentifier(newEntity, fileNameAppearance + 1)); // repeatedName4Times (4).pdf

        subContents.insert(newEntity);
    }

    private boolean isSameEntity(FileSystemEntity entity, FileSystemEntity newEntity) {
        return entity.equals(newEntity);
    }

    private String getNameWithDuplicateIdentifier(FileSystemEntity newFile, int fileNameAppearance) {
        return newFile.getName() + " (" + fileNameAppearance + ")";
    }


    /**
     * Deletes a file in this Folder
     * @param file the file to be deleted.
     * @throws ListEmptyException when this Folder is empty.
     */
    public void removeFile(CustomFile file) throws ListEmptyException {
        removeFileSystemEntity(file);

        int initialSize = getSize();
        initialSize -= file.getSize();
        setSize(initialSize);
    }

    /**
     * Deletes a Folder in this Folder.
     * @param folder the folder to be deleted.
     * @throws ListEmptyException when this Folder is empty.
     */
    public void removeFolder(Folder folder) throws ListEmptyException {
        removeFileSystemEntity(folder);

        int initialSize = getSize();
        initialSize -= folder.getSize();
        setSize(initialSize);
    }

    private void removeFileSystemEntity(FileSystemEntity fileSystemEntity) throws ListEmptyException {
        if (subContents.getSize() == 0)
            throw new ListEmptyException("The folder is empty. ");

        for (int i = 0; i < subContents.getSize(); i++)
            if (isSameEntity(subContents.getElement(i), fileSystemEntity)) {
                subContents.deleteAtIndex(i);
                setModificationDate(new Date());
                return; // stop the traversing now
            }

        throw new prelim.Exceptions.NoSuchElementException("File / Folder not found. Cannot delete. ");
    }


    public void renameAFileInsideThisFolder(CustomFile oldFile, String newName) {
        for (int i = 0; i < subContents.getSize(); i++) {
            FileSystemEntity currentEntity = subContents.getElement(i);
            if (isSameEntity(currentEntity, oldFile)) {
                CustomFile fileToUpdate = (CustomFile) currentEntity;
                fileToUpdate.setName(newName);
                return;
            }
        }

        throw new NoSuchElementException("File not found, ");
    }

    public void renameAFolderInsideThisFolder(Folder oldFolder, String newName) {
        for (int i = 0; i < subContents.getSize(); i++) {
            FileSystemEntity currentEntity = subContents.getElement(i);
            if (isSameEntity(currentEntity, oldFolder)) {
                Folder folderToUpdate = (Folder) currentEntity;
                folderToUpdate.setName(newName);
                return;
            }
        }

        throw new NoSuchElementException("Folder not found. ");
    }


    /**
     *
     * @param filename the fileName to search
     * @return the files that match the filename inputted.
     */
    public MyGrowingArrayList<CustomFile> getMatchingFiles(String filename) {
        MyGrowingArrayList<CustomFile> foundFiles = new MyGrowingArrayList<>();
        searchFileRecursion(filename, foundFiles, subContents);
        return foundFiles;
    }

    // Depth-first search
    private void searchFileRecursion(String filenameToSearch, MyGrowingArrayList<CustomFile> filesFound, LinkedList<FileSystemEntity> currentContents) {
        for (int i = 0; i < currentContents.getSize(); i++) {
            FileSystemEntity currentFileFolder = currentContents.getElement(i);

            if (currentFileFolder instanceof CustomFile file && file.getName().contains(filenameToSearch))
                filesFound.insert(file);
            else if (currentFileFolder instanceof Folder folder)
                searchFileRecursion(filenameToSearch, filesFound, folder.getSubContents());
        }
    }


    /**
     *
     * @param fileToSearch the file to search
     * @return the to be searched
     */
    public CustomFile searchFile(CustomFile fileToSearch) {
        return searchFileRecursion(fileToSearch, subContents);
    }

    // Depth-first search
    private CustomFile searchFileRecursion(CustomFile fileToSearch, LinkedList<FileSystemEntity> currentContents) {
        for (int i = 0; i < currentContents.getSize(); i++) {
            FileSystemEntity currentFileFolder = currentContents.getElement(i);

            if (currentFileFolder instanceof CustomFile file && file.equals(fileToSearch))
                return file;
            else if (currentFileFolder instanceof Folder folder)
                searchFileRecursion(fileToSearch, folder.getSubContents());
        }

        throw new NoSuchElementException("File not found");
    }

    private boolean areFilesEqual(CustomFile fileToSearch, FileSystemEntity currentFileFolder, CustomFile file) {
        return (file.equals(fileToSearch) || file.getName().equals(currentFileFolder.getName()));
    }


    /**
     * Compares two folders
     *
     * @param o the other folder to compare to.
     * @return An integer 1 if greater, -1 if less than, and 0 if equals.
     * */
    @Override
    public int compareTo(Folder o) {
        return 0; // Fix later
    }

    /**
     * Compares this folder to the other (o) folder by name.
     * This will be used to identify when the folder name needs a duplicate identifier when adding a folder.
     * Ex: DupFolder -> DupFolder (2)
     * @param o the other Folder
     * @return true if two objects have the same name, or basically the same reference.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Folder folder = (Folder) o;

        return Objects.equals(getName(), folder.getName());
    }

    @Override
    public int hashCode() {
        return getName() != null ? Objects.hash(getName()) : 0;
    }

    /**
     * @return String representation of this Folder.
     */
    @Override
    public String toString() {
        return getName();
    }

}
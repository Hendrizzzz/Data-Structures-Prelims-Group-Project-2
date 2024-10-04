package prelim;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Objects;


public class Folder extends FileSystemEntity implements Comparable<Folder> {
    private LinkedList<FileSystemEntity> subContents;


    public Folder() {

    }

    public Folder(String name) {
        this.setName(name);
    }

    public Folder(String name, Date creationDate) {
        super (name, creationDate);
    }


    public Folder(String name, LinkedList<FileSystemEntity> subContents, Date creationDate) {
        super(name, creationDate);
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
     * Adds a customFile to this Folder
     * @param customFile the customFile to be added in this Folder.
     */
    public void addFile(CustomFile customFile) {
        addFileSystemEntity(customFile);
    }

    /**
     * Adds a folder to this Folder
     * @param folder the folder to be added in this Folder.
     */
    public void addFolder(Folder folder) {
        addFileSystemEntity(folder);
    }

    private void addFileSystemEntity(FileSystemEntity newEntity) { // modify if the file has a datafield of contents too
        // verify if the name of the newFile doesn't exist in the folder yet
        int fileNameAppearance = 0;
        for (int i = 0; i < subContents.getSize(); i++) {
            if (isSameEntity(subContents.getElement(i), newEntity))
                fileNameAppearance++;
        }

        // Update the fileName if there exists a fileName already
        if (fileNameAppearance != 0)
            newEntity.setName(getNameWithDuplicateIdentifier(newEntity, fileNameAppearance)); // repeatedName4Times (4).pdf

        subContents.insert(newEntity);
    }

    private boolean isSameEntity(FileSystemEntity entity, FileSystemEntity newEntity) {
        return entity.equals(newEntity);
    }

    private String getNameWithDuplicateIdentifier(FileSystemEntity newFile, int fileNameAppearance) {
        return newFile.getName() + " (" + fileNameAppearance + ")";
    }


    /**
     * Deletes a customFile in this Folder
     * @param customFile the customFile to be deleted.
     * @throws ListEmptyException when this Folder is empty.
     */
    public void removeFile(CustomFile customFile) throws ListEmptyException {
        removeFileSystemEntity(customFile);
    }

    /**
     * Deletes a Folder in this Folder.
     * @param folder the folder to be deleted.
     * @throws ListEmptyException when this Folder is empty.
     */
    public void removeFolder(Folder folder) throws ListEmptyException {
        removeFileSystemEntity(folder);
    }

    private void removeFileSystemEntity(FileSystemEntity fileSystemEntity) throws ListEmptyException {
        if (subContents.getSize() == 0)
            throw new ListEmptyException("The folder is empty. ");

        for (int i = 0; i < subContents.getSize(); i++)
            if (isSameEntity(subContents.getElement(i), fileSystemEntity)) {
                subContents.deleteAtIndex(i);
                return; // stop the traversing now
            }

        throw new NoSuchElementException("CustomFile / Folder not found. Cannot delete. ");
    }


    public void renameAFileInsideThisFolder(CustomFile oldCustomFile, String name, String extension) {
        for (int i = 0; i < subContents.getSize(); i++) {
            FileSystemEntity currentEntity = subContents.getElement(i);
            if (isSameEntity(currentEntity, oldCustomFile)) {
                CustomFile customFileToUpdate = (CustomFile) currentEntity;
                customFileToUpdate.setName(name);
                customFileToUpdate.setExtension(extension);
                return;
            }
        }
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
package prelim;

import prelim.DataStructures.MyGrowingArrayList;
import prelim.Exceptions.ListEmptyException;
import prelim.Objects.CustomFile;
import prelim.Objects.Folder;


public interface FileManagerInterface {
    // these are the public methods in the File Manager class
    void createFileWithName(String fileName, String extension);
    void createFile(CustomFile file);
    void createFolderWithName(String folderName);
    void createFolder(Folder folder);
    void deleteFile(String fileName, String extension) throws ListEmptyException;
    void deleteFolder(String folderName) throws ListEmptyException;
    void renameFile(String oldFileName, String oldExtension, String newFileName, String newExtension);
    void renameFolder(String oldFolderName, String newFolderName);
    void navigateToFolder(String folderName);
    void navigateBack();

    String getCurrentPath();
    Object getCurrentPathContents();
    String getFolderPath(String folderName); // for the gui
    String getFilePath(String fileName); // for the gui
    MyGrowingArrayList<CustomFile> getMatchingFiles(String filename); // returns the searched files in the current directory and inner directories
    CustomFile searchFile(String fileName);
}

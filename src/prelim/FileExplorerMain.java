package prelim;

import java.awt.Desktop;
import java.io.IOException;

public class FileExplorerMain {
    private FileManager fileManager;
    private File selectedFile;
    private Folder selectedFolder;

    public FileExplorerMain() {
        fileManager = new FileManager();
    }

    // Highlights file and show actions on what to do with the file
    public void onFileClick(File file) {
        this.selectedFile = file; // Store the selected file for future actions
        // Trigger GUI to show file-related action buttons
        showFileActions(file);
    }

    // Highlights folder and show actions on what to do with the folder
    public void onFolderClick(Folder folder) {
        this.selectedFolder = folder; // Store the selected folder for future actions
        // Trigger GUI to show folder-related action buttons
        showFolderActions(folder);
    }

    // Open the selected file in its default application when button is clicked
    public void openFile() {
        if (selectedFile != null) {
            try {
                String filePath = fileManager.getFilePath(selectedFile.getName() + "." + selectedFile.getExtension());
                // Open the file using the default system application
                Desktop.getDesktop().open(new java.io.File(filePath));  
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Open the selected folder when button is clicked
    public void openFolder() {
        if (selectedFolder != null) {
            fileManager.navigateToFolder(selectedFolder.getName());
            // Update GUI to display the contents of the selected folder
        }
    }

    // Create a new file with the specified name and extension
    public void createFile(String fileName, String fileExtension) {
        try {
            fileManager.createFile(fileName, fileExtension, 0); // Assuming size is 0 for a new file
            // Update GUI to reflect the new file directly here
        } catch (ListEmptyException e) {
            e.printStackTrace();
        }
    }

    // Create a new folder with the specified name
    public void createFolder(String folderName) {
        try {
            fileManager.createFolder(folderName);
            // Update GUI to reflect the new folder directly here
        } catch (ListEmptyException e) {
            e.printStackTrace();
        }
    }

    // Delete the selected file when button is clicked
    public void deleteFile() {
        if (selectedFile != null) {
            try {
                fileManager.deleteFile(selectedFile.getName(), selectedFile.getExtension());
                // Update GUI to reflect the deletion
            } catch (ListEmptyException e) {
                e.printStackTrace();
            }
        }
    }

    // Delete the selected folder
    public void deleteFolder() {
        if (selectedFolder != null) {
            try {
                fileManager.deleteFolder(selectedFolder.getName());
                // Update GUI to reflect the deletion
            } catch (ListEmptyException e) {
                e.printStackTrace();
            }
        }
    }

    // Rename the selected file (takes the new name and extension from the GUI)
    public void renameFile(String newFileName, String newExtension) {
        if (selectedFile != null) {
            fileManager.renameFile(selectedFile.getName(), selectedFile.getExtension(), newFileName, newExtension);
            // Update GUI to reflect the renaming
        }
    }

    // Rename the selected folder (takes the new folder name from the GUI)
    public void renameFolder(String newFolderName) {
        if (selectedFolder != null) {
            fileManager.renameFolder(selectedFolder.getName(), newFolderName);
            // Update GUI to reflect the renaming
        }
    }

    // Move the selected file to a target folder
    public void moveFile(Folder targetFolder) {
        if (selectedFile != null) {
            try {
                fileManager.deleteFile(selectedFile.getName(), selectedFile.getExtension());
                fileManager.navigateToFolder(targetFolder.getName());
                fileManager.createFile(selectedFile.getName(), selectedFile.getExtension(), selectedFile.getSize());
                fileManager.navigateBack();
                // Update GUI to reflect the move
            } catch (ListEmptyException e) {
                e.printStackTrace();
            }
        }
    }

    // Move the selected folder to another target folder
    public void moveFolder(Folder targetFolder) {
        if (selectedFolder != null) {
            try {
                fileManager.deleteFolder(selectedFolder.getName());
                fileManager.navigateToFolder(targetFolder.getName());
                fileManager.createFolder(selectedFolder.getName());
                fileManager.navigateBack();
                // Update GUI to reflect the move
            } catch (ListEmptyException e) {
                e.printStackTrace();
            }
        }
    }

    // Retrieves the contents of the current directory
    public Object getCurrentDirectoryContents() {
        return fileManager.getCurrentDirectoryContents();
    }

    // Retrieves the current directory path
    public String getCurrentDirectory() {
        return fileManager.getDirectory();
    }

    // Trigger the GUI to show folder-related buttons
    private void showFileActions(File file) {
        // show buttons to delete, open, etc.
    }

    // Trigger the GUI to show folder-related buttons
    private void showFolderActions(Folder folder) {
        // show buttons to delete, open, etc.
    }
}
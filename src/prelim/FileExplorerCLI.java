package prelim;

import prelim.DataStructures.LinkedList;
import prelim.Exceptions.ListEmptyException;
import prelim.Exceptions.SpecialFolderCreationException;
import prelim.Exceptions.SpecialFolderDeletionException;
import prelim.Objects.CustomFile;
import prelim.Objects.FileSystemEntity;
import prelim.Objects.Folder;
import prelim.Objects.Reader;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

public class FileExplorerCLI {
    private static final FileManager fileManager = new FileManager();

    public static void main(String[] args) {
        FileExplorerCLI myProgram;

        try {
            myProgram = new FileExplorerCLI();
            myProgram.run();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Runs the main loop for the file explorer application. It displays the menu and processes user inputs for directory
     * and file operations.
     * */
    public void run() {
        boolean isUserBrowsing = true;
        while (isUserBrowsing) {
            showMainMenu();
            int choice = Reader.readChoice(1, 13);

            switch (choice) {
                case 1 -> folderOperations();
                case 2 -> fileOperations();
                case 3 -> openFolder();
                case 4 -> fileManager.navigateBack();
                case 5 -> isUserBrowsing = false; }
            Reader.getSpace();
        }
    }


    private void folderOperations() {
        while (true) {
            showFolderMenu();
            int choice = Reader.readChoice(1, 4);
            switch (choice) {
                case 1 -> addNewFolder();
                case 2 -> deleteFolderInCurrentDirectory();
                case 3 -> renameFolderInCurrentDirectory();
                case 4 -> { return; } }
            Reader.pressEnter();
        }
    }


    private void fileOperations() {
        while (true) {
            showFileMenu();
            int choice = Reader.readChoice(1, 7);
            switch (choice) {
                case 1 -> addNewFileToCurrentDirectory();
                case 2 -> addFileFromExistingPath();
                case 3 -> deleteFileInCurrentDirectory();
                case 4 -> renameFileInCurrentDirectory();
                case 5 -> modifyFileInCurrentDirectory();
                case 6 -> openFile();
                case 7 -> { return; } }
            Reader.pressEnter();
        }
    }


    private String displayContentsOfCurrentDirectory() {
        StringBuilder contents = new StringBuilder();
        LinkedList<FileSystemEntity> currentPathContents = fileManager.getCurrentPathContents();

        for (int i = 0; i < currentPathContents.getSize(); i++)
            contents.append("- ").append(currentPathContents.getElement(i).toString()).append("\n");

        if (contents.isEmpty())
            contents.append("none");
        return contents.toString();
    }


    private void addNewFolder() {
        Folder newFolder = Reader.readFolder();
        fileManager.createFolder(newFolder);
        System.out.println("Successfully added " + newFolder);
    }

    private void addNewFileToCurrentDirectory() {
        CustomFile newFile = Reader.readFile();
        fileManager.createFile(newFile);
        System.out.println("Successfully added " + newFile);
    }

    private void addFileFromExistingPath() {
        String filePath = Reader.prompt("Enter the absolute file path: ");
        File file = new File(filePath);

        if (file.exists() && file.isFile()) {
            String fileName = file.getName(); // This gets the full file name
            String extension = "";

            int dotIndex = fileName.lastIndexOf('.');
            if (isExtensionExists(dotIndex, fileName)) {     // Extract extension if it exists
                extension = fileName.substring(dotIndex + 1);
                fileName = fileName.substring(0, dotIndex);
            }

            String fileContents = "";
            try {
                fileContents = CustomFile.readFileContents(filePath);   // Read file contents
            } catch (IOException ignored) {}

            CustomFile customFile = new CustomFile(fileName, extension, fileContents, filePath);
            try { fileManager.createFile(customFile); }
            catch (SpecialFolderCreationException e ) { System.out.println(e.getMessage()); return;  }

            System.out.println("Successfully added " + customFile + "  !");
        }
        else
            System.out.println("The file does not exist or is not a valid file.");
    }

    private boolean isExtensionExists(int dotIndex, String fileName) {
        return dotIndex > 0 && dotIndex < fileName.length() - 1;
    }

    private void deleteFolderInCurrentDirectory() {
        String folderName = Reader.prompt("Enter folder name to be deleted: ");

        try {
            fileManager.deleteFolder(folderName);
        } catch (ListEmptyException | SpecialFolderDeletionException | NoSuchElementException e) {
            System.out.println(e.getMessage()); return;
        }

        System.out.println(folderName + " is successfully deleted! ");
    }

    private void deleteFileInCurrentDirectory() {
        System.out.println("Input filename and extension separately. ");
        String fileName = Reader.prompt("Enter file name: ");
        String extension = Reader.readExtension();

        try {
            fileManager.deleteFile(fileName, extension);
        } catch (ListEmptyException | NoSuchElementException e) {
            System.out.println(e.getMessage()); return;
        }

        System.out.println(fileName + extension + " is successfully deleted! ");
    }

    private void renameFolderInCurrentDirectory() {

    }

    private void renameFileInCurrentDirectory() {

    }

    private void modifyFileInCurrentDirectory() {

    }

    private void openFolder() {
        String folderName = Reader.prompt("Enter folder name : ");
        fileManager.navigateToFolder(folderName);
    }

    private void openFile() {
        String fileName = Reader.prompt("Enter file name: ");

        CustomFile fileToOpen;
        try {
            fileToOpen = fileManager.searchFile(fileName);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage()); return;
        }

        displayFileDetails(fileToOpen);

        if (fileToOpen.getDesktopPath() != null)
            openFileInDefaultApplication(fileToOpen);
    }

    private void openFileInDefaultApplication(CustomFile fileToOpen) {
        try {
            File f = new File(fileToOpen.getDesktopPath());
            Desktop.getDesktop().open(f); // Open the file with default application
        } catch (IOException e) {
            System.out.println("Error opening the file: " + e.getMessage());
        }
    }

    private void displayFileDetails(CustomFile fileToOpen) {
        System.out.println("Filename: " + fileToOpen.getName());
        System.out.println("Extension: " + fileToOpen.getExtension());
        System.out.println("Size: " + fileToOpen.getSize() + " bytes");
        System.out.println("Creation Date: " + fileToOpen.getCreationDate());
        System.out.println("Last Modified: " + fileToOpen.getModificationDate());
        System.out.println("=== File ===");
        System.out.println(fileToOpen.getContents());
    }


    /**
     * Displays the file explorer menu, showing the current directory and available operations for the user.
     */
    private void showMainMenu() {
        String menu = """
        ╔════════════════════════════════════════════════╗
        ║                 File Explorer                  ║
        ╠════════════════════════════════════════════════╣
         Current Directory: %s
         Contents:
         %s
        ╚════════════════════════════════════════════════╝
        ┌────────────────────────────────────────────────┐
        │ 1. Folder Operations                           │
        │ 2. File Operations                             │
        │ 3. Go to Folder                                │
        │ 4. Go Back                                     │
        │ 5. Exit Program                                │
        └────────────────────────────────────────────────┘
        """;

        System.out.printf(menu, fileManager.getCurrentPath(), displayContentsOfCurrentDirectory());
    }


    private void showFolderMenu() {
        Reader.getSpace();
        System.out.printf("""
                ╔════════════════════════════════════════════════╗
                ║                 File Explorer                  ║
                ╠════════════════════════════════════════════════╣
                 Current Directory: %s
                 Contents:
                %s
                ╚════════════════════════════════════════════════╝
                """, fileManager.getCurrentPath(), displayContentsOfCurrentDirectory());
        System.out.println("╠═══════════════ Folder Operations ══════════════╣");
        System.out.println("│ 1. Create a New Folder                         │");
        System.out.println("│ 2. Remove a Folder                             │");
        System.out.println("│ 3. Rename a Folder                             │");
        System.out.println("│ 4. Back to Main Menu                           │");
        System.out.println("╚════════════════════════════════════════════════╝");
    }

    private void showFileMenu() {
        Reader.getSpace();
        System.out.printf("""
                ╔════════════════════════════════════════════════╗
                ║                 File Explorer                  ║
                ╠════════════════════════════════════════════════╣
                 Current Directory: %s
                 Contents:
                %s
                ╚════════════════════════════════════════════════╝
                """, fileManager.getCurrentPath(), displayContentsOfCurrentDirectory());
        System.out.println("╠═════════════════ File Operations ══════════════╣");
        System.out.println("│ 1. Create a New File                           │");
        System.out.println("│ 2. Import a File from Existing Path            │");
        System.out.println("│ 3. Remove a File                               │");
        System.out.println("│ 4. Rename a File                               │");
        System.out.println("│ 5. Edit a File                                 │");
        System.out.println("│ 6. Open a File                                 │");
        System.out.println("│ 7. Back to Main Menu                           │");
        System.out.println("╚════════════════════════════════════════════════╝");
    }


}

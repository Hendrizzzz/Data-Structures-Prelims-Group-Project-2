package prelim;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * This class is responsible for running a program that displays a menu that allows a user to manipulate directories
 * by utilizing a list of lists.
 * */
public class FileExplorerMain {
    private static FileManager fileManager;
    private static void displayMenu() {
        System.out.println("\nCurrent Directory: " + currentDirectory.getFullPath());
        System.out.println("""
        ================== File Explorer ==================
        (1) Display Folders in Current Directory
        (2) Display Files in Current Directory
        (3) Add a Folder in Current Directory
        (4) Add a Text File in Current Directory
        (5) Add a File from Existing File using File Path
        (6) Delete Folder in Current Directory
        (7) Delete File in Current Directory
        (8) Modify Folder in Current Directory
        (9) Modify File in Current Directory
        (10) Open a File
        (11) Open a Folder
        (12) Go Previous Directory
        (13) Exit Program
        ===================================================
        """);
        System.out.print("Choose an option (1-13): ");
    }

    private static Folder currentDirectory;
    /**
     * TODO
     * */
    public static void main(String[] args) {
        FileExplorerMain app = new FileExplorerMain();
        try {
            app.run();
        } catch (Exception e){
            e.printStackTrace();
        }
        System.exit(0);
    }
    /**
     * Main loop that runs the File Explorer menu system.
     * Handles user input to perform various file and folder operations in the current directory.
     *
     * @throws ListOverflowException if the maximum limit for files/folders is reached.
     * */
    public void run() throws ListOverflowException {
        Scanner scanner = new Scanner(System.in);
        fileManager = new FileManager(); // Initialize FileManager to create default folders
        currentDirectory = fileManager.getRootFolder(); // Set the current directory to root initially
        while (true) {
            // Call displayMenu to show the file explorer menu
            displayMenu();
            System.out.print("Choose an option (1-13): ");
            int choice = 0;
            switch (validateInput(choice)) {
                case 1 -> displayFoldersInCurrentDirectory();
                case 2 -> displayFilesInCurrentDirectory();
                case 3 -> {
                    System.out.print("Enter new folder name: ");
                    String newFolderName = scanner.nextLine();
                    addNewFolder(newFolderName);
                }
                case 4 -> addNewFileToCurrentDirectory(); // Changed to the new method
                case 5 -> {
                    System.out.print("Enter the path of the existing file to add: ");
                    String filePath = scanner.nextLine();
                    addFileFromExistingFile(filePath);
                    break;
                }
                case 6 -> {
                    System.out.print("Enter folder name to delete: ");
                    String folderToDelete = scanner.nextLine();
                    deleteFolderInCurrentDirectory(folderToDelete);
                }
                case 7 -> {
                    System.out.print("Enter file name to delete: ");
                    String fileToDelete = scanner.nextLine();
                    deleteFileInCurrentDirectory(fileToDelete);
                }
                case 8 -> {
                    System.out.print("Enter folder name to modify: ");
                    String folderToModify = scanner.nextLine();
                    modifyFolderInCurrentDirectory(folderToModify);
                }
                case 9 -> {
                    System.out.print("Enter file name to modify: ");
                    String fileToModify = scanner.nextLine();
                    modifyFileInCurrentDirectory(fileToModify);
                }
                case 10 -> {
                    System.out.println("Enter file name to open: ");
                    String fileToOpen = scanner.nextLine();
                    openFile(fileToOpen);
                }
                case 11 -> {
                    System.out.print("Enter folder name to open: ");
                    String folderToOpen = scanner.nextLine();
                    openFolder(folderToOpen);
                }
                case 12 -> goToPreviousDirectory();
                case 13 -> {
                    System.out.println("Exiting... Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid option. Please choose a valid number (1-11).");
            }
        }
    }

    /**
     * Validates the input if it satisfies the conditions if the user picked a number within the range of 1-13.
     *
     * @param choice User's input.
     * */
    private static int validateInput(int choice){
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;
        while (!validInput) {
            System.out.print("Choose an option (1-13): ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                if (choice < 1 || choice > 13) {
                    System.out.println("Invalid option. Please choose a valid number (1-12).");
                } else {
                    validInput = true; // Exit loop if input is valid
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 12.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
        return choice;
    }

    /**
     * Displays the list of subfolders in the current directory.
     * */
    private static void displayFoldersInCurrentDirectory() {
        List<Folder> subfolders = currentDirectory.getSubfolders();
        if (subfolders.isEmpty()) {
            System.out.println("Folders in " + currentDirectory.getFolderName() + ": N/A");
        } else {
            System.out.println("Folders in " + currentDirectory.getFolderName() + ":");
            for (Folder folder : subfolders) {
                System.out.println(" - " + folder.getFolderName());
            }
        }
    }

    /**
     * Displays the list of files in the current directory.
     * */
    private static void displayFilesInCurrentDirectory() {
        List<CustomFile> files = currentDirectory.getFiles();
        if (files.isEmpty()) {
            System.out.println("Files in " + currentDirectory.getFolderName() + ": N/A");
        } else {
            System.out.println("Files in " + currentDirectory.getFolderName() + ":");
            for (CustomFile file : files) {
                System.out.println(" - " + file.getFileName() + file.getExtension());
            }
        }
    }

    /**
     * Adds a new folder to the current directory.
     * 
     * @param folderName the name of the folder to be added.
     * */
    private static void addNewFolder(String folderName) {
        try {
            Folder newFolder = new Folder(folderName);
            currentDirectory.addSubfolder(newFolder);
            System.out.println("New folder added: " + folderName);
        } catch (Exception e) {
            System.out.println("Error adding folder: " + e.getMessage());
        }
    }

    /**
     * Adds a new text file to the current directory.
     * */
    private static void addNewFileToCurrentDirectory() {
        Scanner scanner = new Scanner(System.in);
        String[] acceptedFormats = {".txt", ".csv", ".json", ".xml", ".md"};
        while (true) {
            String fileName = askForFileName(scanner);
            String extension = askForFileExtention(scanner, acceptedFormats);
            String content = askForFileContent(scanner);
            // Check if the extension is valid
            if (extension == null) {
                if (askToExit(scanner))
                    break;
                continue;
            }
            // Check if a same file name already exists in the directory
            if (fileAlreadyInDirectory(fileName, extension)) {
                System.out.println("A file named " + fileName + extension + " already exists in the directory.");
                if (askToExit(scanner)) break;
                continue;
            }
            try {
                createFileToDirectory(fileName, content, extension);
                break; // Exit loop after successful addition
            } catch (IOException e) {
                System.out.println("Error adding file: " + e.getMessage());
            } catch (ListOverflowException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Adds a file to the current directory by copying it from an existing file path.
     *
     * @param filePath the path of the existing file to be added.
     * */
    private static void addFileFromExistingFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                String fileName = file.getName();
                // Read the content of the existing file
                Scanner fileScanner = new Scanner(file);
                StringBuilder content = new StringBuilder();
                while (fileScanner.hasNextLine()) {
                    content.append(fileScanner.nextLine()).append("\n");
                }
                // Create a new CustomFile and add it to the current directory
                CustomFile newFile = new CustomFile(fileName, "", content.toString());
                currentDirectory.addFile(newFile);
                System.out.println("File added: " + newFile.getFileName() + " to " + currentDirectory.getFolderName());
                fileScanner.close();
            } else {
                System.out.println("The file path specified does not exist.");
            }
        } catch (IOException | ListOverflowException e) {
            System.out.println("Error adding file: " + e.getMessage());
        }
    }

    /**
     * Checks if a file with the same name and extension already exists in the current directory.
     * 
     * @param fileName  the name of the file.
     * @param extension the file extension.
     * @return true if a file with the same name and extension exists; false otherwise.
     * */
    private static boolean fileAlreadyInDirectory(String fileName, String extension) {
        // Checks all the files in the directory if it has the same file name with the file being created
        for (CustomFile file: currentDirectory.getFiles()) {
            if (file.getFileName().equalsIgnoreCase(fileName) && file.getExtension().equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Asks the user whether they want to exit or continue.
     *
     * @return true if the user wants to exit, false otherwise.
     * */
    private static boolean askToExit(Scanner scanner) {
        // Provide an option to exit
        System.out.print("Type 'exit' to quit or press Enter to try again: ");
        String response = scanner.nextLine();
        return response.equalsIgnoreCase("exit");
    }

    /**
     * Creates a new file in the current directory.
     * 
     * @param fileName  the name of the file to be created (without extension).
     * @param extension the extension of the file (e.g., .txt).
     * @throws IOException if an error occurs during file creation.
     * @throws ListOverflowException if the directory has reached its file limit.
     * */
    private static void createFileToDirectory(String fileName, String content, String extension) throws IOException, ListOverflowException {
        // Creating file with the full name (fileName + extension)
        CustomFile newFile = new CustomFile(fileName, extension, content);
        currentDirectory.addFile(newFile);
        System.out.println("File added: " + newFile.getFileName() + newFile.getExtension() + " to " + currentDirectory.getFolderName());
    }

    /**
     * Prompts the user to enter a file extension from the list of accepted formats.
     *
     * @param acceptedFormats the array of accepted file extensions (e.g., .txt, .doc).
     * @return the selected file extension if valid; null otherwise.
     * */
    private static String askForFileExtention(Scanner scanner, String[] acceptedFormats) {
        System.out.println("Available formats: " + String.join(", ", acceptedFormats));
        System.out.print("Enter file extension <include '.'>: ");
        String extension = scanner.nextLine().trim();
        for (String format: acceptedFormats) {
            if (format.equalsIgnoreCase(extension)) {
                return extension;
            }
        }
        System.out.println("Invalid file extension. Please enter a valid text format or type 'exit' to quit.");
        return null;
    }

    /**
     * Prompts the user to enter the content for the file.
     *
     * @return the content entered by the user.
     * */
    private static String askForFileContent(Scanner scanner) {
        System.out.print("Enter file content: ");
        return scanner.nextLine();
    }

    /**
     * Prompts the user to enter a name for the file (excluding the extension).
     *
     * @return the file name entered by the user.
     * */
    private static String askForFileName(Scanner scanner) {
        System.out.print("Enter file name (without extension): ");
        return scanner.nextLine();
    }

    /**
     * Deletes a folder from the current directory by its name.
     * 
     * @param folderName the name of the folder to be deleted.
     * */
    private static void deleteFolderInCurrentDirectory(String folderName) {
        try {
            Folder folder = currentDirectory.getSubfolders().stream()
                    .filter(f -> f.getFolderName().equalsIgnoreCase(folderName))
                    .findFirst()
                    .orElse(null);
            if (folder != null) {
                currentDirectory.getSubfolders().remove(folder);
                System.out.println("Folder deleted: " + folderName);
            } else {
                System.out.println("Folder not found: " + folderName);
            }
        } catch (Exception e) {
            System.out.println("Error deleting folder: " + e.getMessage());
        }
    }

    /**
     * Deletes a file from the current directory by name.
     *
     * @param fileName the name of the file to be deleted.
     * */
    private static void deleteFileInCurrentDirectory(String fileName) {
        try {
            CustomFile file = currentDirectory.getFiles().stream()
                    .filter(f -> f.getFileName().equalsIgnoreCase(fileName))
                    .findFirst()
                    .orElse(null);
            if (file != null) {
                if (file.deleteFile()) {
                    currentDirectory.getFiles().remove(file);
                }
            } else {
                System.out.println("File not found: " + fileName);
            }
        } catch (Exception e) {
            System.out.println("Error deleting file: " + e.getMessage());
        }
    }

    /**
     * Modifies the folder name in the current directory.
     *
     * @param folderName the name of the folder to be modified.
     * */
    private static void modifyFolderInCurrentDirectory(String folderName) {
        try {
            Folder folder = currentDirectory.getSubfolders().stream()
                    .filter(f -> f.getFolderName().equalsIgnoreCase(folderName))
                    .findFirst()
                    .orElse(null);
            if (folder != null) {
                // Assuming you want to modify folder properties
                // Example: just changing the name for simplicity
                System.out.print("Enter new folder name: ");
                String newFolderName = new Scanner(System.in).nextLine();
                folder.setFolderName(newFolderName);
                System.out.println("Updated folder name from " + folderName + " to " + newFolderName);
            } else {
                System.out.println("Folder not found: " + folderName);
            }
        } catch (Exception e) {
            System.out.println("Error modifying folder: " + e.getMessage());
        }
    }

    /**
     * Modifies the file name and content in the current directory.
     * 
     * @param fileName the name of the file to be modified.
     * */
    private static void modifyFileInCurrentDirectory(String fileName) {
        try {
            CustomFile file = currentDirectory.getFiles().stream()
                    .filter(f -> f.getFileName().equalsIgnoreCase(fileName))
                    .findFirst()
                    .orElse(null);
            if (file != null) {
                // Assuming you want to modify file properties
                // Example: just changing the name for simplicity
                System.out.print("Enter new file name: ");
                String newFileName = new Scanner(System.in).nextLine();
                file.setFileName(newFileName);
                System.out.println("Updated file name from " + fileName + " to " + newFileName);
            } else {
                System.out.println("File not found: " + fileName);
            }
        } catch (Exception e) {
            System.out.println("Error modifying file: " + e.getMessage());
        }
    }

    /**
     * Opens a file in the current directory and displays its content.
     * 
     * @param fileName the name of the file to be opened.
     * */
    private static void openFile(String fileName) {
        // Search for the file in the current directory
        CustomFile file = findFileInDirectory(fileName);
        // Checks if such file exists, display error message and return if not
        if (file == null) {
            System.out.println("File not found: " + fileName);
            return;
        }
        // Opens the file with default application if it exists on the system
        if (file.isExistingFile()) {
            openExistingFile(file);
        } else {
            // Displays the file in console if the file was created by the user
            displayFileContent(file);
        }
        /* Old code
        CustomFile file = currentDirectory.getFiles().stream()
                .filter(f -> f.getFileName().equalsIgnoreCase(fileName))
                .findFirst()
                .orElse(null);

        if (file != null) {
            // Display the file attributes
            System.out.println("Filename: " + file.getFileName());
            System.out.println("Extension: " + file.getExtension());
            System.out.println("Size: " + file.getSize() + " bytes");
            System.out.println("Creation Date: " + file.getCreationDate());
            System.out.println("Last Modified: " + file.getLastModifiedDate());
            System.out.println("=== File Content ===");
            System.out.println(file.getContent()); // Display the content of the file
        } else {
            System.out.println("File not found: " + fileName);
            >>>>>>> src/prelim/FileExplorerMain.java
        }
        System.out.println("File not found in the current directory.");
         */
    }

    /**
     * Searches for a file by name in the current directory.
     *
     * @param fileName the name of the file to search for (without extension).
     * @return the CustomFile object if found, or null if the file is not present.
     * */
    private static CustomFile findFileInDirectory(String fileName) {
        for (CustomFile file: currentDirectory.getFiles()) {
            if (file.getFileName().equalsIgnoreCase(fileName)) {
                return file;
            }
        }
        return null;
    }

    /**
     * Opens an existing file using the system's default application.
     *
     * @param file the CustomFile object to be opened.
     * */
    private static void openExistingFile(CustomFile file) {
        try {
            // Use Desktop class to open the file
            File f = new File(file.getDesktopPath());
            Desktop.getDesktop().open(f); // Open the file with default application
            System.out.println("Opened file: " + file.getFileName());
        } catch (IOException e) {
            System.out.println("Error opening the file: " + e.getMessage());
        }
    }

    /**
     * Displays the details and content of the specified file.
     *
     * @param file the CustomFile object whose details and content will be displayed.
     * */
    private static void displayFileContent(CustomFile file) {
        System.out.println("Filename: " + file.getFileName());
        System.out.println("Extension: " + file.getExtension());
        System.out.println("Size: " + file.getSize() + " bytes");
        System.out.println("Creation Date: " + file.getCreationDate());
        System.out.println("Last Modified: " + file.getLastModifiedDate());
        System.out.println("=== File ===");
        System.out.println(file.getContent());
    }

    /**
     * Opens a folder in the current directory and sets it as the current directory.
     * 
     * @param folderName the name of the folder to be opened.
     * */
    private static void openFolder(String folderName) {
        Folder selectedFolder = currentDirectory.getSubfolders().stream()
                .filter(folder -> folder.getFolderName().equalsIgnoreCase(folderName))
                .findFirst()
                .orElse(null);
        if (selectedFolder != null) {
            currentDirectory = selectedFolder; // Update the current directory to the selected folder
            System.out.println("Opened folder: " + selectedFolder.getFullPath());
        } else {
            System.out.println("Folder not found: " + folderName);
        }
    }

    /**
     * Navigates to the previous directory if possible.
     * */
    private static void goToPreviousDirectory() {
        if (currentDirectory.getParentFolder() != null) {
            currentDirectory = currentDirectory.getParentFolder(); // Set current directory to parent folder
            System.out.println("Returned to: " + currentDirectory.getFullPath());
        } else {
            System.out.println("You are already in the root directory.");
        }
    }
}

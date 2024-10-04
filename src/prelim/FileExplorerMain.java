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
     * TODO
     * */
    public void run() throws ListOverflowException {
        Scanner scanner = new Scanner(System.in);
        fileManager = new FileManager(); // Initialize FileManager to create default folders
        currentDirectory = fileManager.getRootFolder(); // Set the current directory to root initially
        while (true) {
            showMenu();
            int choice = getChoiceWithValidation();

            switch (choice) {
                case 1 -> displayFoldersInCurrentDirectory();
                case 2 -> displayFilesInCurrentDirectory();
                case 3 -> addNewFolder();
                case 4 -> addNewFileToCurrentDirectory(); // Assumes this method is already defined
                case 5 -> addFileFromExistingPath();
                case 6 -> deleteFileInCurrentDirectory();
                case 7 -> deleteFolderInCurrentDirectory();
                case 8 -> modifyFileInCurrentDirectory();
                case 9 -> modifyFolderInCurrentDirectory();
                case 10 -> openFile();
                case 11 -> openFolder();
                case 12 -> goToPreviousDirectory();
                case 13 -> System.exit(0);
                default -> System.out.println("Invalid option. Please choose a valid number (1-11).");
            }
        }
    }

    private void showMenu() {
        System.out.printf("""
                ╔═══════════════════════════════════════╗
                ║           File Explorer               ║
                ╠═══════════════════════════════════════╣
                Current Directory: %s
                ╚═══════════════════════════════════════╝
                ┌───────────────────────────────────────┐
                │ 1. View Folders in Current Directory  │
                │ 2. View Files in Current Directory    │
                │ 3. Create a New Folder                │
                │ 4. Create a New Text File             │
                │ 5. Import a File from Existing Path   │
                │ 6. Remove a Folder                    │
                │ 7. Remove a File                      │
                │ 8. Rename a Folder                    │
                │ 9. Edit a File                        │
                │ 10. Open a File                       │
                │ 11. Open a Folder                     │
                │ 12. Navigate to Previous Directory    │
                │ 13. Exit Program                      │
                └───────────────────────────────────────┘
                %n""", currentDirectory.getFullPath());
    }

    /**
     * Validates the input if it satisfies the conditions if the user picked a number within the range of 1-13.
     * */
    private int getChoiceWithValidation(){
        int choice = 0;
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Choose an option (1-13): ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                if (choice < 1 || choice > 13)
                    System.out.println("Invalid option. Please choose a valid number (1-12).");
                else
                    validInput = true; // Exit loop if input is valid
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 12.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
        return choice;
    }


    /**
     * TODO
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
     * TODO
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
     * TODO
     * */
    private static void addNewFolder() {
        try {
            String folderName = readString("Enter new folder name: ");
            Folder newFolder = new Folder(folderName);
            currentDirectory.addSubfolder(newFolder);
            System.out.println("New folder added: " + folderName);
        } catch (Exception e) {
            System.out.println("Error adding folder: " + e.getMessage());
        }
    }


    /**
     * TODO
     * */
    private static void addNewFileToCurrentDirectory() {
        Scanner scanner = new Scanner(System.in);
        String[] acceptedFormats = {".txt", ".csv", ".json", ".xml", ".md"};
        while (true) {
            String fileName = askForFileName(scanner);
            String extension = askForFileExtension(scanner, acceptedFormats);
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

    private static void addFileFromExistingPath() {
        try {
            String filePath = readString("Enter file path of the file in your desktop: " );
            // Create an instance of CustomFile using the provided file path
            CustomFile newFile = new CustomFile(filePath);

            // Add the file to the current directory
            currentDirectory.addFile(newFile);
            System.out.println("Successfully added file: " + newFile.getFileName() + newFile.getExtension());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }



    /**
     * TODO
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
     * TODO
     * */
    private static boolean askToExit(Scanner scanner) {
        // Provide an option to exit
        System.out.print("Type 'exit' to quit or press Enter to try again: ");
        String response = scanner.nextLine();
        return response.equalsIgnoreCase("exit");
    }


    /**
     * TODO
     * */
    private static void createFileToDirectory(String fileName, String content, String extension) throws IOException, ListOverflowException {
        // Creating file with the full name (fileName + extension)
        CustomFile newFile = new CustomFile(fileName, extension, content);
        currentDirectory.addFile(newFile);
        System.out.println("File added: " + newFile.getFileName() + newFile.getExtension() + " to " + currentDirectory.getFolderName());
    }


    /**
     * TODO
     * */
    private static String askForFileExtension(Scanner scanner, String[] acceptedFormats) {
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
     * TODO
     * */
    private static String askForFileContent(Scanner scanner) {
        System.out.print("Enter file content: ");
        return scanner.nextLine();
    }


    /**
     * TODO
     * */
    private static String askForFileName(Scanner scanner) {
        System.out.print("Enter file name (without extension): ");
        return scanner.nextLine();
    }


    /**
     * TODO
     * */
    private static void deleteFolderInCurrentDirectory() {
        try {
            String folderName = readString("Enter folder name to delete: ");
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
     * TODO
     * */
    private static void deleteFileInCurrentDirectory() {
        try {
            String fileName = promptFileName();
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

    private static String readString(String message) {
        System.out.print(message);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }


    /**
     * TODO
     * */
    private static void modifyFolderInCurrentDirectory() {
        try {
            String folderName = readString("Enter folder name to modify: ");
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
     * TODO
     * */
    private static void modifyFileInCurrentDirectory() {
        try {
            String fileName = promptFileName();
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
     * TODO
     * */
    private static void openFile() {
        String fileName = promptFileName();
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
    }

    private static String promptFileName() {
        return readString("Enter file name: ");
    }


    /**
     * TODO
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
     * TODO
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
     * TODO
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
     * TODO
     * */
    private static void openFolder() {
        String folderName = readString("Enter folder name to open: ");
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
     * TODO
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

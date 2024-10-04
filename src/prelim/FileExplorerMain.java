package prelim;

import java.util.Scanner;

public class FileExplorerMain {
    private static FileManager fileManager;
    private static Folder currentDirectory;

    public static void main(String[] args) throws ListOverflowException {
        Scanner scanner = new Scanner(System.in);
        fileManager = new FileManager(); // Initialize FileManager to create default folders
        currentDirectory = fileManager.getRootFolder(); // Set the current directory to root initially

        while (true) {
            System.out.println("\nCurrent Directory: " + currentDirectory.getFolderName());
            System.out.println("=== File Explorer ===");
            System.out.println("1. Display Folders in Current Directory");
            System.out.println("2. Display Files in Current Directory");
            System.out.println("3. Add a Folder in Current Directory");
            System.out.println("4. Add a File in Current Directory");
            System.out.println("5. Delete Folder in Current Directory");
            System.out.println("6. Delete File in Current Directory");
            System.out.println("7. Modify Folder in Current Directory");
            System.out.println("8. Modify File in Current Directory");
            System.out.println("9. Open a Folder");
            System.out.println("10. Go Previous Directory");
            System.out.println("11. Exit Program");
            System.out.print("Choose an option (1-11): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    displayFoldersInCurrentDirectory();
                    break;
                case 2:
                    displayFilesInCurrentDirectory();
                    break;
                case 3:
                    System.out.print("Enter new folder name: ");
                    String newFolderName = scanner.nextLine();
                    addNewFolder(newFolderName);
                    break;
                case 4:
                    System.out.print("Enter file path: ");
                    String filePath = scanner.nextLine();
                    addNewFileToCurrentDirectory(filePath);
                    break;
                case 5:
                    System.out.print("Enter folder name to delete: ");
                    String folderToDelete = scanner.nextLine();
                    deleteFolderInCurrentDirectory(folderToDelete);
                    break;
                case 6:
                    System.out.print("Enter file name to delete: ");
                    String fileToDelete = scanner.nextLine();
                    deleteFileInCurrentDirectory(fileToDelete);
                    break;
                case 7:
                    System.out.print("Enter folder name to modify: ");
                    String folderToModify = scanner.nextLine();
                    modifyFolderInCurrentDirectory(folderToModify);
                    break;
                case 8:
                    System.out.print("Enter file name to modify: ");
                    String fileToModify = scanner.nextLine();
                    modifyFileInCurrentDirectory(fileToModify);
                    break;
                case 9:
                    System.out.print("Enter folder name to open: ");
                    String folderToOpen = scanner.nextLine();
                    openFolder(folderToOpen);
                    break;
                case 10:
                    goToPreviousDirectory();
                    break;
                case 11:
                    System.out.println("Exiting... Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please choose a valid number (1-11).");
            }
        }
    }

    private static void displayFoldersInCurrentDirectory() {
        System.out.println("Folders in " + currentDirectory.getFolderName() + ":");
        for (Folder folder : currentDirectory.getSubfolders()) {
            System.out.println(" - " + folder.getFolderName());
        }
    }

    private static void displayFilesInCurrentDirectory() {
        System.out.println("Files in " + currentDirectory.getFolderName() + ":");
        for (CustomFile file : currentDirectory.getFiles()) {
            System.out.println(" - " + file.getFileName());
        }
    }

    private static void addNewFolder(String folderName) {
        try {
            Folder newFolder = new Folder(folderName);
            currentDirectory.addSubfolder(newFolder);
            System.out.println("New folder added: " + folderName);
        } catch (Exception e) {
            System.out.println("Error adding folder: " + e.getMessage());
        }
    }

    private static void addNewFileToCurrentDirectory(String filePath) {
        try {
            CustomFile newFile = new CustomFile(filePath);
            currentDirectory.addFile(newFile);
            System.out.println("File added: " + newFile.getFileName() + " to " + currentDirectory.getFolderName());
        } catch (Exception e) {
            System.out.println("Error adding file: " + e.getMessage());
        }
    }

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

    private static void deleteFileInCurrentDirectory(String fileName) {
        try {
            CustomFile file = currentDirectory.getFiles().stream()
                    .filter(f -> f.getFileName().equalsIgnoreCase(fileName))
                    .findFirst()
                    .orElse(null);
            if (file != null) {
                currentDirectory.getFiles().remove(file);
                System.out.println("File deleted: " + fileName);
            } else {
                System.out.println("File not found: " + fileName);
            }
        } catch (Exception e) {
            System.out.println("Error deleting file: " + e.getMessage());
        }
    }

    private static void modifyFolderInCurrentDirectory(String folderName) {
        try {
            Folder folder = currentDirectory.getSubfolders().stream()
                    .filter(f -> f.getFolderName().equalsIgnoreCase(folderName))
                    .findFirst()
                    .orElse(null);
            if (folder != null) {
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

    private static void modifyFileInCurrentDirectory(String fileName) {
        try {
            CustomFile file = currentDirectory.getFiles().stream()
                    .filter(f -> f.getFileName().equalsIgnoreCase(fileName))
                    .findFirst()
                    .orElse(null);
            if (file != null) {
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

    private static void openFolder(String folderName) {
        try {
            Folder folder = currentDirectory.getSubfolders().stream()
                    .filter(f -> f.getFolderName().equalsIgnoreCase(folderName))
                    .findFirst()
                    .orElse(null);
            if (folder != null) {
                currentDirectory = folder; // Update current directory
                System.out.println("Opened folder: " + folderName);
            } else {
                System.out.println("Folder not found: " + folderName);
            }
        } catch (Exception e) {
            System.out.println("Error opening folder: " + e.getMessage());
        }
    }

    private static void goToPreviousDirectory() {
        if (currentDirectory.getFolderName().equals("root")) {
            System.out.println("You are already in the root directory.");
        } else {
            // Logic to go back to the parent directory.
            System.out.println("This feature is not yet implemented.");
        }
    }
}

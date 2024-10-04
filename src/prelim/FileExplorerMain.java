package prelim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class FileExplorerMain {
    private static FileManager fileManager;

    public static void main(String[] args) throws ListOverflowException {
        Scanner scanner = new Scanner(System.in);
        fileManager = new FileManager(); // Initialize FileManager to create default folders

        while (true) {
            System.out.println("\n=== File Explorer ===");
            System.out.println("1. Display Default Folders");
            System.out.println("2. Add a New Folder");
            System.out.println("3. Add a New File to a Folder");
            System.out.println("4. List Files in a Folder");
            System.out.println("5. Update a Folder's Name");
            System.out.println("6. Compare Two Folders");
            System.out.println("7. Exit");
            System.out.print("Choose an option (1-7): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    displayDefaultFolders();
                    break;
                case 2:
                    System.out.print("Enter new folder name: ");
                    String newFolderName = scanner.nextLine();
                    addNewFolder(newFolderName);
                    break;
                case 3:
                    System.out.print("Enter the folder name to add a file to: ");
                    String folderName = scanner.nextLine();
                    System.out.print("Enter file path: ");
                    String filePath = scanner.nextLine();
                    addNewFileToFolder(folderName, filePath);
                    break;
                case 4:
                    System.out.print("Enter folder name to list files: ");
                    String folderToList = scanner.nextLine();
                    listFilesInFolder(folderToList);
                    break;
                case 5:
                    System.out.print("Enter old folder name: ");
                    String oldFolderName = scanner.nextLine();
                    System.out.print("Enter new folder name: ");
                    String updatedFolderName = scanner.nextLine();
                    updateFolderName(oldFolderName, updatedFolderName);
                    break;
                case 6:
                    compareTwoFolders(scanner);
                    break;
                case 7:
                    System.out.println("Exiting... Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please choose a valid number (1-7).");
            }
        }
    }

    private static void displayDefaultFolders() {
        fileManager.displayDefaultFolders();
    }

    private static void addNewFolder(String folderName) {
        try {
            Folder newFolder = new Folder(folderName);
            fileManager.getRootFolder().addSubfolder(newFolder);
            System.out.println("New folder added: " + folderName);
        } catch (Exception e) {
            System.out.println("Error adding folder: " + e.getMessage());
        }
    }

    private static void addNewFileToFolder(String folderName, String filePath) {
        try {
            Folder folder = fileManager.getDefaultFolder(folderName);
            if (folder != null) {
                CustomFile newFile = new CustomFile(filePath);
                folder.addFile(newFile);
                System.out.println("File added: " + newFile.getFileName() + " to " + folderName);
            } else {
                System.out.println("Folder not found: " + folderName);
            }
        } catch (Exception e) {
            System.out.println("Error adding file: " + e.getMessage());
        }
    }

    private static void listFilesInFolder(String folderName) {
        try {
            Folder folder = fileManager.getDefaultFolder(folderName);
            if (folder != null) {
                System.out.println("Files in " + folder.getFolderName() + ":");
                for (CustomFile file : folder.getFiles()) {
                    System.out.println(" - " + file.getFileName());
                }
            } else {
                System.out.println("Folder not found: " + folderName);
            }
        } catch (Exception e) {
            System.out.println("Error listing files: " + e.getMessage());
        }
    }

    private static void updateFolderName(String oldFolderName, String newFolderName) {
        try {
            Folder folder = fileManager.getDefaultFolder(oldFolderName);
            if (folder != null) {
                folder.setFolderName(newFolderName);
                System.out.println("Updated folder name from " + oldFolderName + " to " + newFolderName);
            } else {
                System.out.println("Folder not found: " + oldFolderName);
            }
        } catch (Exception e) {
            System.out.println("Error updating folder name: " + e.getMessage());
        }
    }

    private static void compareTwoFolders(Scanner scanner) {
        System.out.print("Enter first folder name: ");
        String folderName1 = scanner.nextLine();
        System.out.print("Enter second folder name: ");
        String folderName2 = scanner.nextLine();

        try {
            Folder folder1 = fileManager.getDefaultFolder(folderName1);
            Folder folder2 = fileManager.getDefaultFolder(folderName2);

            if (folder1 != null && folder2 != null) {
                int comparisonResult = folder1.compareTo(folder2);
                if (comparisonResult > 0) {
                    System.out.println(folder1.getFolderName() + " is greater than " + folder2.getFolderName());
                } else if (comparisonResult < 0) {
                    System.out.println(folder1.getFolderName() + " is less than " + folder2.getFolderName());
                } else {
                    System.out.println("Both folders are equal.");
                }
            } else {
                System.out.println("One or both folders not found.");
            }
        } catch (Exception e) {
            System.out.println("Error comparing folders: " + e.getMessage());
        }
    }
}
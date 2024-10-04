package prelim;

import java.io.File;
import java.io.IOException;

public class CreateDirectoryExample {
    public static void main(String[] args) {
        // Create the main directory
        File mainDirectory = new File("exampleDir");
        if (mainDirectory.mkdir()) {
            System.out.println("Main directory created: " + mainDirectory.getName());
        } else {
            System.out.println("Main directory already exists.");
        }

        // Create the Desktop directory
        File desktopDirectory = new File(mainDirectory, "Desktop");
        if (desktopDirectory.mkdir()) {
            System.out.println("Desktop directory created: " + desktopDirectory.getName());
        } else {
            System.out.println("Desktop directory already exists.");
        }

        // Create the Documents directory
        File documentsDirectory = new File(mainDirectory, "Documents");
        if (documentsDirectory.mkdir()) {
            System.out.println("Documents directory created: " + documentsDirectory.getName());
        } else {
            System.out.println("Documents directory already exists.");
        }

        // Create a sample file in the Desktop directory
        createSampleFile(desktopDirectory, "sampleFile.txt");

        // Create a sample file in the Documents directory
        createSampleFile(documentsDirectory, "exampleDoc.txt");

        // Create a folder inside the Desktop directory
        File newFolder = new File(desktopDirectory, "NewFolder");
        if (newFolder.mkdir()) {
            System.out.println("New folder created inside Desktop: " + newFolder.getName());
        } else {
            System.out.println("New folder already exists inside Desktop.");
        }

        // Create another folder inside the NewFolder
        File innerFolder = new File(newFolder, "InnerFolder");
        if (innerFolder.mkdir()) {
            System.out.println("Inner folder created inside NewFolder: " + innerFolder.getName());
        } else {
            System.out.println("Inner folder already exists inside NewFolder.");
        }

        // Create a sample file inside the InnerFolder
        createSampleFile(innerFolder, "innerFile.txt");
    }

    // Method to create a sample file
    private static void createSampleFile(File directory, String fileName) {
        File file = new File(directory, fileName);
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists: " + file.getName());
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file.");
            e.printStackTrace();
        }
    }
}

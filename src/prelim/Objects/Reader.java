package prelim.Objects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import prelim.Exceptions.InvalidFileExtensionException;
import prelim.Exceptions.InvalidFileEntityNameException;

public class Reader {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


    public static int readChoice(int minimum, int maximum) {
        while (true) {
            try {
                int choice = Integer.parseInt(reader.readLine());

                if (choice < minimum || choice > maximum)
                    System.out.println("Choice not found. Please select from " + minimum + " to " + maximum + " only. Try again. ");
                else
                    return choice;
            } catch (NumberFormatException e) {
                System.out.println("Error: Input not a number. Try again. ");
            } catch (IOException e) {
                System.out.println("There's some error while reading the input. Try again. ");
            }
        }
    }

    public static String prompt(String prompt) {
        System.out.print(prompt);

        return readString();
    }

    public static String readString() {
        while (true) {
            try {
                String input = reader.readLine();
                if (!input.isBlank())  return input;
            } catch (IOException ignored) {
                System.out.println("There's some error while reading the input. Try again. ");
            }
        }

    }

    /**
     * @throws InvalidFileExtensionException when the extension is not valid
     * @throws InvalidFileEntityNameException when the name of the file entity contains backslash/es
     * @return the file read
     */
    public static CustomFile readFile() {
        String fileName = prompt("Enter fileName: ");
        String extension = prompt("Enter file extension: ");
        String contents = prompt("Enter contents: ");

        return new CustomFile(fileName, extension, contents);
    }



    public static Folder readFolder() {
        String folderName = prompt("Enter folder name: ");
        return new Folder(folderName);
    }


    public static void pressEnter() {
        try {
            System.out.println("Press <Enter> to continue.... ");
            reader.readLine();
        } catch (IOException ignored){}

        getSpace();
    }

    public static void getSpace() {
        System.out.println("\n\n\n\n\n\n");
    }
}

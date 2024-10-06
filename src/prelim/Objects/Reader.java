package prelim.Objects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

    public static CustomFile readFile() {
        String fileName = prompt("Enter fileName: ");
        String extension = readExtension();
        String contents = prompt("Enter contents: ");

        return new CustomFile(fileName, extension, contents);
    }

    public static String readExtension(){
        while (true) {
            String extension = prompt("Enter extension: ");
            switch (extension) {
                case ".txt", ".csv", ".json", ".xml", ".md"
                    ->  { return extension; }
                default -> System.out.println("Accepted file extensions:  .txt | .csv | .json | .xml | .md | .pdf | .docx");
            }
        }
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

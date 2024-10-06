package prelim;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * GUI application for the File Explorer.
 * @version 1.0
 * */
public class FileExplorerGUI extends JFrame {
    private static JFrame frame;
    private static JPanel panel;
    private static JTextField searchTextField;

    public static void main(String[] args) {
        FileExplorerGUI app = new FileExplorerGUI();
        try {
            app.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Method responsible for displaying the directories for all the folders and files in the File Explorer.
     * */
    private void run() {
        frame = new JFrame("File Explorer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Creating a panel to hold both directory and operation panels on the left
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(2, 1)); // Two rows: one for directory, one for operation
        leftPanel.add(getDirectoryPanel()); // Add directory panel at the top
        leftPanel.add(getOperationPanel()); // Add operation panel below it

        // Creating a panel to place search panel on the top right and left panel on the top left
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(leftPanel, BorderLayout.WEST); // Left side of the top panel
        topPanel.add(getSearchPanel(), BorderLayout.EAST); // Right side of the top panel

        // Adding the top panel to the frame
        frame.setLayout(new BorderLayout());
        frame.add(topPanel, BorderLayout.NORTH);
        frame.setSize(800, 600); // Set preferred size
        frame.setVisible(true);
    }
    /**
     * Creates a JPanel containing buttons for the user to choose between
     * deleting, copying, pasting, cutting, or renaming a file or a folder.
     *
     * @return The created JPanel.
     */
    private JPanel getOperationPanel() {
        panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton deleteButton = new JButton("Delete");
        JButton copyButton = new JButton("Copy");
        JButton pasteButton = new JButton("Paste");
        JButton cutButton = new JButton("Cut");
        JButton renameButton = new JButton("Rename");
        // Adding buttons to the panel
        panel.add(deleteButton);
        panel.add(copyButton);
        panel.add(pasteButton);
        panel.add(cutButton);
        panel.add(renameButton);
        return panel;
    }
    /**
     * Creates a JPanel containing a search bar for a user to find a specific file or a folder
     * he/ she wishes to look for.
     *
     * @return The created JPanel.
     * */
    private JPanel getSearchPanel() {
        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        searchTextField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        panel.add(searchTextField);
        panel.add(searchButton);
        // Action listener for search
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchByFileName();  // You can call the method to search by file or folder
            }
        });
        return panel;
    }
    /**
     * Creates a JPanel containing directory options for the user such as going back to the root folder of
     * the current directory or going all the way back to the home directory.
     *
     * @return The created JPanel.
     * */
    private JPanel getDirectoryPanel() {
        panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton backButton = new JButton("Back");
        JButton rootButton = new JButton("Go to Root");
        JButton homeButton = new JButton("Go to Home");

        // Add buttons to panel
        panel.add(backButton);
        panel.add(rootButton);
        panel.add(homeButton);

        return panel;
    }
    /**
     * Searches list based on given folder name by the user.
     */
    private void searchByFolderName(){
        searchTextField = new JTextField();
        searchTextField.addActionListener((e) -> {
           String key = searchTextField.getText();
           FileManager fileManager = new FileManager();
           //fileManager.searchFolder(key); TODO
        });
    }
    private void searchByFileName(){
        searchTextField = new JTextField();
        searchTextField.addActionListener((e) -> {
            String key = searchTextField.getText();
            FileManager fileManager = new FileManager();
            fileManager.searchFile(key);
        });
    }
    /**
     * Method {@code ActionListener} implementation for searching a folder and utilizing the functional interface
     * with a lambda expression.
     */
    private static class SearchByFolderNameActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //TODO
        }
    }
    /**
     * Method {@code ActionListener} implementation for searching a file and utilizing the functional interface
     * with a lambda expression.
     */
    private static class SearchByFileNameActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //TODO
        }
    }
}

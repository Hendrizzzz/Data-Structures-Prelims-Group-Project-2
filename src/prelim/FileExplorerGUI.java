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
    //TODO

    public static void main(String[] args) {
        FileExplorerGUI app = new FileExplorerGUI();
        try {
            app.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
    /**
     * Method responsible for displaying the directories for all the folders and files in the File Explorer.
     * */
    private void run() {
        frame = new JFrame("File Explorer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        //TODO
    }
    /**
     * Creates a JPanel containing buttons for the user to choose between
     * deleting, copying, pasting, cutting, or renaming a file or a folder.
     *
     * @return The created JPanel.
     */
    private JPanel getOperationPanel() {
        //TODO
        return panel;
    }
    /**
     * Creates a JPanel containing a search bar for a user to find a specific file or a folder
     * he/ she wishes to look for.
     *
     * @return The created JPanel.
     * */
    private JPanel getSearchPanel() {
        //TODO
        return panel;
    }
    /**
     * Creates a JPanel containing directory options for the user such as going back to the root folder of
     * the current directory or going all the way back to the home directory.
     *
     * @return The created JPanel.
     * */
    private JPanel getDirectoryPanel() {
        //TODO
        return panel;
    }
    /**
     * Searches list based on given folder name by the user.
     */
    private void searchByFolderName(){
        //TODO
        searchTextField = new JTextField();
    }
    private void searchByFileName(){
        //TODO
        searchTextField = new JTextField();
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

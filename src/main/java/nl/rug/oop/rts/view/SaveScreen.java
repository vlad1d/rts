package nl.rug.oop.rts.view;

import javax.swing.*;
import java.io.File;

/**
 * Class that pops the FileChooser for the user and allows it to make a choice.
 */
public class SaveScreen {

    /**
     * The function that does it.
     *
     * @return null if no file is chosen or the selected file
     */
    public static File chooseFile() {
        JFileChooser jFileChooser = new JFileChooser();
        int returned = jFileChooser.showSaveDialog(null);
        if (returned == JFileChooser.APPROVE_OPTION) {
            return jFileChooser.getSelectedFile();
        } else {
            return null;
        }
    }
}

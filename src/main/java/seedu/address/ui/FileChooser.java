package seedu.address.ui;

import java.io.File;

import javax.swing.JFileChooser;

/**
 * The file chooser dialogue box of the app.
 */
//@@author javenseow
public class FileChooser {

    private static String outcome;

    /**
     * Opens a dialog page to allow user to choose image to upload.
     *
     * @return file path of the image chosen.
     */
    public static String showDialog() {

        JFileChooser jfc = new JFileChooser();
        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            outcome = selectedFile.getAbsolutePath();
        } else if (returnValue == JFileChooser.CANCEL_OPTION) {
            outcome = "Cancel option chosen.";
        }
        return outcome;
    }
}

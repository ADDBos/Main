package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

import org.simplejavamail.email.Email;

import seedu.address.model.EmailModel;

//@@author EatOrBeEaten
/**
 * Represents a storage for Email.
 */
public interface EmailStorage {

    /**
     * Returns the file path of the Email directory.
     */
    Path getEmailPath();

    /**
     * Saves the email to local directory.
     * @param emailModel cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveEmail(EmailModel emailModel) throws IOException;

    /**
     * Loads email from local directory.
     *
     * @param emailName Name of eml file
     * @return Email object copy of selected email
     * @throws IOException if there was any problem loading the file.
     */
    Email loadEmail(String emailName) throws IOException;

    /**
     * Delete email from local directory.
     *
     * @param emailName Name of the eml file
     * @throws IOException if there was any problem deleting the file.
     */
    void deleteEmail(String emailName) throws IOException;

    /**
     * Returns a set of names of eml files in the directory.
     * Returns empty set if directory does not exist.
     */
    Set<String> readEmailFiles();

    Set<String> readEmailFiles(Path dirPath);
}

package seedu.address.storage;

import static java.util.Objects.requireNonNull;
import static org.simplejavamail.converter.EmailConverter.emlToEmail;
import static seedu.address.commons.util.FileUtil.readFromFile;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.simplejavamail.converter.EmailConverter;
import org.simplejavamail.email.Email;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.EmailModel;

//@@author EatOrBeEaten
/**
 * A class to access Email directory in the hard disk
 */
public class EmailDirStorage implements EmailStorage {

    private static final Logger logger = LogsCenter.getLogger(EmailDirStorage.class);

    private final String emlExtension = ".eml";

    private Path dirPath;

    public EmailDirStorage(Path dirPath) {
        this.dirPath = dirPath;
    }

    @Override
    public Path getEmailPath() {
        return dirPath;
    }

    @Override
    public void saveEmail(EmailModel emailModel) throws IOException {
        Path fileName = Paths.get(dirPath.toString(), emailModel.getEmail().getSubject().concat(emlExtension));
        String toSave = EmailConverter.emailToEML(emailModel.getEmail());
        FileUtil.createIfMissing(fileName);
        FileUtil.writeToFile(fileName, toSave);
    }

    @Override
    public Email loadEmail(String emailName) throws IOException {
        String fileName = emailName + emlExtension;
        Path pathToLoad = Paths.get(dirPath.toString(), fileName);
        String emlString = readFromFile(pathToLoad);
        Email loadedEmail = emlToEmail(emlString);
        return loadedEmail;
    }

    @Override
    public void deleteEmail(String emailName) throws IOException {
        String fileName = emailName + emlExtension;
        Path pathToDelete = Paths.get(dirPath.toString(), fileName);
        Files.delete(pathToDelete);
    }

    @Override
    public Set<String> readEmailFiles() {
        return readEmailFiles(dirPath);
    }

    /**
     * @param dirPath Location of email directory. Cannot be null.
     * @return A set of names of the eml files or empty if no eml files exist in the directory.
     */
    public Set<String> readEmailFiles(Path dirPath) {

        requireNonNull(dirPath);

        if (!Files.exists(dirPath)) {
            logger.info("Email directory " + dirPath + " not found");
            return new HashSet<>();
        }

        File emailDir = new File(dirPath.toString());

        FilenameFilter emlFilter = (dir, name) -> name.endsWith(emlExtension);

        String[] nameArray = emailDir.list(emlFilter);
        return new HashSet<>(Arrays.asList(nameArray));
    }

}

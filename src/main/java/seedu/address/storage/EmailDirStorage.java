package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.simplejavamail.converter.EmailConverter;

import seedu.address.commons.util.FileUtil;
import seedu.address.model.EmailModel;

//@@author EatOrBeEaten
/**
 * A class to access Email directory in the hard disk
 */
public class EmailDirStorage implements EmailStorage {

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
        Path fileName = Paths.get(dirPath.toString(), emailModel.getEmail().getSubject().concat(".eml"));
        String toSave = EmailConverter.emailToEML(emailModel.getEmail());
        FileUtil.createIfMissing(fileName);
        FileUtil.writeToFile(fileName, toSave);
    }

}

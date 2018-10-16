package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyBudgetBook;

/**
 * A class to access BudgetBook data stored as an xml file on the hard disk.
 *
 * @author ericyjw
 */
public class XmlBudgetBookStorage implements BudgetBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlBudgetBookStorage.class);

    private Path filePath;

    public XmlBudgetBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getBudgetBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyBudgetBook> readBudgetBook() throws DataConversionException, IOException {
        return readBudgetBook(filePath);
    }

    /**
     * Similar to {@link #readBudgetBook()}
     *
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyBudgetBook> readBudgetBook(Path filePath) throws DataConversionException,
        FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("BudgetBook file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableBudgetBook xmlBudgetBook = XmlFileStorage.loadDataFromSaveBbFile(filePath);
        try {
            return Optional.of(xmlBudgetBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveBudgetBook(ReadOnlyBudgetBook budgetBook) throws IOException {
        saveBudgetBook(budgetBook, filePath);
    }


    /**
     * Similar to {@link #saveBudgetBook(ReadOnlyBudgetBook)}
     *
     * @param filePath location of the data. Cannot be null
     */
    public void saveBudgetBook(ReadOnlyBudgetBook budgetBook, Path filePath) throws IOException {
        requireNonNull(budgetBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToBbFile(filePath, new XmlSerializableBudgetBook(budgetBook));
    }

}

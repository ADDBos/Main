package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import org.simplejavamail.email.Email;

import com.google.common.eventbus.Subscribe;

import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.BudgetBookChangedEvent;
import seedu.address.commons.events.model.EmailLoadedEvent;
import seedu.address.commons.events.model.EmailSavedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.events.storage.EmailLoadEvent;
import seedu.address.commons.events.ui.EmailNotFoundEvent;
import seedu.address.commons.events.ui.EmailViewEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.EmailModel;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyBudgetBook;
import seedu.address.model.UserPrefs;


/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;
    private BudgetBookStorage budgetBookStorage;
    private CalendarStorage calendarStorage;
    private EmailStorage emailStorage;

    public StorageManager(AddressBookStorage addressBookStorage, BudgetBookStorage budgetBookStorage,
                          UserPrefsStorage userPrefsStorage,
                          CalendarStorage calendarStorage, EmailStorage emailStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.budgetBookStorage = budgetBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.calendarStorage = calendarStorage;
        this.emailStorage = emailStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public Path getBudgetBookFilePath() {
        return budgetBookStorage.getBudgetBookFilePath();
    }

    @Override
    public Optional<ReadOnlyBudgetBook> readBudgetBook() throws DataConversionException, IOException {
        return readBudgetBook(budgetBookStorage.getBudgetBookFilePath());
    }

    @Override
    public Optional<ReadOnlyBudgetBook> readBudgetBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return budgetBookStorage.readBudgetBook(filePath);
    }

    @Override
    public void saveBudgetBook(ReadOnlyBudgetBook budgetBook) throws IOException {
        saveBudgetBook(budgetBook, budgetBookStorage.getBudgetBookFilePath());
    }

    @Override
    public void saveBudgetBook(ReadOnlyBudgetBook budgetBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        budgetBookStorage.saveBudgetBook(budgetBook, filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    @Subscribe
    public void handleBudgetBookChangedEvent(BudgetBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveBudgetBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    //@@author EatOrBeEaten
    // ================ Email methods ==============================

    @Override
    public Path getEmailPath() {
        return emailStorage.getEmailPath();
    }

    @Override
    public void saveEmail(EmailModel emailModel) throws IOException {
        emailStorage.saveEmail(emailModel);
    }

    @Override
    public Email loadEmail(String emailName) throws IOException {
        return emailStorage.loadEmail(emailName);
    }

    @Override
    public Set<String> readEmailFiles() {
        return readEmailFiles(emailStorage.getEmailPath());
    }

    @Override
    public Set<String> readEmailFiles(Path dirPath) {
        logger.fine("Attempting to read eml files from directory: " + dirPath);
        return emailStorage.readEmailFiles(dirPath);
    }

    @Override
    @Subscribe
    public void handleEmailSavedEvent(EmailSavedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Email composed, saving to file"));
        try {
            saveEmail(event.data);
            raise(new EmailViewEvent(event.data));
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    @Subscribe
    public void handleEmailLoadEvent(EmailLoadEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Attempting to read email from file"));
        try {
            Email loadedEmail = loadEmail(event.data);
            raise(new EmailLoadedEvent(loadedEmail));
        } catch (IOException e) {
            logger.warning("Email file not found.");
            raise(new EmailNotFoundEvent(event.data));
        }
    }


    //@@author GilgameshTC
    // ================ Calendar methods ==============================

    @Override
    public Path getCalendarPath() {
        return calendarStorage.getCalendarPath();
    }

    @Override
    public void createCalendar(Calendar calendar, String calendarName) throws IOException {
        calendarStorage.createCalendar(calendar, calendarName);
    }

    @Override
    public Calendar loadCalendar(String calendarName) throws IOException, ParserException {
        return calendarStorage.loadCalendar(calendarName);
    }

}

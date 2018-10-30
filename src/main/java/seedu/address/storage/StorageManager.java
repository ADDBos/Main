package seedu.address.storage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.BudgetBookChangedEvent;
import seedu.address.commons.events.model.EmailSavedEvent;
import seedu.address.commons.events.model.NewImageEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.events.storage.ImageReadingExceptionEvent;
import seedu.address.commons.events.ui.EmailViewEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.EmailModel;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyBudgetBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Room;


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
    private ProfilePictureStorage profilePictureStorage;

    public StorageManager(AddressBookStorage addressBookStorage, BudgetBookStorage budgetBookStorage,
                          UserPrefsStorage userPrefsStorage,
                          CalendarStorage calendarStorage, EmailStorage emailStorage,
                          ProfilePictureStorage profilePictureStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.budgetBookStorage = budgetBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.calendarStorage = calendarStorage;
        this.emailStorage = emailStorage;
        this.profilePictureStorage = profilePictureStorage;
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

    //@@author javenseow
    // ============== Profile Picture methods ========================

    @Override
    public Path getProfilePicturePath() {
        return profilePictureStorage.getProfilePicturePath();
    }

    @Override
    public BufferedImage readProfilePicture(File file) throws IOException {
        try {
            return profilePictureStorage.readProfilePicture(file);
        } catch (IOException e) {
            throw e;
        }
    }

    @Override
    public void saveProfilePicture(BufferedImage image, Room number) throws IOException {
        profilePictureStorage.saveProfilePicture(image, number);
    }

    @Override
    @Subscribe
    public void handleNewImageEvent(NewImageEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "reading image"));
        BufferedImage image = null;

        try {
            image = readProfilePicture(event.file);
        } catch (IOException e) {
            EventsCenter.getInstance().post(new ImageReadingExceptionEvent(e));
        }

        logger.info(LogsCenter.getEventHandlingLogMessage(event, "image read, writing file"));
        if (image != null) {
            try {
                saveProfilePicture(image, event.room);
            } catch (IOException e) {
                EventsCenter.getInstance().post(new DataSavingExceptionEvent(e));
            }
        }
    }
}

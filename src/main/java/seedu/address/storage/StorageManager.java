package seedu.address.storage;

import java.awt.image.BufferedImage;
import java.io.File;
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
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.AllDayEventAddedEvent;
import seedu.address.commons.events.model.BudgetBookChangedEvent;
import seedu.address.commons.events.model.CalendarCreatedEvent;
import seedu.address.commons.events.model.CalendarEventAddedEvent;
import seedu.address.commons.events.model.CalendarEventDeletedEvent;
import seedu.address.commons.events.model.EmailLoadedEvent;
import seedu.address.commons.events.model.EmailSavedEvent;
import seedu.address.commons.events.model.ExportAddressBookEvent;
import seedu.address.commons.events.model.LoadCalendarEvent;
import seedu.address.commons.events.model.NewImageEvent;
import seedu.address.commons.events.storage.CalendarLoadedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.events.storage.EmailDeleteEvent;
import seedu.address.commons.events.storage.EmailLoadEvent;
import seedu.address.commons.events.storage.ImageReadingExceptionEvent;
import seedu.address.commons.events.ui.EmailNotFoundEvent;
import seedu.address.commons.events.ui.EmailViewEvent;
import seedu.address.commons.events.ui.ToggleBrowserPlaceholderEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.StringUtil;
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

    //@@author kengwoon
    // ================ Export and Import methods =========================
    @Override
    @Subscribe
    public void handleExportAddressBookEvent(ExportAddressBookEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Exporting address book data"));
        try {
            exportAddressBook(event.getAddressBook(), event.getPath());
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    public void exportAddressBook(ReadOnlyAddressBook addressBook, Path path) throws IOException {
        addressBookStorage.exportAddressBook(addressBook, path);
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
    public void deleteEmail(String emailName) throws IOException {
        emailStorage.deleteEmail(emailName);
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
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Email composed, saving to directory."));
        try {
            saveEmail(event.data);
            raise(new ToggleBrowserPlaceholderEvent(ToggleBrowserPlaceholderEvent.BROWSER_PANEL));
            raise(new EmailViewEvent(event.data));
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    @Subscribe
    public void handleEmailLoadEvent(EmailLoadEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Attempting to read email from directory."));
        try {
            Email loadedEmail = loadEmail(event.data);
            raise(new EmailLoadedEvent(loadedEmail));
        } catch (IOException e) {
            logger.warning("Email file not found: " + StringUtil.getDetails(e));
            raise(new EmailNotFoundEvent(event.data));
        }
    }

    @Override
    @Subscribe
    public void handleEmailDeleteEvent(EmailDeleteEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Attempting to delete email from directory."));
        try {
            deleteEmail(event.data);
        } catch (IOException e) {
            logger.warning("Email file not found: " + StringUtil.getDetails(e));
            raise(new EmailNotFoundEvent(event.data));
        }
    }


    //@@author GilgameshTC
    // ================ Calendar methods ==============================

    @Override
    public Path getCalendarPath() {
        return calendarStorage.getCalendarPath();
    }

    /**
     * Raises an event to indicate that a calendar has been loaded.
     */
    private void indicateCalendarLoaded(Calendar calendarToBeLoaded, String calendarName) {
        raise(new CalendarLoadedEvent(calendarToBeLoaded, calendarName));
    }

    @Override
    public void createCalendar(Calendar calendar, String calendarName) throws IOException {
        calendarStorage.createCalendar(calendar, calendarName);
    }

    @Override
    public Calendar loadCalendar(String calendarName) throws IOException, ParserException {
        return calendarStorage.loadCalendar(calendarName);
    }

    @Override
    @Subscribe
    public void handleCalendarCreatedEvent(CalendarCreatedEvent event) {
        try {
            createCalendar(event.calendar, event.calendarName);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    @Subscribe
    public void handleLoadCalendarEvent(LoadCalendarEvent event) {
        try {
            Calendar calendarToBeLoaded = loadCalendar(event.calendarName);
            indicateCalendarLoaded(calendarToBeLoaded, event.calendarName);
        } catch (IOException | ParserException e) {
            logger.warning("Failed to load calendar(ics) file : " + StringUtil.getDetails(e));
        }
    }

    @Override
    @Subscribe
    public void handleAllDayEventAddedEvent(AllDayEventAddedEvent event) {
        try {
            String calendarName = event.month + "-" + event.year;
            createCalendar(event.calendar, calendarName);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    @Subscribe
    public void handleCalendarEventAddedEvent(CalendarEventAddedEvent event) {
        try {
            String calendarName = event.month + "-" + event.year;
            createCalendar(event.calendar, calendarName);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    @Subscribe
    public void handleCalendarEventDeletedEvent(CalendarEventDeletedEvent event) {
        try {
            String calendarName = event.month + "-" + event.year;
            createCalendar(event.calendar, calendarName);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
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

    public void saveProfilePicture(BufferedImage image, Room number) throws IOException {
        profilePictureStorage.saveProfilePicture(image, number);
    }

    @Subscribe
    @Override
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

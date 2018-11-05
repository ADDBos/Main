package seedu.address.storage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;

import org.simplejavamail.email.Email;

import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.AllDayEventAddedEvent;
import seedu.address.commons.events.model.BudgetBookChangedEvent;
import seedu.address.commons.events.model.CalendarCreatedEvent;
import seedu.address.commons.events.model.CalendarEventAddedEvent;
import seedu.address.commons.events.model.CalendarEventDeletedEvent;
import seedu.address.commons.events.model.EmailSavedEvent;
import seedu.address.commons.events.model.ExportAddressBookEvent;
import seedu.address.commons.events.model.LoadCalendarEvent;
import seedu.address.commons.events.model.NewImageEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.events.storage.EmailDeleteEvent;
import seedu.address.commons.events.storage.EmailLoadEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.EmailModel;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyBudgetBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Room;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, BudgetBookStorage, UserPrefsStorage, CalendarStorage,
    EmailStorage, ProfilePictureStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    Optional<ReadOnlyBudgetBook> readBudgetBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    @Override
    void exportAddressBook(ReadOnlyAddressBook addressBook, Path path) throws IOException;

    @Override
    Path getEmailPath();

    @Override
    void saveEmail(EmailModel email) throws IOException;

    @Override
    Email loadEmail(String emailName) throws IOException;

    @Override
    void deleteEmail(String emailName) throws IOException;

    @Override
    Set<String> readEmailFiles();

    /**
     * Saves the current version of the Address Book to the hard disk.
     * Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(AddressBookChangedEvent abce);

    /**
     * Saves the current version of the Budget Book to the hard disk.
     * Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     *
     * @author ericyjw
     */
    void handleBudgetBookChangedEvent(BudgetBookChangedEvent bbce);

    //@@author kengwoon

    /**
     * Saves the current version of Hallper to the specified path on hard disk.
     * Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleExportAddressBookEvent(ExportAddressBookEvent eabe);

    //@@author EatOrBeEaten
    /**
     * Saves the current Email in EmailModel to the hard disk.
     * Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleEmailSavedEvent(EmailSavedEvent ese);

    /**
     * Loads an email from the given path.
     */
    void handleEmailLoadEvent(EmailLoadEvent ele);

    /**
     * Deletes an email from the local directory.
     */
    void handleEmailDeleteEvent(EmailDeleteEvent ede);

    //@@author GilgameshTC
    @Override
    Path getCalendarPath();

    @Override
    void createCalendar(Calendar calendar, String calendarName) throws IOException;

    @Override
    Calendar loadCalendar(String calendarName) throws IOException, ParserException;

    void handleCalendarCreatedEvent(CalendarCreatedEvent event);

    void handleLoadCalendarEvent(LoadCalendarEvent event);

    void handleAllDayEventAddedEvent(AllDayEventAddedEvent event);

    void handleCalendarEventAddedEvent(CalendarEventAddedEvent event);

    void handleCalendarEventDeletedEvent(CalendarEventDeletedEvent event);

    //@@author javenseow
    @Override
    Path getProfilePicturePath();

    @Override
    BufferedImage readProfilePicture(File file) throws IOException;

    @Override
    void saveProfilePicture(BufferedImage image, Room number) throws IOException;

    /**
     * Reads and write the image file that is given from the hard disk
     * to the hard disk into a file within the project.
     */
    void handleNewImageEvent(NewImageEvent abce);
}

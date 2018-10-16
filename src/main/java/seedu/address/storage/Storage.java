package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import net.fortuna.ical4j.model.Calendar;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.BudgetBookChangedEvent;
import seedu.address.commons.events.model.EmailSavedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.EmailModel;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyBudgetBook;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, BudgetBookStorage, UserPrefsStorage, CalendarStorage,
    EmailStorage {

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
    Path getEmailPath();

    @Override
    void saveEmail(EmailModel email) throws IOException;

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

    //@@author EatOrBeEaten

    /**
     * Saves the current Email in EmailModel to the hard disk.
     * Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleEmailSavedEvent(EmailSavedEvent abce);

    //@@author GilgameshTC
    @Override
    Path getCalendarPath();

    @Override
    void createCalendar(Calendar calendar, String calendarName) throws IOException;

}

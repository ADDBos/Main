package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import org.simplejavamail.email.Email;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.CalendarCreatedEvent;
import seedu.address.commons.events.model.EmailSavedEvent;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.calendar.Month;
import seedu.address.model.calendar.Year;
import seedu.address.model.cca.Cca;
import seedu.address.model.person.Person;
import seedu.address.storage.CalendarStorage;
import seedu.address.storage.IcsCalendarStorage;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final VersionedBudgetBook versionedBudgetBook;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Cca> filteredCcas;
    private final EmailModel emailModel;
    private final UserPrefs userPrefs;
    private final CalendarModel calendarModel;

    /**
     * Initializes a ModelManager with the given addressBook, budgetBook userPrefs and calendarStorage.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyBudgetBook budgetBook, UserPrefs userPrefs,
                        CalendarStorage calendarStorage) {
        super();
        requireAllNonNull(addressBook, userPrefs, calendarStorage);

        logger.fine("Initializing with address book: " + addressBook + " , user prefs " + userPrefs
            + " and calendar: " + calendarStorage);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        versionedBudgetBook = new VersionedBudgetBook(budgetBook);
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
        filteredCcas = new FilteredList<>(versionedBudgetBook.getCcaList());
        this.userPrefs = userPrefs;
        this.emailModel = new EmailModel();
        this.calendarModel = new CalendarModel(calendarStorage, userPrefs.getExistingCalendar());
    }

    /**
     * Initializes a ModelManager with the given addressBook, userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyBudgetBook budgetBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        versionedBudgetBook = new VersionedBudgetBook(budgetBook);
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
        filteredCcas = new FilteredList<>(versionedBudgetBook.getCcaList());
        this.emailModel = new EmailModel();
        this.userPrefs = userPrefs;
        CalendarStorage calendarStorage = new IcsCalendarStorage(userPrefs.getCalendarPath());
        this.calendarModel = new CalendarModel(calendarStorage, userPrefs.getExistingCalendar());

    }

    public ModelManager() {
        this(new AddressBook(), new BudgetBook(), new UserPrefs());
    }

    public ModelManager(AddressBook addressBook, UserPrefs userPrefs) {
        versionedAddressBook = new VersionedAddressBook(addressBook);
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
        versionedBudgetBook = null;
        filteredCcas = null;
        emailModel = null;
        calendarModel = null;
        this.userPrefs = userPrefs;
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    @Override
    public ReadOnlyBudgetBook getBudgetBook() { return versionedBudgetBook; }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        versionedAddressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    //@@author kengwoon
    @Override
    public void clearMultiplePersons(List<Person> target) {
        for (Person p : target) {
            versionedAddressBook.removePerson(p);
        }
        indicateAddressBookChanged();
    }

    //@@author kengwoon
    @Override
    public void removeTagsFromPersons(List<Person> target, List<Person> original) {
        for (int i = 0; i < target.size(); i++) {
            updatePerson(original.get(i), target.get(i));
        }
        indicateAddressBookChanged();
    }

    @Override
    public void addPerson(Person person) {
        versionedAddressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedAddressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public ObservableList<Cca> getFilteredCcaList() {
        return FXCollections.unmodifiableObservableList(filteredCcas);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateFilteredCcaList(Predicate<Cca> predicate) {
        requireNonNull(predicate);
        filteredCcas.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
        indicateAddressBookChanged();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    //@@author GilgameshTC
    //=========== Calendar =================================================================================

    /**
     * Raises an event to indicate the calendar model has changed
     */
    private void indicateCalendarModelChanged() {
        raise(new CalendarCreatedEvent(calendarModel));
    }

    @Override
    public boolean isExistingCalendar(Year year, Month month) {
        requireAllNonNull(year, month);
        return calendarModel.isExistingCalendar(year, month);
    }

    @Override
    public void createCalendar(Year year, Month month) {
        try {
            calendarModel.createCalendar(year, month);
            updateExistingCalendar();
            indicateCalendarModelChanged();
        } catch (IOException e) {
            logger.warning("Failed to save calendar(ics) file : " + StringUtil.getDetails(e));
        }
    }

    @Override
    public void updateExistingCalendar() {
        userPrefs.setExistingCalendar(calendarModel.updateExistingCalendar());
    }

    //@@author
    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        if (filteredPersons == null) {
            return versionedAddressBook.equals(other.versionedAddressBook);
        } else if (calendarModel == null) {
            return versionedAddressBook.equals(other.versionedAddressBook)
                    && filteredPersons.equals(other.filteredPersons);
        }
        return versionedAddressBook.equals(other.versionedAddressBook)
            && filteredPersons.equals(other.filteredPersons)
            && calendarModel.equals(other.calendarModel);
    }

    //@@author EatOrBeEaten
    //=========== Compose email =================================================================================

    @Override
    public void saveEmail(Email email) {
        emailModel.saveEmail(email);
        indicateEmailSaved();
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateEmailSaved() {
        raise(new EmailSavedEvent(emailModel));
    }
}

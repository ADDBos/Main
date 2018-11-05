package seedu.address.model;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.simplejavamail.email.Email;

import javafx.collections.ObservableList;
import seedu.address.commons.events.model.EmailLoadedEvent;
import seedu.address.commons.events.storage.CalendarLoadedEvent;
import seedu.address.model.calendar.Month;
import seedu.address.model.calendar.Year;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Cca> PREDICATE_SHOW_ALL_CCAS = unused -> true;

    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyAddressBook newData);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns the BudgetBook
     */
    ReadOnlyBudgetBook getBudgetBook();

    /**
     * Returns a set of existing emails
     */
    Set<String> getExistingEmails();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    //@@author ericyjw
    /**
     * Returns true if a person with the same name as {@code person} exists in the address book.
     */
    boolean hasPerson(Name person);

    /**
     * Returns true if a CCA with the same CCA name as {@code Cca} exists in the budget book.
     */
    boolean hasCca(CcaName ccaName);

    /**
     * Returns true if a CCA with the same identity as {@code cca} exists in the budget book.
     */
    boolean hasCca(Cca cca);

    /**
     * Returns true if a person's CCA tag has the same name as the {@code cca} that exists in the budget book.
     */
    boolean hasCca(Person toAdd);

    //@@author
    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Clears the given persons.
     * The persons must exist in the address book.
     */
    void clearMultiplePersons(List<Person> target);

    /**
     * Removes tag(s) from given persons.
     * The persons must exist in the address book.
     */
    void removeTagsFromPersons(List<Person> target, List<Person> original);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Adds the given CCa.
     * {@code cca} must not already exist in the budget book.
     */
    void addCca(Cca cca);

    /**
     * Adds the given persons in list.
     * {@code personList} must not contain persons already exist in the address book.
     */
    void addMultiplePersons(List<Person> personList);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void updatePerson(Person target, Person editedPerson);

    //@@author ericyjw
    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void updateCca(Cca target, Cca editedCca);

    /**
     * Replaces the given persons {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void updateMultiplePersons(List<Person> target, List<Person> editedPerson);

    /**
     * Exports current data in Hallper to given {@code filePath}.
     */
    void exportAddressBook(Path filePath);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the filtered cca list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCcaList(Predicate<Cca> predicate);

    //@@author
    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();

    //@@author ericyjw
    /**
     * Saves the current budget book state for undo/redo.
     */
    void commitBudgetBook();

    /**
     * Returns an unmodifiable view of the filtered CCA list
     */
    ObservableList<Cca> getFilteredCcaList();

    //@@author
    /**
     * Saves the email to the EmailModel.
     */
    void saveEmail(Email email);

    /**
     * Saves a newly composed email to the EmailModel.
     */
    void saveComposedEmail(Email email);

    /**
     * Deletes an existing email from EmailModel.
     */
    void deleteEmail(String fileName);

    /**
     * Checks if eml file with given name exists.
     */
    boolean hasEmail(String fileName);

    /**
     * Passes the calendar loaded from memory into model
     */
    void handleCalendarLoadedEvent(CalendarLoadedEvent event);

    /**
     * Returns true if the model already has a calendar with the same month and year
     */
    boolean isExistingCalendar(Year year, Month month);

    /**
     * Checks if calendar to be edited is already loaded.
     */
    boolean isLoadedCalendar(Year year, Month month);

    /**
     * Returns true if the date is valid in that particular month, year.
     */
    boolean isValidDate(Year year, Month month, int date);

    /**
     * Returns true if the hours and minutes are valid.
     */
    boolean isValidTime(int hour, int minute);

    /**
     * Returns true if the start date is earlier than the end date.
     */
    boolean isValidTimeFrame(int startDate, int startHour, int startMinute, int endDate, int endHour, int endMinute);

    /**
     * Creates the given calendar
     * {@code year} and {@code month} must not already be an existing calendar
     */
    void createCalendar(Year year, Month month);

    /**
     * Load and parse the requested Calendar from storage.
     * {@code year} and {@code month} must already be an existing calendar
     */
    void loadCalendar(Year year, Month month);

    /**
     * Creates a new all day event in the monthly calendar specified.
     */
    void createAllDayEvent(Year year, Month month, int date, String title);

    /**
     * Creates an event in the monthly Calendar with the specified time frame.
     */
    void createEvent(Year year, Month month, int startDate, int startHour, int startMin,
                     int endDate, int endHour, int endMin, String title);

    /**
     * Checks if this specific event exists in the monthly calendar.
     */
    boolean isExistingEvent(Year year, Month month, int startDate, int endDate, String title);

    /**
     * Deletes an event in the monthly Calendar.
     */
    void deleteEvent(Year year, Month month, int startDate, int endDate, String title);

    /**
     * Updates the existing calendar map inside UserPrefs Json file
     */
    void updateExistingCalendar();

    /**
     * Saves loaded email to emailmodel
     */
    void handleEmailLoadedEvent(EmailLoadedEvent e);

    //@@author ericyjw
    /**
     * Deletes an existing CCA in the CCA list.
     */
    void deleteCca(Cca ccaToDelete);

}

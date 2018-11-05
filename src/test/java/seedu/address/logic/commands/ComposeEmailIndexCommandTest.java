package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalEmails.EXCURSION_EMAIL;
import static seedu.address.testutil.TypicalEmails.getTypicalExistingEmails;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SET;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.BudgetBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.DefaultEmailBuilder;

//@@author EatOrBeEaten
public class ComposeEmailIndexCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    private Model model = new ModelManager(getTypicalAddressBook(), new BudgetBook(), new UserPrefs(),
        getTypicalExistingEmails());

    @Test
    public void constructor_nullEmail_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ComposeEmailIndexCommand(null, INDEX_SET);
    }

    @Test
    public void constructor_nullIndexSet_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        Email validEmail = new DefaultEmailBuilder().buildWithoutTo();
        new ComposeEmailIndexCommand(validEmail, null);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Email emailToSaveWithoutTo = new DefaultEmailBuilder().buildWithoutTo();
        Email emailToSave = addIndexesToEmail(emailToSaveWithoutTo, INDEX_SET, model.getFilteredPersonList());

        ComposeEmailIndexCommand composeEmailIndexCommand = new ComposeEmailIndexCommand(emailToSaveWithoutTo,
            INDEX_SET);

        String expectedMessage = String.format(ComposeEmailIndexCommand.MESSAGE_SUCCESS,
            emailToSaveWithoutTo.getSubject());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new BudgetBook(), new UserPrefs(),
            model.getExistingEmails());
        expectedModel.saveComposedEmail(emailToSave);

        assertCommandSuccess(composeEmailIndexCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Email validEmail = new DefaultEmailBuilder().buildWithoutTo();
        ComposeEmailIndexCommand composeEmailIndexCommand =
            new ComposeEmailIndexCommand(validEmail, Set.of(outOfBoundIndex));

        assertCommandFailure(composeEmailIndexCommand, model, commandHistory,
            Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Email emailToSaveWithoutTo = new DefaultEmailBuilder().buildWithoutTo();
        Email emailToSave = addIndexesToEmail(emailToSaveWithoutTo, Set.of(INDEX_FIRST_PERSON),
            model.getFilteredPersonList());

        ComposeEmailIndexCommand composeEmailIndexCommand = new ComposeEmailIndexCommand(emailToSaveWithoutTo,
            Set.of(INDEX_FIRST_PERSON));

        String expectedMessage = String.format(ComposeEmailIndexCommand.MESSAGE_SUCCESS,
            emailToSaveWithoutTo.getSubject());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new BudgetBook(), new UserPrefs(),
            model.getExistingEmails());
        expectedModel.saveComposedEmail(emailToSave);
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        assertCommandSuccess(composeEmailIndexCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        Email validEmail = new DefaultEmailBuilder().buildWithoutTo();
        ComposeEmailIndexCommand composeEmailIndexCommand =
            new ComposeEmailIndexCommand(validEmail, Set.of(outOfBoundIndex));

        assertCommandFailure(composeEmailIndexCommand, model, commandHistory,
            Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_duplicateEmail_throwsCommandException() throws Exception {
        Email emailToSave = new DefaultEmailBuilder(EXCURSION_EMAIL).buildWithoutTo();
        Email emailToAddToModel = new DefaultEmailBuilder(EXCURSION_EMAIL).build();
        model.saveComposedEmail(emailToAddToModel);
        ComposeEmailIndexCommand composeEmailIndexCommand = new ComposeEmailIndexCommand(emailToSave,
            INDEX_SET);
        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(ComposeEmailIndexCommand.MESSAGE_DUPLICATE_EMAIL,
            emailToAddToModel.getSubject()));
        composeEmailIndexCommand.execute(model, commandHistory);
    }

    @Test
    public void equals() {
        Email meeting = new DefaultEmailBuilder().withSubject("Meeting").buildWithoutTo();
        Email conference = new DefaultEmailBuilder().withSubject("Conference").buildWithoutTo();
        ComposeEmailIndexCommand composeMeetingCommand = new ComposeEmailIndexCommand(meeting, INDEX_SET);
        ComposeEmailIndexCommand composeConferenceCommand = new ComposeEmailIndexCommand(conference, INDEX_SET);

        // same object -> returns true
        assertTrue(composeMeetingCommand.equals(composeMeetingCommand));

        // same values -> returns true
        ComposeEmailIndexCommand composeMeetingCommandCopy = new ComposeEmailIndexCommand(meeting, INDEX_SET);
        assertTrue(composeMeetingCommand.equals(composeMeetingCommandCopy));

        // different types -> returns false
        assertFalse(composeMeetingCommand.equals(1));

        // null -> returns false
        assertFalse(composeMeetingCommand.equals(null));

        // different email -> returns false
        assertFalse(composeMeetingCommand.equals(composeConferenceCommand));
    }

    /**
     * Creates an {@code Email} to selected recipients in the list.
     *
     * @param toCopy Email to copy data from.
     * @param indexSet Set of indexes to select recipients.
     * @param personList Current list of people.
     * @return Email with recipients from list.
     */
    private Email addIndexesToEmail(Email toCopy, Set<Index> indexSet, List<Person> personList) {
        final Set<String> toList = new HashSet<>();
        for (Index index : indexSet) {
            Person toPerson = personList.get(index.getZeroBased());
            toList.add(toPerson.getEmail().value);
        }
        return EmailBuilder.copying(toCopy).toMultiple(toList).buildEmail();
    }

}

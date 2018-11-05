package seedu.address.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEmails.getTypicalExistingEmails;
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
import seedu.address.logic.CommandHistory;
import seedu.address.model.BudgetBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.DefaultEmailBuilder;

//@@author EatOrBeEaten
public class ComposeEmailListCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    private Model model = new ModelManager(getTypicalAddressBook(), new BudgetBook(), new UserPrefs(),
        getTypicalExistingEmails());

    @Test
    public void constructor_nullEmail_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ComposeEmailListCommand(null);
    }

    @Test
    public void execute_nonEmptyList_success() {
        Email emailToSaveWithoutTo = new DefaultEmailBuilder().buildWithoutTo();
        Email emailToSave = addRecipientsToEmail(emailToSaveWithoutTo, model.getFilteredPersonList());

        ComposeEmailListCommand composeEmailListCommand = new ComposeEmailListCommand(emailToSaveWithoutTo);

        String expectedMessage = String.format(ComposeEmailListCommand.MESSAGE_SUCCESS,
            emailToSaveWithoutTo.getSubject());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new BudgetBook(), new UserPrefs(),
            model.getExistingEmails());
        expectedModel.saveComposedEmail(emailToSave);

        assertCommandSuccess(composeEmailListCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyList_throwsCommandException() {
        ModelManager emptyModel = new ModelManager();
        Email emailToSaveWithoutTo = new DefaultEmailBuilder().buildWithoutTo();

        ComposeEmailListCommand composeEmailListCommand = new ComposeEmailListCommand(emailToSaveWithoutTo);

        assertCommandFailure(composeEmailListCommand, emptyModel, commandHistory, Messages.MESSAGE_LIST_EMPTY);
    }

    @Test
    public void equals() {
        Email meeting = new DefaultEmailBuilder().withSubject("Meeting").buildWithoutTo();
        Email conference = new DefaultEmailBuilder().withSubject("Conference").buildWithoutTo();
        ComposeEmailListCommand composeMeetingCommand = new ComposeEmailListCommand(meeting);
        ComposeEmailListCommand composeConferenceCommand = new ComposeEmailListCommand(conference);

        // same object -> returns true
        assertTrue(composeMeetingCommand.equals(composeMeetingCommand));

        // same values -> returns true
        ComposeEmailListCommand composeMeetingCommandCopy = new ComposeEmailListCommand(meeting);
        assertTrue(composeMeetingCommand.equals(composeMeetingCommandCopy));

        // different types -> returns false
        assertFalse(composeMeetingCommand.equals(1));

        // null -> returns false
        assertFalse(composeMeetingCommand.equals(null));

        // different email -> returns false
        assertFalse(composeMeetingCommand.equals(composeConferenceCommand));
    }

    /**
     * Creates an {@code Email} to all recipients in the list.
     *
     * @param toCopy Email to copy data from.
     * @param lastShownList Current list of people.
     * @return Email with recipients from list.
     */
    private Email addRecipientsToEmail(Email toCopy, List<Person> lastShownList) {
        final Set<String> toList = new HashSet<>();
        for (Person person : lastShownList) {
            toList.add(person.getEmail().value);
        }
        return EmailBuilder.copying(toCopy).toMultiple(toList).buildEmail();
    }

}

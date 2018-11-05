package seedu.address.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEmails.getTypicalExistingEmails;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.simplejavamail.email.Email;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.BudgetBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.email.Subject;
import seedu.address.testutil.DefaultEmailBuilder;

//@@author EatOrBeEaten
public class DeleteEmailCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    private Model model = new ModelManager(getTypicalAddressBook(), new BudgetBook(), new UserPrefs(),
        getTypicalExistingEmails());

    @Test
    public void constructor_nullString_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new DeleteEmailCommand(null);
    }

    @Test
    public void execute_validSubject_success() {
        Email emailToDelete = new DefaultEmailBuilder().build();
        DeleteEmailCommand deleteEmailCommand = new DeleteEmailCommand(new Subject(emailToDelete.getSubject()));

        ModelManager modelWithEmailToDelete = new ModelManager(model.getAddressBook(), new BudgetBook(),
            new UserPrefs(), model.getExistingEmails());
        modelWithEmailToDelete.saveComposedEmail(emailToDelete);

        String expectedMessage = String.format(DeleteEmailCommand.MESSAGE_SUCCESS, emailToDelete.getSubject());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new BudgetBook(),
            new UserPrefs(), model.getExistingEmails());

        assertCommandSuccess(deleteEmailCommand, modelWithEmailToDelete,
            commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidSubject_success() {
        Email emailToDelete = new DefaultEmailBuilder().build();
        DeleteEmailCommand deleteEmailCommand = new DeleteEmailCommand(new Subject(emailToDelete.getSubject()));

        ModelManager modelWithoutEmailToDelete = new ModelManager(model.getAddressBook(), new BudgetBook(),
            new UserPrefs(), model.getExistingEmails());

        String expectedMessage = String.format(Messages.MESSAGE_EMAIL_DOES_NOT_EXIST, emailToDelete.getSubject());

        assertCommandFailure(deleteEmailCommand, modelWithoutEmailToDelete, commandHistory, expectedMessage);
    }

    @Test
    public void equals() {
        Subject meetingSubject = new Subject("Meeting");
        Subject conferenceSubject = new Subject("Conference");
        DeleteEmailCommand deleteMeetingCommand = new DeleteEmailCommand(meetingSubject);
        DeleteEmailCommand deleteConferenceCommand = new DeleteEmailCommand(conferenceSubject);

        // same object -> returns true
        assertTrue(deleteMeetingCommand.equals(deleteMeetingCommand));

        // same values -> returns true
        DeleteEmailCommand deleteMeetingCommandCopy = new DeleteEmailCommand(meetingSubject);
        assertTrue(deleteMeetingCommand.equals(deleteMeetingCommandCopy));

        // different types -> returns false
        assertFalse(deleteMeetingCommand.equals(1));

        // null -> returns false
        assertFalse(deleteMeetingCommand.equals(null));

        // different subject -> returns false
        assertFalse(deleteMeetingCommand.equals(deleteConferenceCommand));
    }
}

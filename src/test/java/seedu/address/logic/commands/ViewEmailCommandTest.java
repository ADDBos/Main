package seedu.address.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEmails.EXCURSION_EMAIL;
import static seedu.address.testutil.TypicalEmails.MEETING_EMAIL;
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

//@@author EatOrBeEaten
public class ViewEmailCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    private Model model = new ModelManager(getTypicalAddressBook(), new BudgetBook(), new UserPrefs(),
        getTypicalExistingEmails());

    @Test
    public void constructor_nullString_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ViewEmailCommand(null);
    }

    @Test
    public void execute_validSubject_success() {
        Email emailToView = MEETING_EMAIL;

        ViewEmailCommand viewEmailCommand = new ViewEmailCommand(new Subject(emailToView.getSubject()));

        String expectedMessage = String.format(ViewEmailCommand.MESSAGE_SUCCESS, emailToView.getSubject());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new BudgetBook(), new UserPrefs(),
            model.getExistingEmails());
        expectedModel.saveEmail(emailToView);

        assertCommandSuccess(viewEmailCommand, model, commandHistory, expectedMessage, expectedModel);

    }

    @Test
    public void execute_invalidSubject_success() {
        Email emailToView = EXCURSION_EMAIL;

        ViewEmailCommand viewEmailCommand = new ViewEmailCommand(new Subject(emailToView.getSubject()));

        String expectedMessage = String.format(Messages.MESSAGE_EMAIL_DOES_NOT_EXIST, emailToView.getSubject());

        assertCommandFailure(viewEmailCommand, model, commandHistory, expectedMessage);

    }

    @Test
    public void equals() {
        Subject meetingSubject = new Subject("Meeting");
        Subject conferenceSubject = new Subject("Conference");
        ViewEmailCommand viewMeetingCommand = new ViewEmailCommand(meetingSubject);
        ViewEmailCommand viewConferenceCommand = new ViewEmailCommand(conferenceSubject);

        // same object -> returns true
        assertTrue(viewMeetingCommand.equals(viewMeetingCommand));

        // same values -> returns true
        ViewEmailCommand viewMeetingCommandCopy = new ViewEmailCommand(meetingSubject);
        assertTrue(viewMeetingCommand.equals(viewMeetingCommandCopy));

        // different types -> returns false
        assertFalse(viewMeetingCommand.equals(1));

        // null -> returns false
        assertFalse(viewMeetingCommand.equals(null));

        // different subject -> returns false
        assertFalse(viewMeetingCommand.equals(viewConferenceCommand));
    }
}

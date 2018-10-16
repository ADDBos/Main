package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.simplejavamail.email.Email;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.testutil.DefaultEmailBuilder;

//@@author EatOrBeEaten
public class ComposeCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    private Model model = new ModelManager();

    @Test
    public void constructor_nullEmail_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ComposeCommand(null);
    }

    @Test
    public void execute_emailAccepted_composeSuccessful() throws Exception {
        Email validEmail = new DefaultEmailBuilder().build();

        CommandResult commandResult = new ComposeCommand(validEmail).execute(model, commandHistory);

        assertEquals(String.format(ComposeCommand.MESSAGE_SUCCESS, validEmail.getSubject()),
                commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void equals() {
        Email meeting = new DefaultEmailBuilder().withSubject("Meeting").build();
        Email conference = new DefaultEmailBuilder().withSubject("Conference").build();
        ComposeCommand composeMeetingCommand = new ComposeCommand(meeting);
        ComposeCommand composeConferenceCommand = new ComposeCommand(conference);

        // same object -> returns true
        assertTrue(composeMeetingCommand.equals(composeMeetingCommand));

        // same values -> returns true
        ComposeCommand composeMeetingCommandCopy = new ComposeCommand(meeting);
        assertTrue(composeMeetingCommand.equals(composeMeetingCommandCopy));

        // different types -> returns false
        assertFalse(composeMeetingCommand.equals(1));

        // null -> returns false
        assertFalse(composeMeetingCommand.equals(null));

        // different person -> returns false
        assertFalse(composeMeetingCommand.equals(composeConferenceCommand));
    }

}

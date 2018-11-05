package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Set;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ListEmailsEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all emails on the computer to the user.
 */
public class ListEmailsCommand extends Command {

    public static final String COMMAND_WORD = "list_emails";

    public static final String MESSAGE_SUCCESS = "Listed all emails";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        Set<String> existingEmails = model.getExistingEmails();
        EventsCenter.getInstance().post(new ListEmailsEvent(existingEmails));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

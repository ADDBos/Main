package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_EMAIL_DOES_NOT_EXIST;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.storage.EmailLoadEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.email.Subject;

//@@author EatOrBeEaten

/**
 * Loads an email from the computer
 */
public class ViewEmailCommand extends Command {

    public static final String COMMAND_WORD = "view_email";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Loads the email identified by its subject.\n"
        + "Parameters SUBJECT\n"
        + "Example: " + COMMAND_WORD + " Meeting on Friday";

    public static final String MESSAGE_SUCCESS = "Email loaded: %s";

    private final Subject subject;

    public ViewEmailCommand(Subject subject) {
        requireNonNull(subject);
        this.subject = subject;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        requireNonNull(model);

        if (!model.hasEmail(subject.value)) {
            throw new CommandException(String.format(MESSAGE_EMAIL_DOES_NOT_EXIST, subject.value));
        }

        EventsCenter.getInstance().post(new EmailLoadEvent(subject.value));
        return new CommandResult(String.format(MESSAGE_SUCCESS, subject.value));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ViewEmailCommand // instanceof handles nulls
            && subject.equals(((ViewEmailCommand) other).subject)); // state check
    }
}

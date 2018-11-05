package seedu.address.logic.commands;

//@author EatOrBeEaten

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_EMAIL_DOES_NOT_EXIST;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.email.Subject;

/**
 * Deletes an Email from the computer.
 */
public class DeleteEmailCommand extends Command {

    public static final String COMMAND_WORD = "delete_email";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the email identified by its subject.\n"
        + "Parameters SUBJECT\n"
        + "Example: " + COMMAND_WORD + " Meeting on Friday";

    public static final String MESSAGE_SUCCESS = "Email deleted: %s";

    private final Subject subject;

    public DeleteEmailCommand(Subject subject) {
        requireNonNull(subject);
        this.subject = subject;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        requireNonNull(model);

        if (!model.hasEmail(subject.value)) {
            throw new CommandException(String.format(MESSAGE_EMAIL_DOES_NOT_EXIST, subject.value));
        }

        model.deleteEmail(subject.value);
        return new CommandResult(String.format(MESSAGE_SUCCESS, subject.value));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeleteEmailCommand // instanceof handles nulls
            && subject.equals(((DeleteEmailCommand) other).subject)); // state check
    }
}

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.DeleteEmailCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.email.Subject;

//@@author EatOrBeEaten

/**
 * Parses input arguments and creates a new DeleteEmailCommand object
 */
public class DeleteEmailCommandParser implements Parser<DeleteEmailCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteEmailCommand
     * and returns a DeleteEmailCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteEmailCommand parse(String args) throws ParseException {
        try {
            Subject subject = ParserUtil.parseSubject(args);
            return new DeleteEmailCommand(subject);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEmailCommand.MESSAGE_USAGE), pe);
        }
    }
}

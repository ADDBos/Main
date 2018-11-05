package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ViewEmailCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.email.Subject;

//@@author EatOrBeEaten

/**
 * Parses input arguments and creates a new ViewEmailCommand object
 */
public class ViewEmailCommandParser implements Parser<ViewEmailCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewEmailCommand
     * and returns an ViewEmailCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewEmailCommand parse(String args) throws ParseException {
        try {
            Subject subject = ParserUtil.parseSubject(args);
            return new ViewEmailCommand(subject);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewEmailCommand.MESSAGE_USAGE), pe);
        }
    }
}

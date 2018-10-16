package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.EraseCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author kengwoon
/**
 * Parses input arguments and creates a new ClearCommand object
 */
public class EraseCommandParser implements Parser<EraseCommand> {

    /**
     * Parses the given {@code String} argument in the context of the EraseCommand
     * and returns an EraseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EraseCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EraseCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new EraseCommand(Arrays.asList(nameKeywords));
    }

}

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ImageCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Room;

/**
 * Parses input arguments and creates a new ImageCommand object.
 */
//@@author javenseow
public class ImageCommandParser implements Parser<ImageCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ImageCommand
     * and returns an ImageCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public ImageCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImageCommand.MESSAGE_USAGE));
        }

        Room room = ParserUtil.parseRoom(trimmedArgs);
        return new ImageCommand(room);
    }
}

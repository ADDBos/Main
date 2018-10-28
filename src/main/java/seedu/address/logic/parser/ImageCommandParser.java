package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM;

import java.io.File;
import java.util.stream.Stream;

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
        ArgumentMultimap argMultiMap =
                ArgumentTokenizer.tokenize(args, PREFIX_ROOM, PREFIX_FILE);

        if (!arePrefixesPresent(argMultiMap, PREFIX_ROOM, PREFIX_FILE) || !argMultiMap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImageCommand.MESSAGE_USAGE));
        }

        Room room = ParserUtil.parseRoom(argMultiMap.getValue(PREFIX_ROOM).get());
        File file = ParserUtil.parseImage(argMultiMap.getValue(PREFIX_FILE).get());

        return new ImageCommand(room, file);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

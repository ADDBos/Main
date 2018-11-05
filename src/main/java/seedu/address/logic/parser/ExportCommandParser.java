package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESTINATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILENAME;

import java.nio.file.Path;
import java.util.stream.Stream;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author kengwoon

/**
 * Parses input arguments and creates a new ImportCommand object
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the Exportommand
     * and returns an ExportCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ExportCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_DESTINATION, PREFIX_FILENAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_DESTINATION, PREFIX_FILENAME)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }

        Path path = ParserUtil.parsePath(argMultimap.getValue(PREFIX_DESTINATION).get(),
            argMultimap.getValue(PREFIX_FILENAME).get());

        return new ExportCommand(path);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

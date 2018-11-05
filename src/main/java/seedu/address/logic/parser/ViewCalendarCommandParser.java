package seedu.address.logic.parser;

//@@author GilgameshTC

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.stream.Stream;

import seedu.address.logic.commands.ViewCalendarCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.calendar.Month;
import seedu.address.model.calendar.Year;

/**
 * Parses input arguments and creates a new ViewCalendarCommand object.
 */
public class ViewCalendarCommandParser implements Parser<ViewCalendarCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCalendarCommand
     * and returns an ViewCalendarCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCalendarCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_MONTH, PREFIX_YEAR);

        if (!arePrefixesPresent(argMultimap, PREFIX_MONTH, PREFIX_YEAR)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCalendarCommand.MESSAGE_USAGE));
        }

        Month month = ParserUtil.parseMonth(argMultimap.getValue(PREFIX_MONTH).get());
        Year year = ParserUtil.parseYear(argMultimap.getValue(PREFIX_YEAR).get());

        return new ViewCalendarCommand(month, year);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

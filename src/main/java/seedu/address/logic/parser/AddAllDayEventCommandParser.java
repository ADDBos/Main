package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddAllDayEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.calendar.Month;
import seedu.address.model.calendar.Year;

//@@author GilgameshTC
/**
 * Parses input arguments and creates a new AddAllDayEventCommand object.
 */
public class AddAllDayEventCommandParser implements Parser<AddAllDayEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddAllDayEventCommand
     * and returns an AddAllDayEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAllDayEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MONTH, PREFIX_YEAR, PREFIX_DATE, PREFIX_TITLE);

        if (!arePrefixesPresent(argMultimap, PREFIX_MONTH, PREFIX_YEAR, PREFIX_DATE, PREFIX_TITLE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAllDayEventCommand.MESSAGE_USAGE));
        }

        Month month = ParserUtil.parseMonth(argMultimap.getValue(PREFIX_MONTH).get());
        Year year = ParserUtil.parseYear(argMultimap.getValue(PREFIX_YEAR).get());
        int date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        String title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get());

        return new AddAllDayEventCommand(month, year, date, title);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

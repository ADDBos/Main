package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_MIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_MIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.calendar.Month;
import seedu.address.model.calendar.Year;

//@@author GilgameshTC
/**
 * Parses input arguments and creates a new AddEventCommand object.
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddEventCommand
     * and returns an AddEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MONTH, PREFIX_YEAR, PREFIX_START_DATE, PREFIX_START_HOUR,
                        PREFIX_START_MIN, PREFIX_END_DATE, PREFIX_END_HOUR, PREFIX_END_MIN, PREFIX_TITLE);

        if (!arePrefixesPresent(argMultimap, PREFIX_MONTH, PREFIX_YEAR, PREFIX_START_DATE, PREFIX_START_HOUR,
                PREFIX_START_MIN, PREFIX_END_DATE, PREFIX_END_HOUR, PREFIX_END_MIN, PREFIX_TITLE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        Month month = ParserUtil.parseMonth(argMultimap.getValue(PREFIX_MONTH).get());
        Year year = ParserUtil.parseYear(argMultimap.getValue(PREFIX_YEAR).get());
        int startDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_START_DATE).get());
        int startHour = ParserUtil.parseHour(argMultimap.getValue(PREFIX_START_HOUR).get());
        int startMin = ParserUtil.parseMinute(argMultimap.getValue(PREFIX_START_MIN).get());
        int endDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_END_DATE).get());
        int endHour = ParserUtil.parseHour(argMultimap.getValue(PREFIX_END_HOUR).get());
        int endMin = ParserUtil.parseMinute(argMultimap.getValue(PREFIX_END_MIN).get());
        String title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get());

        return new AddEventCommand(month, year, startDate, startHour, startMin, endDate, endHour, endMin, title);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

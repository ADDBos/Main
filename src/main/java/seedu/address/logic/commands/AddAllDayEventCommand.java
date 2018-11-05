package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.calendar.Month;
import seedu.address.model.calendar.Year;

//@@author GilgameshTC
/**
 * Adds a new all day event into the monthly calendar specified by user.
 */
public class AddAllDayEventCommand extends Command {
    public static final String COMMAND_WORD = "add_all_day_event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new all day event into the calendar. "
            + "Parameters: "
            + PREFIX_MONTH + "MMM "
            + PREFIX_YEAR + "YYYY "
            + PREFIX_DATE + "DD "
            + PREFIX_TITLE + "Description\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MONTH + "OCT "
            + PREFIX_YEAR + "2018 "
            + PREFIX_DATE + "25 "
            + PREFIX_TITLE + "Hall Open Day ";

    public static final String MESSAGE_SUCCESS = "All day event added: %s";
    public static final String MESSAGE_NOT_EXISTING_CALENDAR = "This calendar doesn't exist in Hallper";
    public static final String MESSAGE_NOT_VALID_DATE = "This is not a valid date in %s";
    public static final String MESSAGE_EXISTING_EVENT = "This event already exist in the calendar: %s";

    private final Month month;
    private final Year year;
    private final int date;
    private final String title;

    public AddAllDayEventCommand(Month month, Year year, int date, String title) {
        this.month = month;
        this.year = year;
        this.date = date;
        this.title = title;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.isExistingCalendar(year, month)) {
            throw new CommandException(MESSAGE_NOT_EXISTING_CALENDAR);
        }

        if (!model.isValidDate(year, month, date)) {
            throw new CommandException(String.format(MESSAGE_NOT_VALID_DATE, month + " - " + year));
        }

        // Check whether calendar is already loaded
        if (!model.isLoadedCalendar(year, month)) {
            model.loadCalendar(year, month);
        }

        // Check whether event exists in calendar
        if (model.isExistingEvent(year, month, date, date, title)) {
            return new CommandResult(String.format(MESSAGE_EXISTING_EVENT, month + "-" + year));
        }

        model.createAllDayEvent(year, month, date, title);
        return new CommandResult(String.format(MESSAGE_SUCCESS, date + "/" + month + "/" + year + " - " + title));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAllDayEventCommand // instanceof handles nulls
                && month.equals(((AddAllDayEventCommand) other).month)
                && year.equals(((AddAllDayEventCommand) other).year)
                && title.equals(((AddAllDayEventCommand) other).title)
                && date == ((AddAllDayEventCommand) other).date);
    }
}

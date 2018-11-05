package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_MIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_MIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.calendar.Month;
import seedu.address.model.calendar.Year;

//@@author GilgameshTC
/**
 * Adds a new event with the specified time frame into the monthly calendar specified by user.
 */
public class AddEventCommand extends Command {
    public static final String COMMAND_WORD = "add_event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new event into the calendar. "
            + "Parameters: "
            + PREFIX_MONTH + "MMM "
            + PREFIX_YEAR + "YYYY "
            + PREFIX_START_DATE + "DD "
            + PREFIX_START_HOUR + "HH(24 hour format) "
            + PREFIX_START_MIN + "mm "
            + PREFIX_END_DATE + "DD "
            + PREFIX_END_HOUR + "HH(24 hour format) "
            + PREFIX_END_MIN + "mm "
            + PREFIX_TITLE + "Description\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MONTH + "JUL "
            + PREFIX_YEAR + "2018 "
            + PREFIX_START_DATE + "21 "
            + PREFIX_START_HOUR + "08 "
            + PREFIX_START_MIN + "00 "
            + PREFIX_END_DATE + "24 "
            + PREFIX_END_HOUR + "17 "
            + PREFIX_END_MIN + "00 "
            + PREFIX_TITLE + "Hall Exposure Camp ";

    public static final String MESSAGE_SUCCESS = "Calendar event added: %s";
    public static final String MESSAGE_NOT_EXISTING_CALENDAR = "This calendar doesn't exist in Hallper";
    public static final String MESSAGE_NOT_VALID_DATE = "This is not a valid date %s";
    public static final String MESSAGE_NOT_VALID_TIME = "This is not a valid time %s";
    public static final String MESSAGE_NOT_VALID_TIMEFRAME = "End Date should not be earlier than Start Date";
    public static final String MESSAGE_EXISTING_EVENT = "This event already exist in the calendar: %s";

    private final Month month;
    private final Year year;
    private final int startDate;
    private final int startHour;
    private final int startMin;
    private final int endDate;
    private final int endHour;
    private final int endMin;
    private final String title;

    public AddEventCommand(Month month, Year year, int startDate, int startHour, int startMin,
                           int endDate, int endHour, int endMin, String title) {
        this.month = month;
        this.year = year;
        this.startDate = startDate;
        this.startHour = startHour;
        this.startMin = startMin;
        this.endDate = endDate;
        this.endHour = endHour;
        this.endMin = endMin;
        this.title = title;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.isExistingCalendar(year, month)) {
            throw new CommandException(MESSAGE_NOT_EXISTING_CALENDAR);
        }

        // Check whether startDate is valid
        if (!model.isValidDate(year, month, startDate)) {
            throw new CommandException(String.format(MESSAGE_NOT_VALID_DATE, startDate + "/" + month + "/" + year));
        }

        // Check whether startTime is valid
        if (!model.isValidTime(startHour, startMin)) {
            throw new CommandException(String.format(MESSAGE_NOT_VALID_TIME, startHour + ":" + startMin));
        }

        // Check whether endDate is valid
        if (!model.isValidDate(year, month, endDate)) {
            throw new CommandException(String.format(MESSAGE_NOT_VALID_DATE, endDate + "/" + month + "/" + year));
        }

        // Check whether endTime is valid
        if (!model.isValidTime(endHour, endMin)) {
            throw new CommandException(String.format(MESSAGE_NOT_VALID_TIME, endHour + ":" + endMin));
        }

        // Check whether start date and end date is a valid time frame
        if (!model.isValidTimeFrame(startDate, startHour, startMin, endDate, endHour, endMin)) {
            throw new CommandException(MESSAGE_NOT_VALID_TIMEFRAME);
        }

        // Check whether calendar is already loaded
        if (!model.isLoadedCalendar(year, month)) {
            model.loadCalendar(year, month);
        }

        // Check whether event exists in calendar
        if (model.isExistingEvent(year, month, startDate, endDate, title)) {
            return new CommandResult(String.format(MESSAGE_EXISTING_EVENT, month + "-" + year));
        }

        model.createEvent(year, month, startDate, startHour, startMin, endDate, endHour, endMin, title);
        return new CommandResult(String.format(MESSAGE_SUCCESS, startDate + "/" + month + "/" + year
                + " - " + endDate + "/" + month + "/" + year + " [" + title + "]"));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEventCommand // instanceof handles nulls
                && month.equals(((AddEventCommand) other).month)
                && year.equals(((AddEventCommand) other).year)
                && startDate == (((AddEventCommand) other).startDate)
                && startHour == (((AddEventCommand) other).startHour)
                && startMin == (((AddEventCommand) other).startMin)
                && endDate == (((AddEventCommand) other).endDate)
                && endHour == (((AddEventCommand) other).endHour)
                && endMin == (((AddEventCommand) other).endMin)
                && title.equals(((AddEventCommand) other).title));
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.calendar.Month;
import seedu.address.model.calendar.Year;

//@@author GilgameshTC
/**
 * Deletes an event with the title and start - end date in the monthly calendar specified by user.
 */
public class DeleteEventCommand extends Command {
    public static final String COMMAND_WORD = "delete_event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an event from the calendar. "
            + "Parameters: "
            + PREFIX_MONTH + "MMM "
            + PREFIX_YEAR + "YYYY "
            + PREFIX_START_DATE + "DD "
            + PREFIX_END_DATE + "DD "
            + PREFIX_TITLE + "Description\n"
            + "[An all day event will have the same start date and end date]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MONTH + "JUL "
            + PREFIX_YEAR + "2018 "
            + PREFIX_START_DATE + "21 "
            + PREFIX_END_DATE + "24 "
            + PREFIX_TITLE + "Hall Exposure Camp ";

    public static final String MESSAGE_SUCCESS = "Calendar event deleted: %s";
    public static final String MESSAGE_NOT_EXISTING_CALENDAR = "This calendar doesn't exist in Hallper";
    public static final String MESSAGE_NOT_VALID_DATE = "This is not a valid date %s";
    public static final String MESSAGE_NOT_EXISTING_EVENT = "This event does not exist in the calendar: %s";

    private final Month month;
    private final Year year;
    private final int startDate;
    private final int endDate;
    private final String title;

    public DeleteEventCommand(Month month, Year year, int startDate, int endDate, String title) {
        this.month = month;
        this.year = year;
        this.startDate = startDate;
        this.endDate = endDate;
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

        // Check whether endDate is valid
        if (!model.isValidDate(year, month, endDate)) {
            throw new CommandException(String.format(MESSAGE_NOT_VALID_DATE, endDate + "/" + month + "/" + year));
        }

        // Check whether calendar is already loaded
        if (!model.isLoadedCalendar(year, month)) {
            model.loadCalendar(year, month);
        }

        // Check whether event exists in calendar
        if (!model.isExistingEvent(year, month, startDate, endDate, title)) {
            return new CommandResult(String.format(MESSAGE_NOT_EXISTING_EVENT, month + "-" + year));
        }

        model.deleteEvent(year, month, startDate, endDate, title);
        return new CommandResult(String.format(MESSAGE_SUCCESS, startDate + "/" + month + "/" + year
                + " - " + endDate + "/" + month + "/" + year + " [" + title + "]"));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteEventCommand // instanceof handles nulls
                && month.equals(((DeleteEventCommand) other).month)
                && year.equals(((DeleteEventCommand) other).year)
                && startDate == (((DeleteEventCommand) other).startDate)
                && endDate == (((DeleteEventCommand) other).endDate)
                && title.equals(((DeleteEventCommand) other).title));
    }
}

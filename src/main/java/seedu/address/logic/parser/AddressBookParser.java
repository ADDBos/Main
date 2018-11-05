package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddAllDayEventCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.AddTransactionCommand;
import seedu.address.logic.commands.BudgetCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ComposeEmailIndexCommand;
import seedu.address.logic.commands.ComposeEmailListCommand;
import seedu.address.logic.commands.CreateCalendarCommand;
import seedu.address.logic.commands.CreateCcaCommand;
import seedu.address.logic.commands.DeleteCcaCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteEmailCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.DeleteTransactionCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EraseCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ImageCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListEmailsCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.commands.ViewCalendarCommand;
import seedu.address.logic.commands.ViewEmailCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommandParser().parse(arguments);

        case EraseCommand.COMMAND_WORD:
            return new EraseCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ImageCommand.COMMAND_WORD:
            return new ImageCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ListEmailsCommand.COMMAND_WORD:
            return new ListEmailsCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case SearchCommand.COMMAND_WORD:
            return new SearchCommandParser().parse(arguments);

        case BudgetCommand.COMMAND_WORD:
            return new BudgetCommandParser().parse(arguments);

        case ComposeEmailIndexCommand.COMMAND_WORD:
            return new ComposeEmailIndexCommandParser().parse(arguments);

        case ComposeEmailListCommand.COMMAND_WORD:
            return new ComposeEmailListCommandParser().parse(arguments);

        case ViewEmailCommand.COMMAND_WORD:
            return new ViewEmailCommandParser().parse(arguments);

        case DeleteEmailCommand.COMMAND_WORD:
            return new DeleteEmailCommandParser().parse(arguments);

        case CreateCalendarCommand.COMMAND_WORD:
            return new CreateCalendarCommandParser().parse(arguments);

        case CreateCcaCommand.COMMAND_WORD:
            return new CreateCcaCommandParser().parse(arguments);

        case UpdateCommand.COMMAND_WORD:
            return new UpdateCommandPaser().parse(arguments);

        case ImportCommand.COMMAND_WORD:
            return new ImportCommandParser().parse(arguments);

        case ExportCommand.COMMAND_WORD:
            return new ExportCommandParser().parse(arguments);

        case AddAllDayEventCommand.COMMAND_WORD:
            return new AddAllDayEventCommandParser().parse(arguments);

        case AddEventCommand.COMMAND_WORD:
            return new AddEventCommandParser().parse(arguments);

        case DeleteEventCommand.COMMAND_WORD:
            return new DeleteEventCommandParser().parse(arguments);

        case ViewCalendarCommand.COMMAND_WORD:
            return new ViewCalendarCommandParser().parse(arguments);

        case DeleteCcaCommand.COMMAND_WORD:
            return new DeleteCcaCommandParser().parse(arguments);

        case AddTransactionCommand.COMMAND_WORD:
            return new AddTransactionCommandPaser().parse(arguments);

        case DeleteTransactionCommand.COMMAND_WORD:
            return new DeleteTransactionCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}

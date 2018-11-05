package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

//@@author kengwoon

/**
 * Exports an XML file of current contacts in Hallper.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports an XML file of current contacts in Hallper.\n "
        + "Parameters: "
        + "export "
        + "dst/C://Users/Documents "
        + "fn/FILENAME.xml";

    public static final String MESSAGE_SUCCESS = "Contacts successfully exported to %1$s.";

    private final Path path;


    /**
     * Creates an ExportCommand to export the specified file.
     */
    public ExportCommand(Path path) {
        requireNonNull(path);
        this.path = path;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.exportAddressBook(path);
        return new CommandResult(String.format(MESSAGE_SUCCESS, path));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ExportCommand // instanceof handles nulls
            && path.equals(((ExportCommand) other).path));
    }

}

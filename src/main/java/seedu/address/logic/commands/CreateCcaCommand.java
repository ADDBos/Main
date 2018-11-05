package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cca.Cca;

//@@author ericyjw
/**
 * Adds a cca to the cca book.
 *
 * @author ericyjw
 */
public class CreateCcaCommand extends Command {
    public static final String COMMAND_WORD = "create";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Create a new CCA to the address book.\n"
        + "Parameters: "
        + PREFIX_NAME + "CCA NAME "
        + PREFIX_BUDGET + "BUDGET \n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "Basketball "
        + PREFIX_BUDGET + "500";
    public static final String MESSAGE_SUCCESS = "New CCA added: %1$s";
    public static final String MESSAGE_DUPLICATE_CCA = "This CCA already exists in the budget book";

    private final Cca toAdd;

    /**
     * Creates an CreateCcaCommand to add the specified {@code Cca}
     *
     * @param cca the cca to be added
     */
    public CreateCcaCommand(Cca cca) {
        requireNonNull(cca);
        toAdd = cca;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasCca(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CCA);
        }

        model.addCca(toAdd);
        model.commitBudgetBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof CreateCcaCommand // instanceof handles nulls
            && toAdd.equals(((CreateCcaCommand) other).toAdd));
    }
}

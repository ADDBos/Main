package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.UpdateCommand.MESSAGE_NON_EXISTENT_CCA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CCAS;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;

//@@author ericyjw
/**
 * Deletes a CCA from the cca book.
 *
 * @author ericyjw
 */
public class DeleteCcaCommand extends Command {
    public static final String COMMAND_WORD = "delete_cca";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the CCA specified\n"
            + "Parameters: "
            + PREFIX_TAG + "CCA \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TAG + " basketball";
    public static final String MESSAGE_DELETE_CCA_SUCCESS = "Deleted CCA: %1$s";

    private final CcaName targetCca;

    /**
     * Creates a DeleteCcaCommand to delete the {@code Cca}
     * @param ccaName the name of the Cca to be deleted
     */
    public DeleteCcaCommand(CcaName ccaName) {
        this.targetCca = ccaName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Cca> lastShownList = model.getFilteredCcaList();
        if (!model.hasCca(targetCca)) {
            throw new CommandException(MESSAGE_NON_EXISTENT_CCA);
        }

        int index = 0;
        for (Cca c : lastShownList) {
            if (c.getCcaName().equals(targetCca.getNameOfCca())) {
                break;
            }
            index++;
        }

        Cca ccaToDelete = lastShownList.get(index);

        model.deleteCca(ccaToDelete);
        model.updateFilteredCcaList(PREDICATE_SHOW_ALL_CCAS);
        model.commitBudgetBook();
        return new CommandResult(String.format(MESSAGE_DELETE_CCA_SUCCESS, ccaToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCcaCommand // instanceof handles nulls
                && targetCca.equals(((DeleteCcaCommand) other).targetCca)); // state check
    }
}

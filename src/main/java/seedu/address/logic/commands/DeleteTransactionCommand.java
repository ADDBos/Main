package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.UpdateCommand.MESSAGE_NON_EXISTENT_CCA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CCAS;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.TransactionMath;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;
import seedu.address.model.transaction.Entry;

//@@author ericyjw

/**
 * Deletes a Cca transaction entry from the cca book.
 *
 * @author ericyjw
 */
public class DeleteTransactionCommand extends Command {
    public static final String COMMAND_WORD = "delete_trans";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the specified transaction entry of the specified CCA\n"
            + "Parameters: "
            + PREFIX_TAG + "CCA "
            + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TAG + " basketball 2";
    public static final String MESSAGE_DELETE_TRANSACTION_SUCCESS = "Deleted Cca Transaction Entry: %1$s";

    private final CcaName targetCca;
    private final Integer entryIndex;

    /**
     * Creates a DeleteTransactionCommand to delete the transaction entry of a {@code Cca}
     * @param ccaName the name of the Cca to have one of its transaction entry deleted
     * @param index the index of the transaction entry to be deleted
     */
    public DeleteTransactionCommand(CcaName ccaName, Integer index) {
        this.targetCca = ccaName;
        this.entryIndex = index;
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

        Cca ccaToUpdate = lastShownList.get(index);
        Entry entryToBeDeleted = ccaToUpdate.getEntry(entryIndex);
        Cca updatedCca = ccaToUpdate.removeTransaction(entryToBeDeleted);
        updatedCca = TransactionMath.updateDetails(updatedCca);


        model.updateCca(ccaToUpdate, updatedCca);
        model.updateFilteredCcaList(PREDICATE_SHOW_ALL_CCAS);
        model.commitBudgetBook();
        return new CommandResult(String.format(MESSAGE_DELETE_TRANSACTION_SUCCESS, entryToBeDeleted));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTransactionCommand // instanceof handles nulls
                && targetCca.equals(((DeleteTransactionCommand) other).targetCca)); // state check
    }
}

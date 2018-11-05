package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CCAS;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.TransactionMath;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Date;
import seedu.address.model.transaction.Entry;
import seedu.address.model.transaction.Remarks;

//@@author ericyjw
/**
 * Update the details of a CCA.
 *
 * @author ericyjw
 */
public class AddTransactionCommand extends Command {
    public static final String COMMAND_WORD = "add_trans";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add a new transaction to the specified Cca\n"
        + "Parameters: "
        + PREFIX_TAG + "CCA "
        + PREFIX_DATE + "DD.MM.YYYY "
        + PREFIX_AMOUNT + "AMOUNT"
        + PREFIX_REMARKS + "REMARKS \n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_TAG + "basketball "
        + PREFIX_DATE + "13.12.2018 "
        + PREFIX_AMOUNT + "-100"
        + PREFIX_REMARKS + "Purchase of equipment \n";

    public static final String MESSAGE_UPDATE_SUCCESS = "Transaction added: %1$s";
    public static final String MESSAGE_NOT_UPDATED = "All the fields must be provided.";
    public static final String MESSAGE_NON_EXISTENT_CCA = "The CCA does not exist. Please create the CCA before. "
        + "adding its transaction";
    public static final String MESSAGE_NO_SPECIFIC_CCA = "There is no CCA specified. Please use " + PREFIX_TAG + "to "
        + "indicate the CCA.";

    private final CcaName cca;
    private final Date date;
    private final Amount amount;
    private final Remarks remarks;

    /**
     * Creates a {@code AddTransactionCommand} to add a transaction entry to the specified {@code Cca}.
     *
     * @param ccaName name of the Cca that the transaction entry is to be added to
     * @param date the date of the transaction entry
     * @param amount the amount involved in the transaction entry
     * @param remarks the remarks given to the transaction entry
     */
    public AddTransactionCommand(CcaName ccaName, Date date, Amount amount, Remarks remarks) {
        requireAllNonNull(ccaName, date, amount, remarks);

        this.cca = ccaName;
        this.date = date;
        this.amount = amount;
        this.remarks = remarks;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Cca> lastShownList = model.getFilteredCcaList();

        if (!model.hasCca(cca)) {
            throw new CommandException(MESSAGE_NON_EXISTENT_CCA);
        }

        int index = 0;
        for (Cca c : lastShownList) {
            if (c.getCcaName().equals(cca.getNameOfCca())) {
                break;
            }
            index++;
        }

        Cca ccaToUpdate = lastShownList.get(index);
        int entryNum = ccaToUpdate.getEntrySize() + 1;
        Entry newEntry = new Entry (entryNum, this.date, this.amount, this.remarks);
        Cca updatedCca = ccaToUpdate.addNewTransaction(newEntry);
        updatedCca = TransactionMath.updateDetails(updatedCca);

        model.updateCca(ccaToUpdate, updatedCca);
        model.updateFilteredCcaList(PREDICATE_SHOW_ALL_CCAS);
        model.commitBudgetBook();
        return new CommandResult(String.format(MESSAGE_UPDATE_SUCCESS, updatedCca));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        AddTransactionCommand e = (AddTransactionCommand) other;
        return cca.equals(e.cca)
            && date.equals(e.date)
            && amount.equals(e.amount)
            && remarks.equals(e.remarks);
    }
}

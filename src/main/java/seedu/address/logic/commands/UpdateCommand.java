package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEAD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANSACTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VICE_HEAD;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CCAS;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.TransactionMath;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cca.Budget;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;
import seedu.address.model.cca.Outstanding;
import seedu.address.model.cca.Spent;
import seedu.address.model.person.Name;
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
public class UpdateCommand extends Command {
    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Update details of an existing CCA\n"
        + "Parameters: "
        + PREFIX_TAG + "CCA "
        + PREFIX_NAME + "NEW CCA NAME "
        + PREFIX_HEAD + "NAME OF HEAD "
        + PREFIX_VICE_HEAD + "NAME OF VICE-HEAD "
        + PREFIX_BUDGET + "BUDGET \n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_TAG + "Basketball "
        + PREFIX_NAME + "Basketball M"
        + PREFIX_HEAD + "John "
        + PREFIX_VICE_HEAD + "Alex "
        + PREFIX_BUDGET + "700 \n"
        + "or\n"
        + COMMAND_WORD + ": Update transaction details of an existing CCA. It is required to key in an exisiting CCA "
        + "and a valid transaction entry number.\n"
        + "Parameters: "
        + PREFIX_TAG + "CCA "
        + PREFIX_TRANSACTION + "ENTRY NUMBER "
        + PREFIX_DATE + "DATE "
        + PREFIX_AMOUNT + "AMOUNT "
        + PREFIX_REMARKS + "REMARKS\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_TAG + "basketball "
        + PREFIX_TRANSACTION + "1 "
        + PREFIX_DATE + "20.12.2018 "
        + PREFIX_AMOUNT + "-300 "
        + PREFIX_REMARKS + "Purchase of Equipment\n";

    public static final String MESSAGE_UPDATE_SUCCESS = "CCA updated: %1$s";
    public static final String MESSAGE_NOT_UPDATED = "At least one field to be update must be provided.";
    public static final String MESSAGE_NON_EXISTENT_CCA = "The CCA does not exist. Please create the CCA before "
        + "adding its member";
    public static final String MESSAGE_DUPLICATE_CCA = "This CCA already exists in the budget book.";
    public static final String MESSAGE_NO_SPECIFIC_CCA = "There is no CCA specified. Please use " + PREFIX_TAG + "to "
        + "indicate the CCA.";
    private static final String MESSAGE_INVALID_HEAD_NAME = "There is no such person in the address book to add as "
        + "head";
    private static final String MESSAGE_INVALID_VICE_HEAD_NAME = "There is no such person in the address book to add "
        + "as vice-head";

    private final CcaName cca;
    private final EditCcaDescriptor editCcaDescriptor;

    /**
     * Creates an UpdateCommand to update the specified {@code Cca}.
     * @param ccaName name of the Cca to edit
     * @param editCcaDescriptor details to edit the Cca with
     */
    public UpdateCommand(CcaName ccaName, EditCcaDescriptor editCcaDescriptor) {
        requireAllNonNull(ccaName, editCcaDescriptor);

        this.cca = ccaName;
        this.editCcaDescriptor = new EditCcaDescriptor(editCcaDescriptor);
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

        Cca ccaToEdit = lastShownList.get(index);
        Cca editedCca = createEditedCca(ccaToEdit, editCcaDescriptor);

        if (!ccaToEdit.isSameCca(editedCca) && model.hasCca(editedCca)) {
            throw new CommandException(MESSAGE_DUPLICATE_CCA);
        }

        if (!editedCca.getHeadName().equals("-") && !model.hasPerson(editedCca.getHead())) {
            throw new CommandException(MESSAGE_INVALID_HEAD_NAME);
        }

        if (!editedCca.getViceHeadName().equals("-") && !model.hasPerson(editedCca.getViceHead())) {
            throw new CommandException(MESSAGE_INVALID_VICE_HEAD_NAME);
        }

        editedCca = TransactionMath.updateDetails(editedCca);

        model.updateCca(ccaToEdit, editedCca);
        model.updateFilteredCcaList(PREDICATE_SHOW_ALL_CCAS);
        model.commitBudgetBook();
        return new CommandResult(String.format(MESSAGE_UPDATE_SUCCESS, editedCca));

    }

    /**
     * Creates and returns a {@code Cca} with the details of {@code ccaToEdit}
     * edited with {@code editCcaDescriptor}.
     */
    private static Cca createEditedCca(Cca ccaToEdit, EditCcaDescriptor editCcaDescriptor) {
        assert ccaToEdit != null;

        CcaName updatedCcaName = editCcaDescriptor.getCcaName().orElse(ccaToEdit.getName());

        Name updatedHead = editCcaDescriptor.getHead().orElse(ccaToEdit.getHead());

        Name updatedViceHead = editCcaDescriptor.getViceHead().orElse(ccaToEdit.getViceHead());

        Budget updatedBudget = editCcaDescriptor.getBudget().orElse(ccaToEdit.getBudget());

        Spent updatedSpent = editCcaDescriptor.getSpent().orElse(ccaToEdit.getSpent());

        Outstanding updatedOutstanding = editCcaDescriptor.getOutstanding().orElse(ccaToEdit.getOutstanding());

        Set<Entry> updatedTransactions = new LinkedHashSet<>();
        if (editCcaDescriptor.getEntryNum().isPresent()) {
            Set<Entry> entrySet = ccaToEdit.getEntries();
            Entry[] currentCcaEntries = entrySet.toArray(new Entry[entrySet.size()]);
            int updatingEntryNum = editCcaDescriptor.getEntryNum().get() - 1;
            Entry entryToEdit = currentCcaEntries[updatingEntryNum];

            if (editCcaDescriptor.getDate().isPresent()) {
                entryToEdit.updateDate(editCcaDescriptor.getDate());
            }

            if (editCcaDescriptor.getAmount().isPresent()) {
                entryToEdit.updateAmount(editCcaDescriptor.getAmount());
            }

            if (editCcaDescriptor.getRemarks().isPresent()) {
                entryToEdit.updateRemarks(editCcaDescriptor.getRemarks());
            }

            currentCcaEntries[updatingEntryNum] = entryToEdit;

            int index = 1;
            for (Entry e: currentCcaEntries) {
                if (e.getEntryNum() == index) {
                    updatedTransactions.add(e);
                    index++;
                }
            }
        } else {
            updatedTransactions = ccaToEdit.getEntries();
        }

        return new Cca(updatedCcaName, updatedHead, updatedViceHead, updatedBudget, updatedSpent,
            updatedOutstanding, updatedTransactions);
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
        UpdateCommand e = (UpdateCommand) other;
        return cca.equals(e.cca)
            && editCcaDescriptor.equals(e.editCcaDescriptor);
    }

    /**
     * Stores the details to edit the CCA with. Each non-empty field value will replace the corresponding field value
     * of the CCA.
     */
    public static class EditCcaDescriptor {
        private CcaName name;
        private Name head;
        private Name viceHead;
        private Budget budget;
        private Spent spent;
        private Outstanding outstanding;
        private Set<Entry> transactions;
        private Integer entryNum;
        private Date date;
        private Amount amount;
        private Remarks remarks;

        public EditCcaDescriptor() {
        }

        /**
         * Copy constructor. To copy the values the attributes to be updated.
         */
        private EditCcaDescriptor(EditCcaDescriptor toCopy) {
            setCcaName(toCopy.name);
            setHead(toCopy.head);
            setViceHead(toCopy.viceHead);
            setBudget(toCopy.budget);
            setSpent(toCopy.spent);
            setOutstanding(toCopy.outstanding);
            setTransaction(toCopy.transactions);

            setEntryNum(toCopy.entryNum);
            setDate(toCopy.date);
            setAmount(toCopy.amount);
            setRemarks(toCopy.remarks);
        }


        /**
         * Returns true if at least one cca field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, head, viceHead, budget, spent, outstanding, transactions,
                entryNum, date, amount, remarks);
        }

        public void setCcaName(CcaName name) {
            this.name = name;
        }

        public Optional<CcaName> getCcaName() {
            return Optional.ofNullable(name);
        }

        public void setHead(Name head) {
            this.head = head;
        }

        public Optional<Name> getHead() {
            return Optional.ofNullable(head);
        }

        public void setViceHead(Name viceHead) {
            this.viceHead = viceHead;
        }

        public Optional<Name> getViceHead() {
            return Optional.ofNullable(viceHead);
        }

        public void setBudget(Budget budget) {
            this.budget = budget;
        }

        public Optional<Budget> getBudget() {
            return Optional.ofNullable(budget);
        }

        public void setSpent(Spent spent) {
            this.spent = spent;
        }

        public Optional<Spent> getSpent() {
            return Optional.ofNullable(spent);
        }

        public void setOutstanding(Outstanding outstanding) {
            this.outstanding = outstanding;
        }

        public Optional<Outstanding> getOutstanding() {
            return Optional.ofNullable(outstanding);
        }

        public void setEntryNum(Integer entryNum) {
            this.entryNum = entryNum;
        }

        public Optional<Integer> getEntryNum() {
            return Optional.ofNullable(entryNum);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(this.date);
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public Optional<Amount> getAmount() {
            return Optional.ofNullable(this.amount);
        }

        public void setRemarks(Remarks remarks) {
            this.remarks = remarks;
        }

        public Optional<Remarks> getRemarks() {
            return Optional.ofNullable(this.remarks);
        }


        /**
         * Sets {@code transaction} to this object's {@code transaction}.
         * A defensive copy of {@code transactions} is used internally.
         */
        public void setTransaction(Set<Entry> transaction) {
            this.transactions = (transactions != null) ? new LinkedHashSet<>(transaction) : null;
        }

        /**
         * Returns an unmodifiable transactions entry set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code transactions} is null.
         */
        public Optional<Set<Entry>> getTransactionEntries() {
            return (transactions != null) ? Optional.of(Collections.unmodifiableSet(transactions)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCcaDescriptor)) {
                return false;
            }

            // state check
            EditCcaDescriptor e = (EditCcaDescriptor) other;

            return getCcaName().equals(e.getCcaName())
                && getHead().equals(e.getHead())
                && getViceHead().equals(e.getViceHead())
                && getBudget().equals(e.getBudget())
                && getSpent().equals(e.getSpent())
                && getOutstanding().equals(e.getOutstanding())
                && getTransactionEntries().equals(e.getTransactionEntries());
        }
    }

}

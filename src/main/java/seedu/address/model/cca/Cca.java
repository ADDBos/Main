package seedu.address.model.cca;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Name;
import seedu.address.model.transaction.Entry;

//@@author ericyjw
/**
 * Represents a Cca in the cca book.
 *
 * @author ericyjw
 */
public class Cca {

    // Identity fields
    private final CcaName name;
    private final Name head;
    private final Name viceHead;

    // Data fields
    private final Budget budget;
    private final Spent spent;
    private final Outstanding outstanding;
    private final Set<Entry> transactionEntries;

    /**
     * Constructor for Cca where there is a vice-head.
     * Every identity field must be present and not null.
     * Data field can be null.
     *
     * @param name name of Cca
     * @param head name of head of Cca
     * @param viceHead name of viceHead of Cca
     * @param budget budget of Cca
     * @param spent amount spent by the Cca
     * @param outstanding outstanding amount of the Cca
     * @param transactionEntries transaction entries of the Cca
     */
    public Cca(CcaName name, Name head, Name viceHead, Budget budget, Spent spent, Outstanding outstanding,
               Set<Entry> transactionEntries) {
        requireAllNonNull(name, head, viceHead, budget, spent, outstanding);
        this.name = name;
        this.head = head;
        this.viceHead = viceHead;
        this.budget = budget;
        this.spent = spent;
        this.outstanding = outstanding;
        this.transactionEntries = transactionEntries;
    }

    /**
     * Create a Cca object with a given {@code CcaName}.
     * Used to check if the Cca is present in the {@code UniqueCcaList}
     *
     * @param ccaName the name of the Cca to be create
     */
    public Cca(String ccaName) {
        requireNonNull(ccaName);
        CcaName name = new CcaName(ccaName);
        this.name = name;
        this.head = new Name("-");
        this.viceHead = new Name("-");
        this.budget = new Budget(0);
        this.spent = new Spent(0);
        this.outstanding = new Outstanding(budget.getBudgetValue());
        this.transactionEntries = new LinkedHashSet<>();
    }

    /**
     * Create a Cca object with a given {@code CcaName} and a given {@code Budget}.
     * Used for creating new Cca with a given budget from the {@code CreateCcaCommand}.
     *
     * @param ccaName the name of the Cca to be created
     * @param budget the budget given to the Cca
     */
    public Cca(CcaName ccaName, Budget budget) {
        requireAllNonNull(ccaName, budget);
        this.name = ccaName;
        this.head = new Name("-");
        this.viceHead = new Name("-");
        this.budget = budget;
        this.spent = new Spent(0);
        this.outstanding = new Outstanding(budget.getBudgetValue());
        this.transactionEntries = new LinkedHashSet<>();
    }

    public String getCcaName() {
        return name.getNameOfCca();
    }

    public String getHeadName() {
        return head.fullName;
    }

    public String getViceHeadName() {
        return viceHead.fullName;
    }

    public Integer getBudgetAmount() {
        return budget.getBudgetValue();
    }

    public Integer getSpentAmount() {
        return spent.getSpentValue();
    }

    public int getOutstandingAmount() {
        return outstanding.getOutstandingValue();
    }

    /**
     * Returns an immutable Transaction Entry set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Entry> getEntries() {
        return Collections.unmodifiableSet(transactionEntries);
    }

    public CcaName getName() {
        return this.name;
    }

    public Name getHead() {
        return head;
    }

    public Name getViceHead() {
        return viceHead;
    }

    public Spent getSpent() {
        return spent;
    }

    public Outstanding getOutstanding() {
        return outstanding;
    }

    public Budget getBudget() {
        return budget;
    }

    /**
     * Returns the number of transaction entries in the Cca.
     */
    public int getEntrySize() {
        return this.transactionEntries.size();
    }

    /**
     * Adds a {@code newEntry} to the transaction list in the Cca.
     *
     * @param newEntry the new transaction entry to be added
     */
    public Cca addNewTransaction(Entry newEntry) {
        this.transactionEntries.add(newEntry);

        return this;
    }

    /**
     * Returns a specific transaction entry of the Cca.
     *
     * @param entryIndex the entry index to be deleted
     */
    public Entry getEntry(Integer entryIndex) throws CommandException {
        Entry[] transactionsArr = new Entry[transactionEntries.size()];
        this.transactionEntries.toArray(transactionsArr);

        if (entryIndex > transactionsArr.length || entryIndex < 1) {
            throw new CommandException(Messages.MESSAGE_INVALID_TRANSACTION_INDEX);
        }

        return transactionsArr[entryIndex - 1];
    }

    /**
     * Removes the specified transaction entry from the Cca.
     * Reorder the existing transaction entries in the Cca.
     *
     * @param entryToBeDeleted the entry to be deleted
     */
    public Cca removeTransaction(Entry entryToBeDeleted) throws CommandException {
        if (!transactionEntries.contains(entryToBeDeleted)) {
            throw new CommandException(Messages.MESSAGE_INVALID_TRANSACTION_ENTRY);
        }

        Set<Entry> deletableSet = transactionEntries.stream().collect(Collectors.toSet());
        deletableSet.remove(entryToBeDeleted);
        Entry[] transactionArr = new Entry[deletableSet.size()];
        deletableSet.toArray(transactionArr);


        int index = 1;
        for (Entry e : deletableSet) {
            e.updateEntryNum(index);
            index++;
        }

        return new Cca (name, head, viceHead, budget, spent, outstanding, deletableSet);
    }

    /**
     * Returns true if both Ccas have the same name
     * This defines a weaker notion of equality between two CCAs.
     *
     * @param toCheck name of the CCA to be checked
     */
    public boolean isSameCca(Cca toCheck) {
        return toCheck != null
            && toCheck.getCcaName().equals(getCcaName());
    }

    /**
     * Returns true if both CCAs have the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Cca)) {
            return false;
        }

        Cca otherCca = (Cca) other;
        return otherCca.name.equals(this.name)
            && otherCca.head.equals(this.head)
            && otherCca.viceHead.equals(this.viceHead)
            && otherCca.budget.equals(this.budget);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, head, viceHead, budget);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getCcaName())
            .append(" Head: ")
            .append(getHeadName())
            .append(" Vice-Head: ")
            .append(getViceHeadName())
            .append(" Budget: ")
            .append(getBudgetAmount())
            .append(" Outstanding: ")
            .append(getOutstandingAmount());
        return builder.toString();
    }
}

package seedu.address.model.cca;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.budget.Transaction;
import seedu.address.model.person.Name;

/**
 * Represents a CCA in the cca book.
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
    private final Transaction transaction;

    /**
     * Constructor for CCA
     * Every identity field must be present and not null.
     * Data field can be null.
     *
     * @param name name of CCA
     * @param head name of head of CCA
     * @param viceHead name of viceHead of CCA
     * @param budget budget of CCA
     * @param spent amount spent by the CCA
     * @param outstanding outstanding amount of the CCA
     * @param transaction transaction history of the CCA
     */
    public Cca(CcaName name, Name head, Name viceHead, Budget budget, Spent spent, Outstanding outstanding,
               Transaction transaction) {
        requireAllNonNull(name, head, viceHead, budget, spent, outstanding);
        this.name = name;
        this.head = head;
        this.viceHead = viceHead;
        this.budget = budget;
        this.spent = spent;
        this.outstanding = outstanding;
        this.transaction = transaction;
    }

    /**
     * Alternative constructor, in the event that there is no vice-head in the CCA.
     *
     * @param name name of the cca
     * @param head name of the head of the CCA
     * @param budget budget of the CCA
     * @param spent amount spent by the CCA
     * @param outstanding outstanding amount of the CCA
     * @param transaction transaction history of the CCA
     */
    public Cca(CcaName name, Name head, Budget budget, Spent spent, Outstanding outstanding,
               Transaction transaction) {
        requireAllNonNull(name, head, budget, spent, outstanding);
        this.name = name;
        this.head = head;
        this.viceHead = null;
        this.budget = budget;
        this.spent = spent;
        this.outstanding = outstanding;
        this.transaction = transaction;
    }

    public Cca(String ccaName) {
        CcaName name = new CcaName(ccaName);
        this.name = name;
        this.head = null;
        this.viceHead = null;
        this.budget = null;
        this.spent = null;
        this.outstanding = null;
        this.transaction = null;
    }

    public Cca(CcaName ccaName, Budget budget) {
        this.name = ccaName;
        this.head = new Name("-");
        this.viceHead = new Name("-");
        this.budget = budget;
        this.spent = new Spent(0);
        this.outstanding = new Outstanding(budget.getBudget());
        this.transaction = new Transaction("-");
    }

    public String getCcaName() {
        return name.getCcaName();
    }

    public String getHeadName() {
        return head.fullName;
    }

    public String getViceHeadName() {
        if (!viceHead.equals(null)) {
            return viceHead.fullName;
        }
        return "-";
    }

    public int getGivenBudgetAmount() {
        return budget.getBudget();
    }

    public int getSpentAmount() {
        return spent.getSpent();
    }

    public int getOutstandingAmount() {
        return outstanding.getOutstanding();
    }

    public String getTransactionLog() {
        return transaction.getTransactionLog();
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

    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * Returns true if both CCA of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two CCAs.
     */
    public boolean isSameCca(Cca otherCca) {
        if (otherCca == this) {
            return true;
        }

        return otherCca != null
            && otherCca.getCcaName().equals(getCcaName());
        //&& (otherCca.getHeadName().equals(getHeadName()) || otherCca.getViceHeadName().equals(getViceHeadName()));
    }

    /**
     * Return true if the CCA name is the same
     *
     * @param toCheck name of the CCA to be checked
     */
    public boolean isSameCcaName(Cca toCheck) {
        return getCcaName().equals(toCheck.getCcaName());
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
        return otherCca.getCcaName().equals(getCcaName())
            && otherCca.getHeadName().equals(getHeadName())
            && otherCca.getViceHeadName().equals(getViceHeadName())
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
            .append(getGivenBudgetAmount())
            .append(" Outstanding: ")
            .append(getOutstandingAmount());
        return builder.toString();
    }

}


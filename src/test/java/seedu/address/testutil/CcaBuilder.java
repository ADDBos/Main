package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import seedu.address.model.budget.Transaction;
import seedu.address.model.cca.Budget;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;
import seedu.address.model.cca.Outstanding;
import seedu.address.model.cca.Spent;
import seedu.address.model.person.Name;

/**
 * A utility class to help with building Cca objects.
 */
//@@author javenseow
//@author ericyjw
public class CcaBuilder {
    public static final String DEFAULT_CCANAME = "jcrc";
    public static final Name DEFAULT_HEAD = CARL.getName();
    public static final Name DEFAULT_VICEHEAD = DANIEL.getName();
    public static final int DEFAULT_BUDGET = 500;
    public static final int DEFAULT_SPENT = 100;
    public static final int DEFAULT_OUTSTANDING = 400;
    public static final String DEFAULT_TRANSACTION = "Bought equipments/-$150\nTeam Fund/$50\n";

    private CcaName ccaName;
    private Name head;
    private Name viceHead;
    private Budget budget;
    private Spent spent;
    private Outstanding outstanding;
    private Transaction transaction;

    public CcaBuilder() {
        this.ccaName = new CcaName(DEFAULT_CCANAME);
        this.head = DEFAULT_HEAD;
        this.viceHead = DEFAULT_HEAD;
        this.budget = new Budget(DEFAULT_BUDGET);
        this.spent = new Spent(DEFAULT_SPENT);
        this.outstanding = new Outstanding(DEFAULT_OUTSTANDING);
        this.transaction = new Transaction(DEFAULT_TRANSACTION);
    }

    /**
     * Sets the cca name of the {@code Cca} we are building.
     */
    public CcaBuilder withCcaName(String ccaName) {
        this.ccaName = new CcaName(ccaName);
        return this;
    }

    /**
     * Sets the head of the {@code Cca} we are building.
     */
    public CcaBuilder withHead(Name head) {
        this.head = head;
        return this;
    }

    /**
     * Sets the vice head of the {@code Cca} we are building.
     */
    public CcaBuilder withViceHead(Name viceHead) {
        this.viceHead = viceHead;
        return this;
    }

    /**
     * Sets the {@code Budget} of the {@code Cca} we are building.
     */
    public CcaBuilder withBudget(int budget) {
        this.budget = new Budget(budget);
        return this;
    }

    /**
     * Sets the {@code Spent} of the {@code Cca} we are building.
     */
    public CcaBuilder withSpent(int spent) {
        this.spent = new Spent(spent);
        return this;
    }

    /**
     * Sets the {@code Outstanding} of the {@code Cca} we are building.
     */
    public CcaBuilder withOutstanding(int outstanding) {
        this.outstanding = new Outstanding(outstanding);
        return this;
    }

    /**
     * Sets the {@code Outstanding} of the {@code Cca} we are building.
     */
    public CcaBuilder withTransaction(String transaction) {
        this.transaction = new Transaction(transaction);
        return this;
    }

    public Cca build() {
        return new Cca(ccaName, head, viceHead, budget, spent, outstanding, transaction);
    }

}

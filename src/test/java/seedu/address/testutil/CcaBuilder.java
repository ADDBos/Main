package seedu.address.testutil;

import static seedu.address.testutil.TypicalEntries.TRANSACTION_EMPTY;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import java.util.Set;

import seedu.address.model.cca.Budget;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;
import seedu.address.model.cca.Outstanding;
import seedu.address.model.cca.Spent;
import seedu.address.model.person.Name;
import seedu.address.model.transaction.Entry;

//@@author javenseow ericyjw
/**
 * A utility class to help with building Cca objects.
 */
public class CcaBuilder {
    public static final String DEFAULT_CCANAME = "JCRC";
    public static final Name DEFAULT_HEAD = CARL.getName();
    public static final Name DEFAULT_VICEHEAD = DANIEL.getName();
    public static final int DEFAULT_BUDGET = 500;
    public static final int DEFAULT_SPENT = 300;
    public static final int DEFAULT_OUTSTANDING = 200;
    public static final Set<Entry> DEFAULT_TRANSACTION = TRANSACTION_EMPTY;

    private CcaName ccaName;
    private Name head;
    private Name viceHead;
    private Budget budget;
    private Spent spent;
    private Outstanding outstanding;
    private Set<Entry> transactionEntries;

    public CcaBuilder() {
        this.ccaName = new CcaName(DEFAULT_CCANAME);
        this.head = DEFAULT_HEAD;
        this.viceHead = DEFAULT_VICEHEAD;
        this.budget = new Budget(DEFAULT_BUDGET);
        this.spent = new Spent(DEFAULT_SPENT);
        this.outstanding = new Outstanding(DEFAULT_OUTSTANDING);
        this.transactionEntries = DEFAULT_TRANSACTION;
    }

    /**
     * Initializes the CcaBuilder with the data of {@code ccaToCopy}.
     */
    public CcaBuilder(Cca ccaToCopy) {
        this.ccaName = ccaToCopy.getName();
        this.head = ccaToCopy.getHead();
        this.viceHead = ccaToCopy.getViceHead();
        this.budget = ccaToCopy.getBudget();
        this.spent = ccaToCopy.getSpent();
        this.outstanding = ccaToCopy.getOutstanding();
        this.transactionEntries = ccaToCopy.getEntries();
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
    public CcaBuilder withHead(String head) {
        this.head = new Name(head);
        return this;
    }

    /**
     * Sets the vice head of the {@code Cca} we are building.
     */
    public CcaBuilder withViceHead(String viceHead) {
        this.viceHead = new Name(viceHead);
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
     * Sets the {@code entries} of the {@code Cca} we are building.
     */
    public CcaBuilder withTransaction(Set<Entry> entries) {
        this.transactionEntries = entries;
        return this;
    }

    public Cca build() {
        return new Cca(ccaName, head, viceHead, budget, spent, outstanding, transactionEntries);
    }

}

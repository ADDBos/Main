package seedu.address.model.budget;

/**
 * Represents a Budget entry.
 */
public class Budget {

    private static int initialSpending = 0;

    private int initial;
    private int spent;
    private int outstanding;
    private Transaction transaction;

    public Budget(int initial) {
        this.initial = initial;
        this.spent = initialSpending;
        this.outstanding = initial;
        this.transaction = new Transaction();
    }

    public Budget(int initial, int spent, int outstanding, Transaction transaction) {
        this.initial = initial;
        this.spent = spent;
        this.outstanding = outstanding;
        this.transaction = transaction;
    }

    public int getGivenBudget() {
        return initial;
    }

    public int getSpent() {
        return spent;
    }

    public int getOutstanding() {
        return outstanding;
    }

    public String getTransactionLog() {
        return transaction.getTransactionLog();
    }

    /**
     * Returns true if both budget entry have the same data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Budget)) {
            return false;
        }

        Budget otherBudget = (Budget) other;
        return otherBudget.getGivenBudget() == getGivenBudget()
            && otherBudget.getOutstanding() == getOutstanding()
            && otherBudget.getTransactionLog().equals(getTransactionLog());
    }

}

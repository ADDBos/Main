package seedu.address.model.budget;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represent the transaction log.
 */
public class Transaction {
    public static final String MESSAGE_TRANSACTION_CONSTRAINTS =
        "Transactions should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TRANSACTION_VALIDATION_REGEX = "[\\p{Alnum}-][\\p{Alnum} ]*";

    private String log;

    public Transaction() {
        this.log = null;
    }

    public Transaction(String log) {
        requireNonNull(log);
        checkArgument(isValidTranscation(String.valueOf(log)), MESSAGE_TRANSACTION_CONSTRAINTS);
        this.log = log;
    }

    public static boolean isValidTranscation(String test) {
        return test.matches(TRANSACTION_VALIDATION_REGEX);
    }

    public String getTransactionLog() {
        return log;
    }

    /**
     * Update the transaction log by appending new entry
     * @param entry new transaction entry
     */
    public void updateTransaction(String entry) {
        StringBuilder sb = new StringBuilder(this.log);
        sb.append(entry);
    }

    /**
     * Returns true if both ccas have the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Transaction)) {
            return false;
        }

        Transaction otherBudget = (Transaction) other;
        return otherBudget.getTransactionLog().equals(getTransactionLog());
    }
}

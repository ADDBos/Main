package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author ericyjw
/**
 * Represents a transaction entry amount in the cca book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAmount(String)}
 *
 * @author ericyjw
 */
public class Amount {
    public static final String MESSAGE_AMOUNT_CONSTRAINTS =
        "Transaction amount should only contain digits and dashes, and it should not be blank";
    /*
     * The first character of the Date must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String AMOUNT_VALIDATION_REGEX = "[-][0-9]{1,9}||[0-9]{1,9}";

    private final Integer amount;

    /**
     * Create an {@code Amount}.
     *
     * @param amount a valid amount
     */
    public Amount(Integer amount) {
        requireNonNull(amount);
        checkArgument(isValidAmount(String.valueOf(amount)), MESSAGE_AMOUNT_CONSTRAINTS);
        this.amount = amount;
    }

    public Integer getAmount() {
        return this.amount;
    }

    /**
     * To test is the given string contains a valid transaction {@code Amount}.
     *
     * @param test the string to check
     */
    public static boolean isValidAmount(String test) {
        if ("".equals(test)) {
            return false;
        }
        return test.matches(AMOUNT_VALIDATION_REGEX);
    }

    /**
     * Returns true if both amounts are the same.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Amount)) {
            return false;
        }

        Amount otherAmounts = (Amount) other;
        return otherAmounts.amount.equals(this.amount);
    }

    @Override
    public String toString() {
        return String.valueOf(amount);
    }
}

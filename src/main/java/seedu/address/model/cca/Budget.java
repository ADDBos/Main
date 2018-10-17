package seedu.address.model.cca;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * @author ericyjw
 */
public class Budget {
    public static final String MESSAGE_BUDGET_CONSTRAINTS =
        "Budget should only contain numbers and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String BUDGET_VALIDATION_REGEX = "\\d+";

    public final Integer budget;

    /**
     * Constructs a {@code Budget} amount.
     *
     * @param amount A valid budget amount.
     */
    public Budget(Integer amount) {
        requireNonNull(amount);
        checkArgument(isValidBudget(String.valueOf(amount)), MESSAGE_BUDGET_CONSTRAINTS);
        this.budget = amount;
    }

    /**
     * Returns true if a given string is a valid budget amount.
     */
    public static boolean isValidBudget(String test) {
        return test.matches(BUDGET_VALIDATION_REGEX);
    }
    // TODO: Cross check with the address book CCA

    public int getBudget() {
        return this.budget;
    }

    @Override
    public String toString() {
        return String.valueOf(budget);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Budget // instanceof handles nulls
            && budget.equals(((Budget) other).budget)); // state check
    }

    @Override
    public int hashCode() {
        return budget.hashCode();
    }
}

package seedu.address.model.cca;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author ericyjw
/**
 * Represents a CCA's Spent amount in the cca book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSpent(String)}
 *
 * @author ericyjw
 */
public class Spent {
    public static final String MESSAGE_SPENT_CONSTRAINTS =
        "Spent should only contain numbers and should not be blank!";
    /*
     * The first character of Spent must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String SPENT_VALIDATION_REGEX = "\\d+";

    private final Integer spent;

    /**
     * Constructs a {@code Spent}.
     *
     * @param spent a valid spent amount.
     */
    public Spent(Integer spent) {
        requireNonNull(spent);
        checkArgument(isValidSpent(String.valueOf(spent)), MESSAGE_SPENT_CONSTRAINTS);
        this.spent = spent;
    }

    /**
     * Returns true if a given string is a valid CCA name.
     */
    public static boolean isValidSpent(String test) {
        return test.matches(SPENT_VALIDATION_REGEX);
    }

    public Integer getSpentValue() {
        return this.spent;
    }

    @Override
    public String toString() {
        return String.valueOf(spent);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Spent // instanceof handles nulls
            && spent.equals(((Spent) other).spent)); // state check
    }

    @Override
    public int hashCode() {
        return spent.hashCode();
    }
}

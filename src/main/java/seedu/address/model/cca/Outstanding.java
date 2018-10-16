package seedu.address.model.cca;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * @author ericyjw
 */
public class Outstanding {
    public static final String MESSAGE_OUTSTANDING_CONSTRAINTS =
        "Outstanding should only contain numbers and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String OUTSTANDING_VALIDATION_REGEX = "\\d+";

    public final Integer outstanding;

    /**
     * Constructs an {@code outstanding} amount.
     *
     * @param amount valid outstanding amount.
     */
    public Outstanding(Integer amount) {
        requireNonNull(amount);
        checkArgument(isValidOutstanding(String.valueOf(amount)), MESSAGE_OUTSTANDING_CONSTRAINTS);
        this.outstanding = amount;
    }

    /**
     * Returns true if a given string is a valid outstanding amount.
     */
    public static boolean isValidOutstanding(String test) {
        return test.matches(OUTSTANDING_VALIDATION_REGEX);
    }
    // TODO: To do the arithmetic

    public int getOutstanding() {
        return this.outstanding;
    }

    @Override
    public String toString() {
        return String.valueOf(this.outstanding);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Outstanding // instanceof handles nulls
            && outstanding.equals(((Outstanding) other).outstanding)); // state check
    }

    @Override
    public int hashCode() {
        return outstanding.hashCode();
    }
}

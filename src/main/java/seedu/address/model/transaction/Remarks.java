package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author ericyjw
/**
 * Represents a transaction entry remarks in the cca book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRemark(String)}
 *
 * @author ericyjw
 */
public class Remarks {
    public static final String MESSAGE_REMARKS_CONSTRAINTS =
        "Transaction Remarks should only contain alphanumeric characters and spaces, and it should not be blank";
    /*
     * The first character of the Date must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String REMARKS_VALIDATION_REGEX = "[\\p{Alnum}-][\\p{Alnum}- ]*";

    private String remarks;

    /**
     * Creates a {@code Remarks}.
     * @param remarks a valid string of remarks
     */
    public Remarks(String remarks) {
        requireNonNull(remarks);
        checkArgument(isValidRemark(remarks), MESSAGE_REMARKS_CONSTRAINTS);
        this.remarks = remarks;
    }

    public static boolean isValidRemark(String test) {
        return test.matches(REMARKS_VALIDATION_REGEX);
    }

    public String getRemarks() {
        return this.remarks;
    }

    /**
     * Returns true if both remarks are the same.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Remarks)) {
            return false;
        }

        Remarks otherRemarks = (Remarks) other;
        return otherRemarks.remarks.equals(remarks);
    }

    @Override
    public String toString() {
        return remarks;
    }
}

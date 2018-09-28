//@@author javenseow
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's school in the address book.
 */
//@@author javenseow
public class School {
    public static final String MESSAGE_SCHOOL_CONSTRAINTS =
            "School should only contain alphanumeric characters and spaces, and it should not be blank.";
    public static final String SCHOOL_VALIDATION_REGEX = "\\p{Alnum}+";

    public final String schoolName;

    /**
     * Constructs a {@code School}.
     *
     * @param school a valid school
     */
    public School(String school) {
        requireNonNull(school);
        checkArgument(isValidSchool(school), MESSAGE_SCHOOL_CONSTRAINTS);
        schoolName = school;
    }

    /**
     * Returns true if a given string is a valid school.
     */
    public static boolean isValidSchool(String test) {
        return test.matches(SCHOOL_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return schoolName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof School // instanceof handles nulls
                && schoolName.equals(((School) other).schoolName));
    }

    @Override
    public int hashCode() {
        return schoolName.hashCode();
    }
}

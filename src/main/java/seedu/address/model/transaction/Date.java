package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author ericyjw
/**
 * Represents a transaction entry date in the cca book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 *
 * @author ericyjw
 */
public class Date {
    public static final String MESSAGE_DATE_CONSTRAINTS =
        "Transaction date should only contain digits and dots, and it should not be blank.\n"
            + "It should be a valid date on the calendar.";
    /*
     * The first character of the Date must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DATE_VALIDATION_REGEX = "[0-9]{1,2}[.]\\d{1,2}[.]\\d{4}";

    private final String date;

    /**
     * Creates a {@code Date}.
     *
     * @param date a valid string of date
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_DATE_CONSTRAINTS);
        this.date = date;
    }

    /**
     * To test if the given string contains a valid string of {@code Date}.
     *
     * @param test the string to check
     */
    public static boolean isValidDate(String test) {
        requireNonNull(test);
        String[] token = test.split("\\.");
        return isValidDate(token) && test.matches(DATE_VALIDATION_REGEX);
    }

    /**
     * To check for valid date.
     *
     * @param date the array of date containing the day, month and year
     */
    private static boolean isValidDate(String[] date) {
        try {
            int day = Integer.valueOf(date[0].trim());
            int month = Integer.valueOf(date[1].trim());
            int year = Integer.valueOf(date[2].trim());

            int leapYear = 2016;
            boolean isLeapYear = (Math.abs(leapYear - year) % 4) == 0;

            switch (month) {
            case 4:
            case 6:
            case 9:
            case 11:
                return day < 31 && day > 0;

            case 2:
                if (isLeapYear) {
                    return day < 30 && day > 0;
                } else {
                    return day < 29 && day > 0;
                }

            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return day <= 31 && day > 0;

            default:
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public String getDate() {
        return this.date;
    }

    /**
     * Returns true if both dates are the same.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Date)) {
            return false;
        }

        Date otherDate = (Date) other;
        return otherDate.date.equals(date);
    }

    @Override
    public String toString() {
        return date;
    }
}

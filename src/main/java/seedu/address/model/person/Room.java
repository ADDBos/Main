//@@author javenseow
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's room number in the address book.
 */
//@@author javenseow
public class Room {
    public static final String MESSAGE_ROOM_CONSTRAINTS =
            "Room numbers should only be alphanumeric, and the first character should be an alphabet,"
            + "followed by 3 digits.";
    public static final String ROOM_VALIDATION_REGEX = "\\D\\d{3}";
    public final String value;

    /**
     * Constructs a {@code Room}.
     *
     * @param room a valid room number.
     */
    public Room(String room) {
        requireNonNull(room);
        checkArgument(isValidRoom(room), MESSAGE_ROOM_CONSTRAINTS);
        value = room;
    }

    /**
     * Returns true if a given string is a valid room number.
     */
    public static boolean isValidRoom(String test) {
        return test.matches(ROOM_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Room // instanceof handles nulls
                && value.equals(((Room) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

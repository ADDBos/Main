package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author javenseow
public class RoomTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Room(null));
    }

    @Test
    public void constructor_invalidRoom_throwsIllegalArgumentException() {
        String invalidRoom = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Room(invalidRoom));
    }

    @Test
    public void isValidRoom() {
        // null room
        Assert.assertThrows(NullPointerException.class, () -> Room.isValidRoom(null));

        // invalid room
        assertFalse(Room.isValidRoom("")); // empty string
        assertFalse(Room.isValidRoom(" ")); // spaces only
        assertFalse(Room.isValidRoom("^")); // only non-alphanumeric characters
        assertFalse(Room.isValidRoom("A123@")); // contains non-alphanumeric characters
        assertFalse(Room.isValidRoom("a1425")); // contains more than 4 characters
        assertFalse(Room.isValidRoom("1234")); // contains only numbers
        assertFalse(Room.isValidRoom("abcd")); // contains only alphabets

        // valid room
        assertTrue(Room.isValidRoom("a123")); // alphanumeric characters
        assertTrue(Room.isValidRoom("A123")); // with capital letters
    }
}

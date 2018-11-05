package seedu.address.model.cca;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author ericyjw

/**
 * To test for valid {@code Spent}.
 * Checks for null, negative spent and other combinations of spent values.
 */
public class SpentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Spent(null));
    }

    @Test
    public void constructor_invalidSpent_throwsIllegalArgumentException() {
        Integer invalidSpent = -1;
        Assert.assertThrows(IllegalArgumentException.class, () -> new Spent(invalidSpent));
    }

    @Test
    public void isValidSpent() {
        // null spent
        Assert.assertThrows(NullPointerException.class, () -> Spent.isValidSpent(null));

        // invalid spent amount
        assertFalse(Spent.isValidSpent("")); // empty string
        assertFalse(Spent.isValidSpent(" ")); // spaces only
        assertFalse(Spent.isValidSpent("spent")); // non-numeric
        assertFalse(Spent.isValidSpent("3p11")); // alphabets within digits
        assertFalse(Spent.isValidSpent("3 000")); // spaces within digits
        assertFalse(Spent.isValidSpent("3,000")); // non-digit character within digits
        assertFalse(Spent.isValidSpent("-100")); // dash in front of digits


        // valid spent amount
        assertTrue(Spent.isValidSpent("300")); // regular amount for spent
        assertTrue(Spent.isValidSpent("0")); // zero for spent
        assertTrue(Spent.isValidSpent("1")); // extremely small spent amount
        assertTrue(Spent.isValidSpent("1234567890")); // extremely large spent amount
    }

    @Test
    public void getSpentValue() {
        Spent spent = new Spent(300);
        assertTrue(spent.getSpentValue().equals(300));

        assertFalse(spent.getSpentValue().equals(100)); // different int value
        assertFalse(spent.getSpentValue().equals("300")); // string int value
    }

}

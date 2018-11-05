package seedu.address.model.cca;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author ericyjw

/**
 * To test for valid {@code Outstanding}.
 * Checks for null, negative outstanding and other combinations of outstanding values.
 */
public class OutstandingTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Outstanding(null));
    }

    @Test
    public void constructor_invalidOutstanding_throwsNumberFormatException() {
        Integer invalidOutstanding = -1;
        Assert.assertThrows(NumberFormatException.class, () -> new Outstanding(invalidOutstanding));
    }

    @Test
    public void isValidOutstanding() {
        // null outstanding
        Assert.assertThrows(NullPointerException.class, () -> Outstanding.isValidOutstanding(null));

        // invalid outstanding amount
        assertFalse(Outstanding.isValidOutstanding("")); // empty string
        assertFalse(Outstanding.isValidOutstanding(" ")); // spaces only
        assertFalse(Outstanding.isValidOutstanding("spent")); // non-numeric
        assertFalse(Outstanding.isValidOutstanding("3p11")); // alphabets within digits
        assertFalse(Outstanding.isValidOutstanding("3 000")); // spaces within digits
        assertFalse(Outstanding.isValidOutstanding("3,000")); // non-digit character within digits
        assertFalse(Outstanding.isValidOutstanding("-100")); // dash in front of digits


        // valid outstanding amount
        assertTrue(Outstanding.isValidOutstanding("300")); // regular amount for outstanding
        assertTrue(Outstanding.isValidOutstanding("0")); // zero for outstanding
        assertTrue(Outstanding.isValidOutstanding("1")); // extremely small outstanding amount
        assertTrue(Outstanding.isValidOutstanding("1234567890")); // extremely large outstanding amount
    }

    @Test
    public void getOutstandingValue() {
        Outstanding outstanding = new Outstanding(100);
        assertTrue(outstanding.getOutstandingValue().equals(100));

        assertFalse(outstanding.getOutstandingValue().equals(200)); // different int value
        assertFalse(outstanding.getOutstandingValue().equals("100")); // string value of outstanding amount
    }
}

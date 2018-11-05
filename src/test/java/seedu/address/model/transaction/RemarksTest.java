package seedu.address.model.transaction;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author ericyjw

/**
 * To test for valid {@code Remarks}.
 * Checks for null, empty remarks and other combinations of remarks.
 */
public class RemarksTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Remarks(null));
    }

    @Test
    public void constructor_invalidRemarks_throwsIllegalArgumentException() {
        String invalidRemarks = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Remarks(invalidRemarks));
    }

    @Test
    public void isValidRemarks() {
        // null remarks
        Assert.assertThrows(NullPointerException.class, () -> Remarks.isValidRemark(null));

        // invalid remarks
        assertFalse(Remarks.isValidRemark("")); // empty string
        assertFalse(Remarks.isValidRemark(" ")); // spaces only
        assertFalse(Remarks.isValidRemark("Purchase of balls: Molten")); // contains wrong symbol between characters

        // valid remarks
        assertTrue(Remarks.isValidRemark("Purchase of Molten Basketball")); // valid remarks
        assertTrue(Remarks.isValidRemark("-")); // symbol only
        assertTrue(Remarks.isValidRemark("Welfare")); // Single word
        assertTrue(Remarks.isValidRemark("100")); // digits only
        assertTrue(Remarks.isValidRemark("Purchase of Molten Basketball ")); // ending with whitespace
        assertTrue(Remarks.isValidRemark("Purchase of 10 Molten Basketball")); // digits in between characters
        assertTrue(Remarks.isValidRemark("Purchase of Molten Basketball - 10")); // dash in between words
    }


}

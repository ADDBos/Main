package seedu.address.model.cca;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author ericyjw

/**
 * To test for valid {@code CcaName}.
 * Checks for null, empty cca name and other combinations of cca names.
 */
public class CcaNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new CcaName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new CcaName(invalidName));
    }

    @Test
    public void isValidCcaName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> CcaName.isValidCcaName(null));

        // invalid name
        assertFalse(CcaName.isValidCcaName("")); // empty string
        assertFalse(CcaName.isValidCcaName(" ")); // spaces only
        assertFalse(CcaName.isValidCcaName("1")); // numbers only
        assertFalse(CcaName.isValidCcaName("-")); // only non-alphabet characters
        assertFalse(CcaName.isValidCcaName("basketball(f)")); // contains non-alphabet characters
        assertFalse(CcaName.isValidCcaName("Basketball 1")); // contain non-alphabet characters

        // valid name
        assertTrue(CcaName.isValidCcaName("basketball")); // alphabets only
        assertTrue(CcaName.isValidCcaName("basketball m")); // with space
        assertTrue(CcaName.isValidCcaName("Basketball M")); // with capital letters
    }
}

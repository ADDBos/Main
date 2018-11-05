package seedu.address.model.transaction;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author ericyjw
/**
 * To test for valid {@code Amount}.
 * Checks for null and other combinations of amount.
 */
public class AmountTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Amount(null));
    }

    @Test
    public void isValidAmount() {
        // null amount
        Assert.assertThrows(NullPointerException.class, () -> Amount.isValidAmount(null));

        // invalid date
        assertFalse(Amount.isValidAmount("")); // empty string
        assertFalse(Amount.isValidAmount(" ")); // spaces only
        assertFalse(Amount.isValidAmount("$")); // symbols only
        assertFalse(Amount.isValidAmount("c")); // characters only
        assertFalse(Amount.isValidAmount("$100")); // contains $ in front of digit
        assertFalse(Amount.isValidAmount("100.5")); // contains decimal
        assertFalse(Amount.isValidAmount("-$100")); // contains negative in front of $
        assertFalse(Amount.isValidAmount("100-")); // contains negative at the back of digits

        // valid date
        assertTrue(Amount.isValidAmount("100")); // valid date
        assertTrue(Amount.isValidAmount("-100")); // shorten date
    }

    @Test
    public void getAmount() {
        Amount amount = new Amount(-200);
        assertTrue(amount.getAmount().equals(-200));
        assertFalse(amount.getAmount().equals("-200")); // string value of the amount
        assertFalse(amount.getAmount().equals(200)); // different amount value
    }
}

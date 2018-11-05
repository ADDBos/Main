package seedu.address.model.cca;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author ericyjw

/**
 * To test for valid {@code Budget}.
 * Checks for null, negative budget and other combinations of budget values.
 */
public class BudgetTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Budget(null));
    }

    @Test
    public void constructor_invalidBudget_throwsIllegalArgumentException() {
        Integer invalidBudget = -1;
        Assert.assertThrows(IllegalArgumentException.class, () -> new Budget(invalidBudget));
    }

    @Test
    public void isValidBudget() {
        // null budget
        Assert.assertThrows(NullPointerException.class, () -> Budget.isValidBudget(null));

        // invalid budget amount
        assertFalse(Budget.isValidBudget("")); // empty string
        assertFalse(Budget.isValidBudget(" ")); // spaces only
        assertFalse(Budget.isValidBudget("budget")); // non-numeric
        assertFalse(Budget.isValidBudget("3p11")); // alphabets within digits
        assertFalse(Budget.isValidBudget("3 000")); // spaces within digits
        assertFalse(Budget.isValidBudget("3,000")); // non-digit character within digits
        assertFalse(Budget.isValidBudget("-500")); // dash in front of digits


        // valid budget amount
        assertTrue(Budget.isValidBudget("300")); // regular amount for budget
        assertTrue(Budget.isValidBudget("0")); // zero for budget
        assertTrue(Budget.isValidBudget("1")); // extremely small budget
        assertTrue(Budget.isValidBudget("1234567890")); // extremely large budget
    }
}

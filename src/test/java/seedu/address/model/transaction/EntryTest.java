package seedu.address.model.transaction;

import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENTRY_NUM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS;

import org.junit.Test;

import seedu.address.testutil.Assert;

/**
 * To test for valid {@code Entry}.
 * Checks for null, empty budget and other combinations of budget values.
 */
public class EntryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Entry(null, VALID_DATE, VALID_AMOUNT, VALID_REMARKS));
        Assert.assertThrows(NullPointerException.class, () -> new Entry(VALID_ENTRY_NUM, null, VALID_AMOUNT,
            VALID_REMARKS));
        Assert.assertThrows(NullPointerException.class, () -> new Entry(VALID_ENTRY_NUM, VALID_DATE, null,
            VALID_REMARKS));
        Assert.assertThrows(NullPointerException.class, () -> new Entry(VALID_ENTRY_NUM, VALID_DATE, VALID_AMOUNT,
            null));

        Assert.assertThrows(NullPointerException.class, () -> new Entry(null, new Date(VALID_DATE),
            new Amount(Integer.valueOf(VALID_AMOUNT)), new Remarks(VALID_REMARKS)));
        Assert.assertThrows(NullPointerException.class, () -> new Entry(1, null,
            new Amount(Integer.valueOf(VALID_AMOUNT)), new Remarks(VALID_REMARKS)));
        Assert.assertThrows(NullPointerException.class, () -> new Entry(1, new Date(VALID_DATE), null, new Remarks(
            VALID_REMARKS)));
        Assert.assertThrows(NullPointerException.class, () -> new Entry(1, new Date(VALID_DATE),
            new Amount(Integer.valueOf(VALID_AMOUNT)),
            null));
    }

    @Test
    public void constructor_invalidEntryNum_throwsIllegalArgumentException() {
        String invalidEntryNum = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Entry(invalidEntryNum, VALID_DATE, VALID_AMOUNT,
            VALID_REMARKS));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Entry(VALID_ENTRY_NUM, invalidDate, VALID_AMOUNT,
            VALID_REMARKS));
    }

    @Test
    public void constructor_invalidAmount_throwsIllegalArgumentException() {
        String invalidAmount = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Entry(VALID_ENTRY_NUM, VALID_DATE, invalidAmount,
            VALID_REMARKS));
    }

    @Test
    public void constructor_invalidRemarksthrowsIllegalArgumentException() {
        String invalidRemarks = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Entry(VALID_ENTRY_NUM, VALID_DATE, VALID_AMOUNT,
            invalidRemarks));
    }

    @Test
    public void isValidEntry() {
        // null entry
        Assert.assertThrows(NullPointerException.class, () -> Entry.isValidEntry(null, null, null, null));
    }

}

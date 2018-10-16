package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author javenseow
public class SchoolTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new School(null));
    }

    @Test
    public void constructor_invalidSchool_throwsIllegalArgumentException() {
        String invalidSchool = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new School(invalidSchool));
    }

    @Test
    public void isValidSchool() {
        // null school
        Assert.assertThrows(NullPointerException.class, () -> School.isValidSchool(null));

        // invalid school
        assertFalse(School.isValidSchool("")); // empty string
        assertFalse(School.isValidSchool(" ")); // spaces only
        assertFalse(School.isValidSchool("#")); // only non-alphanumeric characters
        assertFalse(School.isValidSchool("soc!")); // contains non-alphanumeric characters
        assertFalse(School.isValidSchool("business school")); // contains space

        // valid school
        assertTrue(School.isValidSchool("soc")); // alphabets only
        assertTrue(School.isValidSchool("Biz2")); // alphanumeric
        assertTrue(School.isValidSchool("210")); // numbers only
        assertTrue(School.isValidSchool("SoC")); // with capital letters
    }
}

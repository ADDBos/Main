package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ProfilePictureTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ProfilePicture(null));
    }

    @Test
    public void constructor_invalidProfilePicture_throwsIllegalArgumentException() {
        String invalidProfilePicture = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ProfilePicture(invalidProfilePicture));
    }

    @Test
    public void isValidProfilePicture() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> ProfilePicture.isValidProfilePicture(null));

        // invalid profile picture
        assertFalse(ProfilePicture.isValidProfilePicture("")); // empty string
        assertFalse(ProfilePicture.isValidProfilePicture(" ")); // spaces only
        assertFalse(ProfilePicture.isValidProfilePicture("^")); // only non-alphanumeric characters
        assertFalse(ProfilePicture
                .isValidProfilePicture("C:\\Users\\javen\\OneDrive\\Pictures\\Saved Pictures")); // does not end
        // jpg or png

        //valid profile picture
        assertTrue(ProfilePicture.isValidProfilePicture("a.jpg")); // alphabets only
        assertTrue(ProfilePicture.isValidProfilePicture("A.jpg")); // with capital letters
        assertTrue(ProfilePicture.isValidProfilePicture("IMG_0109.jpg")); // alphanumeric characters
    }
}

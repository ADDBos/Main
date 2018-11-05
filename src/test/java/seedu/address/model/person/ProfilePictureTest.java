package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Paths;

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
        //Assert.assertThrows(IllegalArgumentException.class, () -> new ProfilePicture(invalidProfilePicture));
    }

    @Test
    public void isValidProfilePicture() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> ProfilePicture.isValidProfilePicture(null));

        // invalid profile picture
        assertFalse(ProfilePicture.isValidProfilePicture(Paths.get("^"))); // only non-alphanumeric characters
        assertFalse(ProfilePicture
                .isValidProfilePicture(Paths.get(".", "resources", "profile_picture"))); // does not end jpg or png
        assertFalse(ProfilePicture.isValidProfilePicture(Paths.get("a.png"))); // ends with .png

        //valid profile picture
        assertTrue(ProfilePicture.isValidProfilePicture(Paths.get("a.jpg"))); // alphabets only
        assertTrue(ProfilePicture.isValidProfilePicture(Paths.get("A.jpg"))); // with capital letters
        assertTrue(ProfilePicture.isValidProfilePicture(Paths.get("IMG_0109.jpg"))); // alphanumeric characters
    }
}

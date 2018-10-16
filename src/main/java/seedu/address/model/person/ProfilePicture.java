package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.awt.image.BufferedImage;

/**
 * Represents a Person's profile picture file path in the address book.
 */
//@@author javenseow
public class ProfilePicture {

    public static final String MESSAGE_PROFILE_PICTURE_CONSTRAINTS =
            "Profile picture should be either a .jpeg or .png file, and not empty";

    /*
     * The first character of the file path must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String PROFILE_PICTURE_VALIDATION_REGEX = "([^\\s]+(\\.(?i)(jpg|png))$)";
    public final String filePath;
    private BufferedImage picture;

    /**
     * Constructs a {@code ProfilePicture}.
     *
     * @param path a valid file path.
     */
    public ProfilePicture(String path) {
        requireNonNull(path);
        checkArgument(isValidProfilePicture(path), MESSAGE_PROFILE_PICTURE_CONSTRAINTS);
        filePath = path;
    }

    public BufferedImage getPicture() {
        return picture;
    }

    /**
     * Returns true if a given string ends with .jpeg or .png.
     */
    public static boolean isValidProfilePicture(String test) {
        return test.matches(PROFILE_PICTURE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return filePath;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ProfilePicture) // instanceof handles null
            && filePath.equals(((ProfilePicture) other).filePath);
    }

    @Override
    public int hashCode() {
        return filePath.hashCode();
    }
}

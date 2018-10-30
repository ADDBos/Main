package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents a Person's profile picture file path in the address book.
 */
//@@author javenseow
public class ProfilePicture {

    public static final String MESSAGE_PROFILE_PICTURE_CONSTRAINTS =
            "Profile picture should be either a .jpg or .png file, and not empty";

    /*
     * The first character of the file path must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String PROFILE_PICTURE_VALIDATION_REGEX =
            "(?:([^:/?#]+):)?(?://([^/?#]*))?([^?#]*\\.(?:jpg|png))(?:\\?([^#]*))?(?:#(.*))?";
    public final Path filePath;

    /**
     * Constructs a {@code ProfilePicture}.
     *
     * @param path a valid file path.
     */
    public ProfilePicture(Path path) {
        requireNonNull(path);
        checkArgument(isValidProfilePicture(path), MESSAGE_PROFILE_PICTURE_CONSTRAINTS);
        filePath = Paths.get(path.toString());
    }

    public Path getPicture() {
        return filePath;
    }

    /**
     * Returns true if a given string ends with .jpg or .png.
     */
    public static boolean isValidProfilePicture(Path test) {
        return test.toString().matches(PROFILE_PICTURE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return filePath.toString();
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

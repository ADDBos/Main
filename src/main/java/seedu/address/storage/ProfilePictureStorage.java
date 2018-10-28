package seedu.address.storage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import seedu.address.model.person.Room;
//@@author javenseow
/**
 * Represents a storage for Profile Picture.
 */
public interface ProfilePictureStorage {

    /**
     * Returns the file path of the Email directory.
     */
    Path getProfilePicturePath();

    /**
     * Reads the profile picture from directory.
     */
    BufferedImage readProfilePicture(File file) throws IOException;

    /**
     * Saves the profile picture to local directory.
     * @param  image cannot be null.
     * @param  number cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveProfilePicture(BufferedImage image, Room number) throws IOException;
}

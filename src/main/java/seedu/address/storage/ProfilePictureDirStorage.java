package seedu.address.storage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;

import seedu.address.model.person.Room;

//@@author javenseow
/**
 * A class to access Profile Picture directory in the hard disk.
 */
public class ProfilePictureDirStorage implements ProfilePictureStorage {

    private Path dirPath;

    public ProfilePictureDirStorage(Path dirPath) {
        this.dirPath = dirPath;
    }

    @Override
    public Path getProfilePicturePath() {
        return dirPath;
    }

    @Override
    public BufferedImage readProfilePicture(File file) throws IOException {
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            throw e;
        }
        return image;
    }

    @Override
    public void saveProfilePicture(BufferedImage image, Room number) throws IOException {
        try {
            File copiedFile = new File(dirPath + "/" + number.value + ".jpg");
            ImageIO.write(image, "jpg", copiedFile);
        } catch (IOException e) {
            throw e;
        }
    }
}

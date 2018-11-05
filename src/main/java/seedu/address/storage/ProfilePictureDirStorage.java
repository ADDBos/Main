package seedu.address.storage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;

import seedu.address.commons.util.FileUtil;
import seedu.address.model.person.Room;

//@@author javenseow
/**
 * A class to access Profile Picture directory in the hard disk.
 */
public class ProfilePictureDirStorage implements ProfilePictureStorage {

    private static final String JPG = ".jpg";
    private Path dirPath;
    private Path opPath;

    public ProfilePictureDirStorage(Path dirPath, Path opPath) {
        this.dirPath = dirPath;
        this.opPath = opPath;
    }

    @Override
    public Path getProfilePicturePath() {
        return dirPath;
    }

    @Override
    public BufferedImage readProfilePicture(File file) throws IOException {
        BufferedImage image;
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
            File copiedFile = new File(dirPath + "/" + number.value + JPG);
            File outputFile = new File(opPath + "/" + number.value + JPG);
            FileUtil.createIfMissing(copiedFile.toPath());
            FileUtil.createIfMissing(outputFile.toPath());
            ImageIO.write(image, "jpg", copiedFile);
            ImageIO.write(image, "jpg", outputFile);
        } catch (IOException e) {
            throw e;
        }
    }
}

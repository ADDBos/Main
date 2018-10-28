package seedu.address.ui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Logger;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.commons.core.LogsCenter;

import javax.imageio.ImageIO;

import static seedu.address.logic.commands.ImageCommand.MESSAGE_FILE_ERROR;

/**
 * Controller for profile page.
 */
//@@author javenseow
public class ProfileWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(ProfileWindow.class);
    private static final String     FXML = "ProfileWindow.fxml";

    @FXML
    private HBox profilePane;
    @FXML
    private Label picture;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label room;
    @FXML
    private Label school;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    /**
     * Creates a new ProfileWindow.
     *
     * @param root Stage to use as the root of the ProfileWindow.
     */
    public ProfileWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new ProfileWindow.
     */
    public ProfileWindow() {
        this(new Stage());
    }

    /**
     * Shows the profile window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show(Person person) {
        logger.fine("Showing profile page of " + person.getName().fullName + ".");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        room.setText(person.getRoom().value);
        school.setText(person.getSchool().value);
        email.setText(person.getEmail().value);
        ImageView graphic;
        BufferedImage profile = (BufferedImage) person.getProfilePicture().getPicture();
        Image image = SwingFXUtils.toFXImage(profile, null);
        graphic = new ImageView(image);
        picture.setGraphic(graphic);
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        getRoot().show();
    }

    /**
     * Returns true if the profile window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Focuses on the profile window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}

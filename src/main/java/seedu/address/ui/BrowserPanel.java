package seedu.address.ui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.EmailViewEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ProfileViewEvent;
import seedu.address.model.EmailModel;
import seedu.address.model.person.Person;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String PERSON_PROFILE_PAGE = "profile.html";
    public static final String SEARCH_PAGE_URL =
            "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    private void loadPersonPage(Person person) {
        loadPage(SEARCH_PAGE_URL + person.getName().fullName);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection());
    }

    //@@author EatOrBeEaten
    @Subscribe
    private void handleEmailViewEvent(EmailViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadEmail(event.getEmailModel());
    }

    /**
     * Loads HTML text preview of email.
     * @param emailModel The emailModel containing the saved email.
     */
    public void loadEmail(EmailModel emailModel) {
        Platform.runLater(() -> browser.getEngine().loadContent(emailModel.getPreview()));
    }

    @Subscribe
    private void handleProfileViewEvent(ProfileViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadProfile(event.getPersonSelected());
    }

    /**
     * Loads HTML page of profile.
     * @param person The person that the profile will show.
     */
    public void loadProfile(Person person) {
        String profileView = loadProfileHtml(person);
        Platform.runLater(() -> browser.getEngine().loadContent(profileView));
    }

    /**
     * Loads the HTML code of profile view.
     * @param person The person that the code will be for.
     */
    private String loadProfileHtml(Person person) {
        File htmlTemplateFile = new File("./src/main/resources/ProfileWindow.html");
        String htmlString = null;
        try {
            htmlString = FileUtils.readFileToString(htmlTemplateFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        htmlString = htmlString.replace("$name", person.getName().fullName);
        htmlString = htmlString.replace("$cca", person.getTags().toString());
        htmlString = htmlString.replace("$room", person.getRoom().value);
        htmlString = htmlString.replace("$number", person.getPhone().value);
        htmlString = htmlString.replace("$school", person.getSchool().value);
        htmlString = htmlString.replace("$email", person.getEmail().value);
        htmlString = htmlString.replace("$profileRoom", person.getRoom().value.toLowerCase());

        return htmlString;
    }
}

package seedu.address.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.EmailNotFoundEvent;
import seedu.address.commons.events.ui.EmailViewEvent;
import seedu.address.commons.events.ui.ListEmailsEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ProfileViewEvent;
import seedu.address.model.EmailModel;
import seedu.address.model.person.Person;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String PROFILE_PAGE = "/ProfileWindow.html";
    public static final String PICTURE_LINK = "/Hallper/out/production/resources/profile_picture/";
    public static final String JPG = ".jpg";
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

    //@@author EatOrBeEaten

    /**
     * Loads HTML text preview of email.
     *
     * @param emailModel The emailModel containing the saved email.
     */
    private void loadEmail(EmailModel emailModel) {
        Platform.runLater(() -> browser.getEngine().loadContent(emailModel.getPreview()));
    }
    //@@author

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

    @Subscribe
    private void handleEmailNotFoundEvent(EmailNotFoundEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> browser.getEngine().loadContent(event.toString()));

    }

    @Subscribe
    private void handleListEmailsEvent(ListEmailsEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> browser.getEngine().loadContent(event.toString()));
    }

    @Subscribe
    private void handleProfileViewEvent(ProfileViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadProfile(event.getPersonSelected());
    }

    //@@author javenseow
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
        String htmlString = "";
        String pictureString = "";
        String tempString;
        try {
            InputStream profilePage = getClass().getResourceAsStream(PROFILE_PAGE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(profilePage));
            while ((tempString = reader.readLine()) != null) {
                htmlString += tempString;
            }
            pictureString += PICTURE_LINK + person.getRoom().value.toLowerCase() + JPG;
        } catch (IOException e) {
            e.printStackTrace();
        }

        htmlString = htmlString.replace("$name", person.getName().fullName);
        htmlString = htmlString.replace("$cca", person.getTags().toString());
        htmlString = htmlString.replace("$room", person.getRoom().value);
        htmlString = htmlString.replace("$number", person.getPhone().value);
        htmlString = htmlString.replace("$school", person.getSchool().value);
        htmlString = htmlString.replace("$email", person.getEmail().value);
        htmlString = htmlString.replace("$profileURL", pictureString);

        System.out.println(htmlString);

        return htmlString;
    }
}

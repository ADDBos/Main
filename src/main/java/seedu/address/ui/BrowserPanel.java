package seedu.address.ui;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.html.HTMLImageElement;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String PERSON_PROFILE_PAGE = "profile.html";
    public static final String SEARCH_PAGE_URL =
            "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";
    public static final String BREAK = "<br />";

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
        String name = "Name: " + person.getName().fullName + BREAK;
        String cca = "CCA: " + person.getTags().toString() + BREAK;
        String room = "Room: " + person.getRoom().value + BREAK;
        String number = "Number: " + person.getPhone().value + BREAK;
        String school = "School: " + person.getSchool().value + BREAK;
        String email = "Email: " + person.getEmail().value + BREAK;

        /*browser.getEngine()
                .getLoadWorker()
                .stateProperty()
                .addListener(new ChangeListener<Worker.State>() {
                    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                        Document doc = null;
                        try {
                            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                            doc = dBuilder.parse();
                            doc.getDocumentElement().normalize();
                        } catch (ParserConfigurationException e) {
                            e.printStackTrace();
                        } catch (SAXException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (newState == Worker.State.SUCCEEDED) {
                            NodeList nodeList = doc.getElementsByTagName("profile_picture");
                            for (int i = 0; i < nodeList.getLength(); i++) {
                                HTMLImageElement n = (HTMLImageElement) nodeList.item(1);
                                String path = n.getSrc();
                                if (path.startsWith("file://")) {
                                    path = path.substring(7, path.length());
                                } else if (path.startsWith("jar:")) {
                                    path = path.substring(4, path.length());
                                }
                                URL m = BrowserPanel.class.getResource(path);
                                if (m != null) {
                                    n.setSrc(m.toExternalForm());
                                }
                            }
                        }
                    }
                });*/
        String profilePicture = "<img src=" + person.getProfilePicture().toString() + "\"" + " alt=\"Profile Picture of " +
                person.getName().fullName + "\" style=\"width:250px;height:260px;\">" + BREAK;
        String html = name + cca + room + number + school + email + profilePicture;
        System.out.println(html);

        return html;
    }
}

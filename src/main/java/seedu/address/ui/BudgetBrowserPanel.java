package seedu.address.ui;

import java.net.URL;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.cca.Cca;
import seedu.address.model.person.Person;

/**
 * The Browser Panel of the App.
 */
public class BudgetBrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String SEARCH_PAGE_URL =
        "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";

    private static final String FXML = "BudgetBrowserPanel.fxml";

    public final Cca cca;

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private Label id;
    @FXML
    private Label ccaName;

    @FXML
    private Label head;
    @FXML
    private Label viceHead;
    @FXML
    private Label budget;
    @FXML
    private Label spent;
    @FXML
    private Label outstanding;
    @FXML
    private Label transaction;
    @FXML
    private FlowPane tags;

    @FXML
    private WebView browser;

    public BudgetBrowserPanel(Cca cca) {
        super(FXML);
        this.cca = cca;
        ccaName.setText(cca.getCcaName());
        head.setText(cca.getHead());
        viceHead.setText(cca.getViceHead());
        budget.setText(String.valueOf(cca.getGivenBudget()));
        spent.setText(String.valueOf(cca.getSpent()));
        outstanding.setText(String.valueOf(cca.getOutstanding()));
        transaction.setText(cca.getTransactionLog());

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
}

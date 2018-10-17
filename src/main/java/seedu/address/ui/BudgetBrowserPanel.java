package seedu.address.ui;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.XmlToHmtl;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.CcaPanelSelectionChangedEvent;
import seedu.address.model.cca.Cca;

/**
 * The Browser Panel of the App.
 *
 * @author ericyjw
 */
public class BudgetBrowserPanel extends UiPart<Region> {

    public static final String BUDGET_PAGE = "./data/ccabook.html";
    public static final String DEFAULT_PAGE = "default.html";

    private static final String FXML = "BudgetBrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public BudgetBrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    /**
     * Load the budget page of a chosen CCA
     *
     * @param cca chosen CCA
     */
    private void loadCcaBudgetPage(Cca cca) {
        String chosen = cca.getCcaName();
        XmlToHmtl.convertCcaBook(chosen);
        File budgetFile = new File(BUDGET_PAGE);
        try {
            URL url = budgetFile.toURI().toURL();
            loadPage(url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

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
    private void handleCcaPanelSelectionChangedEvent(CcaPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadCcaBudgetPage(event.getNewSelection());
    }
}

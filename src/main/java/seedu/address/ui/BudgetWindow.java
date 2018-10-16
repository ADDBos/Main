package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.logic.Logic;
import seedu.address.model.UserPrefs;

/**
 * The Budget Window. Displays the CCAs available and the budget information of each CCA
 *
 * @author ericyjw
 */
public class BudgetWindow extends UiPart<Stage> {

    private static final String FXML = "BudgetWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private BudgetBrowserPanel budgetBrowserPanel;
    private CcaListPanel ccaListPanel;
    private UserPrefs prefs;

    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private MenuItem budgetMenuItem;

    @FXML
    private StackPane ccaListPanelPlaceholder;

    public BudgetWindow(Stage root) {
        super(FXML, root);
    }

    public BudgetWindow(Logic logic, UserPrefs prefs) {
        this(new Stage());
        this.prefs = prefs;
        this.logic = logic;
    }


    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        budgetBrowserPanel = new BudgetBrowserPanel();
        browserPlaceholder.getChildren().add(budgetBrowserPanel.getRoot());

        ccaListPanel = new CcaListPanel(logic.getFilteredCcaList());
        ccaListPanelPlaceholder.getChildren().add(ccaListPanel.getRoot());
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    public CcaListPanel getCcaListPanel() {
        return ccaListPanel;
    }

    void releaseResources() {
        budgetBrowserPanel.freeResources();
    }

    /**
     * Show budget window
     */
    public void show() {
        logger.fine("Showing budget list of the hostel.");
        getRoot().show();
        fillInnerParts();
    }

    /**
     * Returns true if the budget window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Focuses on the budget window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

}

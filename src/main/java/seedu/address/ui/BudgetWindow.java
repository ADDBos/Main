package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ExitAppRequestEvent;

import seedu.address.logic.Logic;
import seedu.address.model.UserPrefs;

/**
 * Controller for a budget page
 */
public class BudgetWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(BudgetWindow.class);
    private static final String FXML = "BudgetWindow.fxml";

    private BrowserPanel browserPanel;
    private CcaListPanel ccaListPanel;
    private UserPrefs prefs;
    private Logic logic;

    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private StackPane ccaListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;


    /**
     * Creates a new BudgetWindow.
     *
     * @param root Stage to use as the root of the BudgetWindow.
     */
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

        browserPanel = new BrowserPanel();
        browserPlaceholder.getChildren().add(browserPanel.getRoot());

        CommandBox commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        ccaListPanel = new CcaListPanel(logic.getFilteredCcaList());
        ccaListPanelPlaceholder.getChildren().add(ccaListPanel.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        // BudgetResultDisplay budgetResultDisplay = new BudgetResultDisplay();
        // budgetResultDisplayPlaceholder.getChildren().add(budgetResultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getBudgetBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    /**
     * Shows the budget window.
     *
     * @throws IllegalStateException <ul>
     * <li>
     * if this method is called on a thread other than the JavaFX Application Thread.
     * </li>
     * <li>
     * if this method is called during animation or layout processing.
     * </li>
     * <li>
     * if this method is called on the primary stage.
     * </li>
     * <li>
     * if {@code dialogStage} is already showing.
     * </li>
     * </ul>
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

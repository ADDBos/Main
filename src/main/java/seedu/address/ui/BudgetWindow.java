package seedu.address.ui;

import java.util.Optional;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ExitBudgetWindowRequestEvent;
import seedu.address.logic.Logic;
import seedu.address.model.UserPrefs;
import seedu.address.model.cca.CcaName;

//@@author ericyjw
/**
 * The Budget Window. Displays the CCAs available and the budget information of each CCA
 *
 * @author ericyjw
 */
public class BudgetWindow extends UiPart<Stage> {

    private static final String FXML = "BudgetWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Logic logic;
    private MainWindow mainWindow;

    private boolean isShowing;

    // Independent Ui parts residing in this Ui container
    private BudgetBrowserPanel budgetBrowserPanel;
    private CcaListPanel ccaListPanel;
    private UserPrefs prefs;

    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private MenuItem mainMenuItem;

    @FXML
    private StackPane ccaListPanelPlaceholder;


    public BudgetWindow(Stage root) {
        super(FXML, root);
    }

    public BudgetWindow(Logic logic, UserPrefs prefs, MainWindow mainWindow) {
        this(new Stage());
        this.mainWindow = mainWindow;
        this.prefs = prefs;
        this.logic = logic;
        this.isShowing = false;

        setAccelerators();
    }

    private void setAccelerators() {
        setAccelerator(mainMenuItem, KeyCombination.valueOf("F3"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     * @param ccaName the name of the CCA to be viewed
     */
    private void fillInnerParts(CcaName ccaName) {
        if (Optional.ofNullable(ccaName).isPresent()) {
            budgetBrowserPanel = new BudgetBrowserPanel(ccaName);
        } else {
            budgetBrowserPanel = new BudgetBrowserPanel();
        }
        browserPlaceholder.getChildren().add(budgetBrowserPanel.getRoot());

        ccaListPanel = new CcaListPanel(logic.getFilteredCcaList());
        ccaListPanelPlaceholder.getChildren().add(ccaListPanel.getRoot());
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleMain() {
        if (!mainWindow.isShowing()) {
            mainWindow.show();
        } else {
            mainWindow.focus();
        }
    }

    /**
     * Closes the budget window.
     */
    @FXML
    private void handleExit() {
        this.isShowing = false;
        raise(new ExitBudgetWindowRequestEvent());
    }

    public CcaListPanel getCcaListPanel() {
        return ccaListPanel;
    }

    void releaseResources() {
        budgetBrowserPanel.freeResources();
    }

    /**
     * Show budget window
     * @param ccaName
     */
    public void show(CcaName ccaName) {
        logger.fine("Showing budget list of the hostel.");
        getRoot().show();
        fillInnerParts(ccaName);
        this.isShowing = true;
    }

    /**
     * Returns true if the budget window is currently being shown.
     */
    public boolean isShowing() {
        return this.isShowing;
    }

    /**
     * Focuses on the budget window.
     */
    public void focus(CcaName ccaName) {
        fillInnerParts(ccaName);
        getRoot().requestFocus();
    }

}

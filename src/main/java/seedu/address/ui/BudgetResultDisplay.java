package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewBudgetResultAvailableEvent;
import seedu.address.model.cca.Cca;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class BudgetResultDisplay extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(BudgetResultDisplay.class);
    private static final String FXML = "ResultDisplay.fxml";

    public final Cca cca;

    private final StringProperty displayed = new SimpleStringProperty("");

    @FXML
    private TextArea resultDisplay;
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


    public BudgetResultDisplay(Cca cca) {
        super(FXML);

        this.cca = cca;
        ccaName.setText(cca.getCcaName());
        head.setText(cca.getHead());
        viceHead.setText(cca.getViceHead());
        budget.setText(String.valueOf(cca.getGivenBudget()));
        spent.setText(String.valueOf(cca.getSpent()));
        outstanding.setText(String.valueOf(cca.getOutstanding()));
        transaction.setText(cca.getTransactionLog());

        resultDisplay.textProperty().bind(displayed);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleNewBudgetResultAvailableEvent(NewBudgetResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> displayed.setValue(event.message));
    }

}

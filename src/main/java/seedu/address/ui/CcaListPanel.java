package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.CcaPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.cca.Cca;

/**
 * Panel containing the list of CCAs.
 *
 * @author ericyjw
 */
public class CcaListPanel extends UiPart<Region> {
    private static final String FXML = "CcaListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Cca> ccaListView;

    public CcaListPanel(ObservableList<Cca> ccaList) {
        super(FXML);
        setConnections(ccaList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Cca> ccaList) {
        ccaListView.setItems(ccaList);
        ccaListView.setCellFactory(listView -> new CcaListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        ccaListView.getSelectionModel().selectedItemProperty()
            .addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    logger.fine("Selection in cca list panel changed to : '" + newValue + "'");
                    raise(new CcaPanelSelectionChangedEvent(newValue));
                }
            });
    }

    /**
     * Scrolls to the {@code CcaCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            ccaListView.scrollTo(index);
            ccaListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Cca} using a {@code CcaCard}.
     */
    class CcaListViewCell extends ListCell<Cca> {
        @Override
        protected void updateItem(Cca cca, boolean empty) {
            super.updateItem(cca, empty);

            if (empty || cca == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CcaCard(cca, getIndex() + 1).getRoot());
            }
        }
    }

}

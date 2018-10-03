package seedu.address.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.CcaPanelSelectionChangedEvent;
import seedu.address.model.cca.Cca;

/**
 * Panel containing the list of persons.
 */
public class CcaListPanel extends UiPart<Region> {
    private static final String FXML = "CcaListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CcaListPanel.class);

    @javafx.fxml.FXML
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
                    logger.fine("Selection in person list panel changed to : '" + newValue + "'");
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

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code CCA} using a {@code CcaCard}.
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

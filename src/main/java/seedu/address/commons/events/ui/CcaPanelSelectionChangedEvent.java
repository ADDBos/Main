package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.cca.Cca;

/**
 * Represents a selection change in the CCA List Panel
 *
 * @author ericyjw
 */
public class CcaPanelSelectionChangedEvent extends BaseEvent {

    private final Cca newSelection;

    public CcaPanelSelectionChangedEvent(Cca newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Cca getNewSelection() {
        return newSelection;
    }
}

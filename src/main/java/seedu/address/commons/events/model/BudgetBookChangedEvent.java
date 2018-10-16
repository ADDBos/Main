package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyBudgetBook;

/**
 * Indicates the BudgetBook in the model has changed
 *
 * @author ericyjw
 */
public class BudgetBookChangedEvent extends BaseEvent {

    public final ReadOnlyBudgetBook data;

    public BudgetBookChangedEvent(ReadOnlyBudgetBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of CCAs " + data.getCcaList().size();
    }
}

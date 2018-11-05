package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.cca.CcaName;

//@@author ericyjw
/**
 * An event requesting to view the budget page according to the input of the user.
 *
 * @author ericyjw
 */
public class ShowBudgetViewEvent extends BaseEvent {

    private CcaName ccaName;

    public ShowBudgetViewEvent(CcaName ccaName) {
        super();
        this.ccaName = ccaName;
    }

    public CcaName getCcaName() {
        return ccaName;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}

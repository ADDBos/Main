package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

//@@author GilgameshTC

/**
 * Indicates a calendar has been requested to be loaded
 */
public class LoadCalendarEvent extends BaseEvent {

    public final String calendarName;

    public LoadCalendarEvent(String calendarName) {
        this.calendarName = calendarName;
    }

    @Override
    public String toString() {
        return "request for calendar " + calendarName + " to be loaded";
    }
}

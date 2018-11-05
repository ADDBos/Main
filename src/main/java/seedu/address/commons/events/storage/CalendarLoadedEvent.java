package seedu.address.commons.events.storage;

import net.fortuna.ical4j.model.Calendar;
import seedu.address.commons.events.BaseEvent;

//@@author GilgameshTC

/**
 * Indicates a calendar has been loaded
 */
public class CalendarLoadedEvent extends BaseEvent {

    public final Calendar calendar;
    public final String calendarName;

    public CalendarLoadedEvent(Calendar calendar, String calendarName) {
        this.calendar = calendar;
        this.calendarName = calendarName;
    }

    @Override
    public String toString() {
        return "calendar " + calendarName + " loaded";
    }
}

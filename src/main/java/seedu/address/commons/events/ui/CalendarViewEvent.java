package seedu.address.commons.events.ui;

import net.fortuna.ical4j.model.Calendar;
import seedu.address.commons.events.BaseEvent;

//@@author GilgameshTC

/**
 * Indicates a request to view calendar.
 */
public class CalendarViewEvent extends BaseEvent {

    public final Calendar calendar;
    public final String calendarName;

    public CalendarViewEvent(Calendar calendar, String calendarName) {
        this.calendar = calendar;
        this.calendarName = calendarName;
    }

    @Override
    public String toString() {
        return "calendar " + calendarName + " viewed on UI";
    }
}

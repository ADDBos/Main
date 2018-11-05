package seedu.address.commons.events.model;

import net.fortuna.ical4j.model.Calendar;
import seedu.address.commons.events.BaseEvent;

//@@author GilgameshTC
/** Indicates a calendar has been created*/
public class CalendarCreatedEvent extends BaseEvent {

    public final Calendar calendar;
    public final String calendarName;

    public CalendarCreatedEvent(Calendar calendar, String calendarName) {
        this.calendar = calendar;
        this.calendarName = calendarName;
    }

    @Override
    public String toString() {
        return "calendar " + calendarName + " created";
    }
}

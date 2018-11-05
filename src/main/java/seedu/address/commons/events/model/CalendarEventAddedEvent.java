package seedu.address.commons.events.model;

import net.fortuna.ical4j.model.Calendar;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.calendar.Month;
import seedu.address.model.calendar.Year;

//@@author GilgameshTC
/** Indicates an event with a specific time frame has been added into the calendar*/
public class CalendarEventAddedEvent extends BaseEvent {

    public final Year year;
    public final Month month;
    public final int startDate;
    public final int startHour;
    public final int startMin;
    public final int endDate;
    public final int endHour;
    public final int endMin;
    public final String title;
    public final Calendar calendar;

    public CalendarEventAddedEvent(Year year, Month month, int startDate, int startHour, int startMin,
                                   int endDate, int endHour, int endMin, String title, Calendar calendar) {
        this.year = year;
        this.month = month;
        this.startDate = startDate;
        this.startHour = startHour;
        this.startMin = startMin;
        this.endDate = endDate;
        this.endHour = endHour;
        this.endMin = endMin;
        this.title = title;
        this.calendar = calendar;

    }

    @Override
    public String toString() {
        return startDate + "/" + month + "/" + year + " - "
                + endDate + "/" + month + "/" + year + " [" + title + "]";

    }
}

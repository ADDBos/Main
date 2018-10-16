package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.calendar.Month;
import seedu.address.model.calendar.Year;

//@@author GilgameshTC
/** Indicates an event has been deleted from the monthly calendar*/
public class CalendarEventDeletedEvent extends BaseEvent {

    public final Year year;
    public final Month month;
    public final int startDate;
    public final int endDate;
    public final String title;

    public CalendarEventDeletedEvent(Year year, Month month, int startDate, int endDate, String title) {
        this.year = year;
        this.month = month;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;

    }

    @Override
    public String toString() {
        return startDate + "/" + month + "/" + year + " - "
                + endDate + "/" + month + "/" + year + " [" + title + "]";

    }
}

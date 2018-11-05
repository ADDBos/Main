package seedu.address.commons.events.model;

import net.fortuna.ical4j.model.Calendar;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.calendar.Month;
import seedu.address.model.calendar.Year;

//@@author GilgameshTC
/** Indicates an all day event has been added into the calendar*/
public class AllDayEventAddedEvent extends BaseEvent {

    public final Year year;
    public final Month month;
    public final int date;
    public final String title;
    public final Calendar calendar;

    public AllDayEventAddedEvent(Year year, Month month, int date, String title, Calendar calendar) {
        this.year = year;
        this.month = month;
        this.date = date;
        this.title = title;
        this.calendar = calendar;

    }

    @Override
    public String toString() {
        return date + "/" + month + "/" + year + " - " + title;
    }

}

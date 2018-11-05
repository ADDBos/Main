package seedu.address.model;

import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Categories;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.FixedUidGenerator;
import net.fortuna.ical4j.util.UidGenerator;
import seedu.address.model.calendar.Month;
import seedu.address.model.calendar.Year;

//@@author GilgameshTC
/**
 * Wraps Calendar Data
 */
public class CalendarModel {

    // Light Blue
    public static final String EVENT_COLOR_IN_CAL = "group13";

    private Map<Year, Set<Month>> existingCalendar;
    // Field to store calendar loaded by user if any.
    // User can only load at most one calendar at any point of time.
    private Calendar loadedCalendar;
    private String loadedCalendarName;
    private VEvent eventToBeRemoved;
    private Map<Month, Integer> monthToConstantMap;

    public CalendarModel(Map<Year, Set<Month>> existingCalendar) {
        this.existingCalendar = existingCalendar;
        this.loadedCalendar = null;
        this.loadedCalendarName = null;
        this.eventToBeRemoved = null;
        this.monthToConstantMap = initializeMonthToStringMap();

    }

    /** Provide a mapping between Month objects and java.util.Calendar int constants. */
    private Map<Month, Integer> initializeMonthToStringMap() {
        HashMap<Month, Integer> map = new HashMap<>();
        map.put(new Month("JAN"), java.util.Calendar.JANUARY);
        map.put(new Month("FEB"), java.util.Calendar.FEBRUARY);
        map.put(new Month("MAR"), java.util.Calendar.MARCH);
        map.put(new Month("APR"), java.util.Calendar.APRIL);
        map.put(new Month("MAY"), java.util.Calendar.MAY);
        map.put(new Month("JUN"), java.util.Calendar.JUNE);
        map.put(new Month("JUL"), java.util.Calendar.JULY);
        map.put(new Month("AUG"), java.util.Calendar.AUGUST);
        map.put(new Month("SEP"), java.util.Calendar.SEPTEMBER);
        map.put(new Month("OCT"), java.util.Calendar.OCTOBER);
        map.put(new Month("NOV"), java.util.Calendar.NOVEMBER);
        map.put(new Month("DEC"), java.util.Calendar.DECEMBER);
        return map;

    }

    /** Checks if calendar to be created already exists. */
    public boolean isExistingCalendar(Year year, Month month) {
        Set<Month> yearExists = existingCalendar.get(year);
        if (yearExists != null) {
            for (Month existingMonths : yearExists) {
                if (month.equals(existingMonths)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks if calendar to be edited is already loaded.
     */
    public boolean isLoadedCalendar(Year year, Month month) {
        String calendarName = month + "-" + year;
        if (this.loadedCalendar == null) {
            return false;
        } else {
            return (this.loadedCalendarName.compareTo(calendarName) == 0);
        }
    }

    /** Checks if date is valid in a particular month. */
    public boolean isValidDate(Year year, Month month, int date) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(java.util.Calendar.YEAR, Integer.parseInt(year.toString()));
        cal.set(java.util.Calendar.MONTH, monthToConstantMap.get(month));
        int maximumDate = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
        return (date <= maximumDate) && date > 0;

    }

    /** Checks if the hours and minutes are valid. */
    public boolean isValidTime(int hour, int minute) {
        return (hour >= 0) && (hour < 24) && (minute >= 0) && (minute < 60);
    }

    /**
     * Checks if it is a valid time frame (end date not earlier than start date).
     * Assumes that date, hour and minutes are valid.
     */
    public boolean isValidTimeFrame(int startDate, int startHour, int startMinute,
                                    int endDate, int endHour, int endMinute) {
        if (startDate < endDate) {
            return true;
        } else {
            if (startDate > endDate) {
                return false;
            } else if (startHour < endHour) {
                return true;
            } else if (startHour > endHour) {
                return false;
            } else {
                return startMinute < endMinute;
            }
        }
    }

    /** Setter method for loadedCalendar field. */
    private void setLoadedCalendar(Calendar calendar, String calendarName) {
        this.loadedCalendar = calendar;
        this.loadedCalendarName = calendarName;
    }

    /** Creates the calendar file. */
    public Calendar createCalendar(Year year, Month month) throws IOException {
        // Initialize the calendar
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);

        // ----- Create dummy Christmas event -----
        // Start Date
        java.util.Calendar sDate = new GregorianCalendar();
        sDate.set(java.util.Calendar.YEAR, Integer.parseInt(year.toString()));
        sDate.set(java.util.Calendar.MONTH, java.util.Calendar.NOVEMBER);
        sDate.set(java.util.Calendar.DAY_OF_MONTH, 25);
        sDate.set(java.util.Calendar.HOUR_OF_DAY, 0);
        sDate.set(java.util.Calendar.MINUTE, 0);
        sDate.set(java.util.Calendar.SECOND, 0);

        // End Date
        java.util.Calendar eDate = new GregorianCalendar();
        eDate.set(java.util.Calendar.YEAR, Integer.parseInt(year.toString()));
        eDate.set(java.util.Calendar.MONTH, java.util.Calendar.NOVEMBER);
        eDate.set(java.util.Calendar.DAY_OF_MONTH, 25);
        eDate.set(java.util.Calendar.HOUR_OF_DAY, 23);
        eDate.set(java.util.Calendar.MINUTE, 59);
        eDate.set(java.util.Calendar.SECOND, 59);

        // Create the event
        DateTime start = new DateTime(sDate.getTime());
        DateTime end = new DateTime(eDate.getTime());
        VEvent christmas = new VEvent(start, end, "Christmas Day");

        // Add Category Color to event
        Categories categories = new Categories();
        // light blue color in iCalendarAgenda
        categories.setValue(EVENT_COLOR_IN_CAL);
        christmas.getProperties().add(categories);

        // Generate a UID for the event
        UidGenerator ug = new FixedUidGenerator("1");
        christmas.getProperties().add(ug.generateUid());
        calendar.getComponents().add(christmas);
        // ------------------------------

        // Update existing calendar map
        Set<Month> yearOfCal = existingCalendar.get(year);
        if (yearOfCal == null) {
            Set<Month> newYearOfCal = new HashSet<>();
            newYearOfCal.add(month);
            existingCalendar.put(year, newYearOfCal);
        } else {
            yearOfCal.add(month);
        }

        return calendar;
    }

    /** Load and parse the requested calendar file. */
    public void loadCalendar(Calendar calendarToBeLoaded, String calendarName) {
        setLoadedCalendar(calendarToBeLoaded, calendarName);
    }

    /** Creates a new all day event in the loaded Calendar. */
    public Calendar createAllDayEvent(Year year, Month month, int date, String title) throws IOException {
        // Start Date
        java.util.Calendar sDate = new GregorianCalendar();
        sDate.set(java.util.Calendar.YEAR, Integer.parseInt(year.toString()));
        sDate.set(java.util.Calendar.MONTH, monthToConstantMap.get(month));
        sDate.set(java.util.Calendar.DAY_OF_MONTH, date);
        sDate.set(java.util.Calendar.HOUR_OF_DAY, 0);
        sDate.set(java.util.Calendar.MINUTE, 0);
        sDate.set(java.util.Calendar.SECOND, 0);

        // End Date
        java.util.Calendar eDate = new GregorianCalendar();
        eDate.set(java.util.Calendar.YEAR, Integer.parseInt(year.toString()));
        eDate.set(java.util.Calendar.MONTH, monthToConstantMap.get(month));
        eDate.set(java.util.Calendar.DAY_OF_MONTH, date);
        eDate.set(java.util.Calendar.HOUR_OF_DAY, 23);
        eDate.set(java.util.Calendar.MINUTE, 59);
        eDate.set(java.util.Calendar.SECOND, 59);

        // Create the event
        DateTime start = new DateTime(sDate.getTime());
        DateTime end = new DateTime(eDate.getTime());
        VEvent newEvent = new VEvent(start, end, title);

        // Add Category Color to event
        Categories categories = new Categories();
        // light blue color in iCalendarAgenda
        categories.setValue(EVENT_COLOR_IN_CAL);
        newEvent.getProperties().add(categories);

        // Generate a UID for the event
        UidGenerator ug = new FixedUidGenerator("1");
        newEvent.getProperties().add(ug.generateUid());
        loadedCalendar.getComponents().add(newEvent);

        // Return the updated calendar
        return loadedCalendar;

    }

    /** Creates an event in the loaded Calendar with the specified time frame. */
    public Calendar createEvent(Year year, Month month, int startDate, int startHour, int startMin,
                                int endDate, int endHour, int endMin, String title) throws IOException {
        // Start Date
        java.util.Calendar sDate = new GregorianCalendar();
        sDate.set(java.util.Calendar.YEAR, Integer.parseInt(year.toString()));
        sDate.set(java.util.Calendar.MONTH, monthToConstantMap.get(month));
        sDate.set(java.util.Calendar.DAY_OF_MONTH, startDate);
        sDate.set(java.util.Calendar.HOUR_OF_DAY, startHour);
        sDate.set(java.util.Calendar.MINUTE, startMin);
        sDate.set(java.util.Calendar.SECOND, 0);

        // End Date
        java.util.Calendar eDate = new GregorianCalendar();
        eDate.set(java.util.Calendar.YEAR, Integer.parseInt(year.toString()));
        eDate.set(java.util.Calendar.MONTH, monthToConstantMap.get(month));
        eDate.set(java.util.Calendar.DAY_OF_MONTH, endDate);
        eDate.set(java.util.Calendar.HOUR_OF_DAY, endHour);
        eDate.set(java.util.Calendar.MINUTE, endMin);
        eDate.set(java.util.Calendar.SECOND, 0);

        // Create the event
        DateTime start = new DateTime(sDate.getTime());
        DateTime end = new DateTime(eDate.getTime());
        VEvent newEvent = new VEvent(start, end, title);

        // Add Category Color to event
        Categories categories = new Categories();
        // light blue color in iCalendarAgenda
        categories.setValue(EVENT_COLOR_IN_CAL);
        newEvent.getProperties().add(categories);

        // Generate a UID for the event
        UidGenerator ug = new FixedUidGenerator("1");
        newEvent.getProperties().add(ug.generateUid());
        loadedCalendar.getComponents().add(newEvent);

        // Return the updated calendar
        return loadedCalendar;
    }

    /**
     * Checks if an event exists in the loaded Calendar and returns the event.
     * Private method that should only be called by deleteEvent.
     */
    private VEvent retrieveEvent(int startDate, int endDate, String title) {
        ComponentList<CalendarComponent> events = loadedCalendar.getComponents(Component.VEVENT);
        VEvent eventToReturn = null;

        for (CalendarComponent event : events) {
            VEvent vevent = (VEvent) event;
            if (isSameEvent(startDate, endDate, title, vevent)) {
                eventToReturn = vevent;
                break;
            }
        }
        return eventToReturn;
    }

    /**
     * An event is considered the same in terms of its title and start - end date.
     */
    private boolean isSameEvent(int startDate, int endDate, String title, VEvent event) {
        boolean result;

        // Parse title from the event to check
        String titleToCheck = event.getSummary().getValue();
        // Check if title is the same
        result = title.compareTo(titleToCheck) == 0;

        // Parse start date information from the event to check
        DtStart dtStart = event.getStartDate();
        Date sDate = dtStart.getDate();
        java.util.Calendar sCal = java.util.Calendar.getInstance();
        sCal.setTime(sDate);
        int startDateToCheck = sCal.get(java.util.Calendar.DAY_OF_MONTH);
        result = result && (startDate == startDateToCheck);

        // Parse end date information from the event to check
        DtEnd dtEnd = event.getEndDate();
        Date eDate = dtEnd.getDate();
        java.util.Calendar eCal = java.util.Calendar.getInstance();
        eCal.setTime(eDate);
        int endDateToCheck = eCal.get(java.util.Calendar.DAY_OF_MONTH);
        return result && (endDate == endDateToCheck);

    }

    /** Checks if this specific event exists in the loaded Calendar. */
    public boolean isExistingEvent(int startDate, int endDate, String title) {
        // Store the event into private field eventToBeRemoved
        this.eventToBeRemoved = retrieveEvent(startDate, endDate, title);
        return isExistingEvent(this.eventToBeRemoved);

    }

    /** Checks if this specific event exists in the loaded Calendar. */
    private boolean isExistingEvent(VEvent event) {
        return event != null;
    }

    /**
     * Deletes an event in the loaded Calendar.
     * A call to the public isExistingEvent method has to precede this method call.
     */
    public Calendar deleteEvent() {
        if (isExistingEvent(this.eventToBeRemoved)) {
            loadedCalendar.getComponents().remove(this.eventToBeRemoved);
        }

        return loadedCalendar;
    }

    /** Returns the updated Map: existingCalendar. */
    public Map<Year, Set<Month>> updateExistingCalendar() {
        return existingCalendar;
    }

    /** Returns the number of calendar in hard disk. */
    public int size() {
        int size = 0;
        for (Year year : existingCalendar.keySet()) {
            Set<Month> monthsOfYear = existingCalendar.get(year);
            if (monthsOfYear != null) {
                size += monthsOfYear.size();
            }
        }
        return size;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof CalendarModel)) { //this handles null as well.
            return false;
        }

        CalendarModel o = (CalendarModel) other;

        return existingCalendar.equals(o.existingCalendar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(existingCalendar);
    }
}

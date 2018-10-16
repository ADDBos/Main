package seedu.address.storage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import seedu.address.commons.util.FileUtil;

//@@author GilgameshTC
/**
 * A class to access Calendars stored in the hard disk as a ics file
 */
public class IcsCalendarStorage implements CalendarStorage {
    private Path dirPath;

    public IcsCalendarStorage(Path dirPath) {
        this.dirPath = dirPath;
    }

    @Override
    public Path getCalendarPath() {
        return dirPath;
    }

    @Override
    public void createCalendar(Calendar calendar, String calendarName) throws IOException {
        String fileName = calendarName + ".ics";
        Path pathToSave = Paths.get(dirPath.toString(), fileName);
        FileUtil.createIfMissing(pathToSave);
        FileOutputStream fout = new FileOutputStream(pathToSave.toFile());
        CalendarOutputter outputter = new CalendarOutputter();
        outputter.output(calendar, fout);
    }

    @Override
    public Calendar loadCalendar(String calendarName) throws IOException, ParserException {
        String fileName = calendarName + ".ics";
        Path pathToSave = Paths.get(dirPath.toString(), fileName);
        FileInputStream fin = new FileInputStream(pathToSave.toFile());
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(fin);

        return calendar;
    }

    @Override
    public String toString() {
        return dirPath.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(dirPath);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof IcsCalendarStorage)) {
            return false;
        }
        return dirPath.equals(((IcsCalendarStorage) other).dirPath);
    }
}

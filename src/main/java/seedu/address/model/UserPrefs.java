package seedu.address.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.calendar.Month;
import seedu.address.model.calendar.Year;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private Path addressBookFilePath = Paths.get("data" , "addressbook.xml");
    private Path budgetBookFilePath = Paths.get("data", "ccabook.xml");
    private Path ccaXslFilePath = Paths.get("data", "ccabook.xsl");
    private Path emailPath = Paths.get("email");
    private Path calendarPath = Paths.get("calendar");
    private Path profilePicturePath = Paths.get("src", "main", "resources", "profile_picture");
    private Map<Year, Set<Month>> existingCalendar;

    public UserPrefs() {
        setGuiSettings(500, 500, 0, 0);
        setExistingCalendar(new HashMap<>());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }

    public Path getAddressBookFilePath() {
        return addressBookFilePath;
    }

    public Path getBudgetBookFilePath() {
        return budgetBookFilePath;
    }

    public Path getCcaXslFilePath() {
        return ccaXslFilePath;
    }


    public void setBudgetBookFilePath(Path budgetBookFilePath) {
        this.budgetBookFilePath = budgetBookFilePath;
    }

    public void setAddressBookFilePath(Path addressBookFilePath) {
        this.addressBookFilePath = addressBookFilePath;
    }

    //author@@ EatOrBeEaten
    public Path getEmailPath() {
        return emailPath;
    }

    public void setEmailPath(Path emailPath) {
        this.emailPath = emailPath;
    }

    //@@author GilgameshTC
    public Path getCalendarPath() {
        return calendarPath;
    }

    public void setCalendarPath(Path calendarPath) {
        this.calendarPath = calendarPath;
    }

    public Map<Year, Set<Month>> getExistingCalendar() {
        return existingCalendar;
    }

    public void setExistingCalendar(Map<Year, Set<Month>> existingCalendar) {
        this.existingCalendar = existingCalendar;
    }

    //@@author javenseow
    public Path getProfilePicturePath() {
        return profilePicturePath;
    }

    //@@author

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return Objects.equals(guiSettings, o.guiSettings)
                && Objects.equals(addressBookFilePath, o.addressBookFilePath)
                && Objects.equals(emailPath, o.emailPath)
                && Objects.equals(calendarPath, o.calendarPath)
                && Objects.equals(profilePicturePath, o.profilePicturePath);

    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookFilePath, calendarPath, emailPath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + addressBookFilePath);
        sb.append("\nEmail directory location : " + emailPath);
        sb.append("\nCalendar directory location : " + calendarPath);
        sb.append("\nProfile Picture directory location : " + profilePicturePath);
        return sb.toString();
    }
}

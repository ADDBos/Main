package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author GilgameshTC

/**
 * Indicates a check to toggle BrowserPlaceHolder
 */
public class ToggleBrowserPlaceholderEvent extends BaseEvent {
    public static final String CALENDAR_PANEL = "Calendar Panel";
    public static final String BROWSER_PANEL = "Browser Panel";

    public final String view;

    public ToggleBrowserPlaceholderEvent(String view) {
        this.view = view;
    }

    @Override
    public String toString() {
        return "Check if BrowserPlaceholder needs to be toggled to have " + view + " as top view";
    }
}

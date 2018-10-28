package seedu.address.commons.events.model;

import java.io.File;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Room;

//@@author javenseow
/**
 * Indicates the Profile Picture file has been read.
 */
public class NewImageEvent extends BaseEvent {

    public final File file;
    public final Room room;

    public NewImageEvent(File file, Room room) {
        this.file = file;
        this.room = room;
    }

    @Override
    public String toString() {
        return file.toString();
    }
}

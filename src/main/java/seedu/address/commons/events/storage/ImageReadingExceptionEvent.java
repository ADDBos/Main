package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates an exception during an image reading
 */
public class ImageReadingExceptionEvent extends BaseEvent {

    public final Exception exception;

    public ImageReadingExceptionEvent(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return exception.toString();
    }
}

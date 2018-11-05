package seedu.address.model.cca.exceptions;

/**
 * Signals that the operation will result in duplicate Ccas.
 * Ccas are considered duplicates if they have the same name.
 */
public class DuplicateCcaException extends RuntimeException {
    public DuplicateCcaException() {
        super("Operation would result in duplicate Ccas");
    }
}

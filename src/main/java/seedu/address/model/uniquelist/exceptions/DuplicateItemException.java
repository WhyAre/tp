package seedu.address.model.uniquelist.exceptions;

import seedu.address.model.uniquelist.UniqueList;

/**
 * Represents the exception thrown by {@link UniqueList} when an item is not
 * found
 */
public class DuplicateItemException extends Exception {
    public DuplicateItemException() {
    }
    public DuplicateItemException(String message) {
        super(message);
    }
}

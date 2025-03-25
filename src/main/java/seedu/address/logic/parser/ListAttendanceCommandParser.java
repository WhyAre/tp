package seedu.address.logic.parser;

import java.util.Objects;

import seedu.address.logic.commands.ListAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListAttendanceCommand object
 */
public class ListAttendanceCommandParser implements Parser<ListAttendanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * ListAttendanceCommand and returns a ListAttendanceCommand object for
     * execution.
     *
     * @throws ParseException
     *             if the user input does not conform the expected format
     */
    public ListAttendanceCommand parse(String unused) throws ParseException {
        Objects.requireNonNull(unused);

        return new ListAttendanceCommand();
    }

}

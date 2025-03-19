package seedu.address.logic.parser;

import java.util.Objects;

import seedu.address.logic.commands.ListTutorialCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@link ListTutorialCommand} object
 */
public class ListTutorialCommandParser implements Parser<ListTutorialCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * ListTutorialCommand and returns an ListTutorialCommand object for execution.
     *
     * @throws ParseException
     *             if the user input does not conform the expected format
     */
    public ListTutorialCommand parse(String unused) throws ParseException {
        Objects.requireNonNull(unused);

        return new ListTutorialCommand();
    }

}

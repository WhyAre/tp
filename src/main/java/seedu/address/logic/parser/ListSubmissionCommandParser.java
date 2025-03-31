package seedu.address.logic.parser;

import seedu.address.logic.commands.ListSubmissionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parser for submission list command
 */
public class ListSubmissionCommandParser implements Parser<ListSubmissionCommand> {
    @Override
    public ListSubmissionCommand parse(String userInput) throws ParseException {
        return new ListSubmissionCommand();
    }
}

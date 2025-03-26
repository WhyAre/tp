package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import seedu.address.logic.commands.MarkAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tutorial.Tutorial;

/**
 * Parses input arguments and creates a new MarkAttendanceCommand object
 */
public class MarkAttendanceCommandParser implements Parser<MarkAttendanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * MarkAttendanceCommand and returns an MarkAttendanceCommand object for
     * execution.
     *
     * @throws ParseException
     *             if the user input does not conform the expected format
     */
    public MarkAttendanceCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_WEEK, PREFIX_INDEX);

        if (!argMultimap.allPresent(PREFIX_INDEX)) {
            throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceCommand.MESSAGE_USAGE));
        }

        String tutorialString = argMultimap.getPreamble();
        // Throw parse error if no tutorial is specified.
        if (tutorialString.isEmpty()) {
            throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceCommand.MESSAGE_USAGE));
        }

        int week = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_WEEK).get()).getZeroBased() + 1;

        if (week < MarkAttendanceCommand.START_WEEK || week > MarkAttendanceCommand.END_WEEK) {
            throw new ParseException(String.format(MarkAttendanceCommand.MESSAGE_INVALID_WEEK));
        }

        int index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get()).getZeroBased();

        Tutorial tutorial = new Tutorial(tutorialString);

        return new MarkAttendanceCommand(tutorial, week, index);
    }
}

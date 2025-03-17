package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddStudentToTutorialCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tutorial.Tutorial;

/**
 * Parses input arguments and creates a new AddStudentToTutorialCommand object
 */
public class AddStudentToTutorialCommandParser implements Parser<AddStudentToTutorialCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddStudentToTutorialCommand and returns an AddStudentToTutorialCommand object
     * for execution.
     *
     * @throws ParseException
     *             if the user input does not conform the expected format
     */
    public AddStudentToTutorialCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_INDEX);

        if (!argMultimap.allPresent(PREFIX_INDEX)) {
            throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStudentToTutorialCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_INDEX);

        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        Tutorial tutorial = new Tutorial(argMultimap.getPreamble());

        return new AddStudentToTutorialCommand(index, tutorial);
    }
}

package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.ArrayList;

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

        ArrayList<Index> indices = new ArrayList<>();
        for (String indexString : argMultimap.getAllValues(PREFIX_INDEX)) {
            indices.add(ParserUtil.parseIndex(indexString));
        }

        String tutorialString = argMultimap.getPreamble();
        // Throw parse error if no tutorial is specified.
        if (tutorialString.isEmpty()) {
            throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStudentToTutorialCommand.MESSAGE_USAGE));
        }

        return new AddStudentToTutorialCommand(indices, tutorialString);
    }
}

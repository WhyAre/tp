package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteStudentFromTutorialCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tutorial.Tutorial;

/**
 * Parses input arguments and creates a new DeleteStudentFromTutorialCommand
 * object
 */
public class DeleteStudentFromTutorialCommandParser implements Parser<DeleteStudentFromTutorialCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * DeleteStudentFromTutorialCommand and returns an AddStudentToTutorialCommand
     * object for execution.
     *
     * @throws ParseException
     *             if the user input does not conform the expected format
     */
    public DeleteStudentFromTutorialCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_INDEX);

        if (!argMultimap.allPresent(PREFIX_INDEX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            DeleteStudentFromTutorialCommand.MESSAGE_USAGE));
        }

        ArrayList<Index> indices = new ArrayList<>();
        for (String indexString : argMultimap.getAllValues(PREFIX_INDEX)) {
            indices.add(ParserUtil.parseIndex(indexString));
        }

        String tutorialString = argMultimap.getPreamble();
        // Throw parse error if no tutorial is specified.
        if (tutorialString.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            DeleteStudentFromTutorialCommand.MESSAGE_USAGE));
        }

        Tutorial tutorial = new Tutorial(tutorialString);

        return new DeleteStudentFromTutorialCommand(indices, tutorial);
    }
}

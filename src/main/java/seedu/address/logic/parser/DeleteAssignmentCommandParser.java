package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.DeleteAssignmentCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_IDX;

import java.util.ArrayList;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteAssignmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tutorial.Assignment;

/**
 * Parses input arguments and creates a new {@link DeleteAssignmentCommand}
 * object
 */
public class DeleteAssignmentCommandParser implements Parser<DeleteAssignmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * DeleteAssignmentCommand and returns an DeleteAssignmentCommand object for
     * execution.
     *
     * @throws ParseException
     *             if the user input does not conform the expected format
     */
    public DeleteAssignmentCommand parse(String args) throws ParseException {
        requireNonNull(args);

        var argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TUTORIAL_IDX);

        if (!argMultimap.allPresent(PREFIX_TUTORIAL_IDX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        ArrayList<Index> indices = new ArrayList<>();
        for (String indexString : argMultimap.getAllValues(PREFIX_TUTORIAL_IDX)) {
            indices.add(ParserUtil.parseIndex(indexString));
        }

        String assignmentName = argMultimap.getPreamble();
        // Throw parse error if no assignment is specified.
        if (assignmentName.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        return new DeleteAssignmentCommand(indices, new Assignment(assignmentName, Optional.empty()));
    }
}

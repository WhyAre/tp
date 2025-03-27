package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Objects;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddAssignmentCommand;
import seedu.address.logic.commands.AddTutorialCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tutorial.Assignment;

/**
 * Parses input arguments and creates a new {@link AddTutorialCommand} object
 */
public class AddAssignmentCommandParser implements Parser<AddAssignmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddTutorialCommand and returns an AddTutorialCommand object for execution.
     *
     * @throws ParseException
     *             if the user input does not conform the expected format
     */
    public AddAssignmentCommand parse(String args) throws ParseException {
        Objects.requireNonNull(args);

        if (args.isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT.formatted(AddAssignmentCommand.MESSAGE_USAGE));
        }

        var prefixIndex = new Prefix("i/");
        var prefixDate = new Prefix("d/");
        var argMultimap = ArgumentTokenizer.tokenize(args, prefixIndex, prefixDate);

        var idxList = argMultimap.getAllValues(prefixIndex).stream().flatMap(idx -> {
            try {
                return Stream.of(ParserUtil.parseIndex(idx));
            } catch (ParseException e) {
                return Stream.empty();
            }
        }).toList();
        if (idxList.isEmpty()) {
            throw new ParseException("i/ flag is missing");
        }

        var name = argMultimap.getPreamble();
        var dueDate = argMultimap.getValue(prefixDate).map(ParserUtil::parseDateTime);

        return new AddAssignmentCommand(idxList, new Assignment(name, dueDate));
    }
}

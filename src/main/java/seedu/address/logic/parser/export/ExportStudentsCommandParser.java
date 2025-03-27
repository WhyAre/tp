package seedu.address.logic.parser.export;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Objects;

import seedu.address.logic.commands.DeleteTutorialCommand;
import seedu.address.logic.commands.export.ExportStudentsCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tutorial.Tutorial;

/**
 * Parses input arguments and creates a new {@link ExportStudentsCommand} object
 */
public class ExportStudentsCommandParser implements Parser<ExportStudentsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * ExportStudentsCommand and returns an ExportStudentsCommand object for
     * execution.
     *
     * @throws ParseException
     *             if the user input does not conform the expected format
     */
    public ExportStudentsCommand parse(String tutorialName) throws ParseException {
        Objects.requireNonNull(tutorialName);

        if (tutorialName.isEmpty()) {
            return new ExportStudentsCommand();
        }

        if (!Tutorial.isValidName(tutorialName)) {
            throw new ParseException(
                            MESSAGE_INVALID_COMMAND_FORMAT.formatted(DeleteTutorialCommand.MESSAGE_INVALID_NAME));
        }

        return new ExportStudentsCommand(new Tutorial(tutorialName));
    }

}

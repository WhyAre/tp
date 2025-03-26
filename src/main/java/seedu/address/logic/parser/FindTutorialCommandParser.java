package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FindTutorialCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tutorial.TutorialContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindTutorialCommand object
 */
public class FindTutorialCommandParser implements Parser<FindTutorialCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * FindTutorialCommand and returns a FindTutorialCommand object for execution.
     *
     * @throws ParseException
     *             if the user input does not conform the expected format
     */
    public FindTutorialCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TUTORIAL_NAME);
        String trimmedPreamble = argMultimap.getPreamble().trim();

        if (trimmedPreamble.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTutorialCommand.MESSAGE_USAGE));
        }
        List<String> tutorialKeywords = new ArrayList<>(Arrays.asList(trimmedPreamble.split("\\s+")));
        TutorialContainsKeywordsPredicate tutorialPredicate = new TutorialContainsKeywordsPredicate(tutorialKeywords);

        return new FindTutorialCommand(tutorialPredicate);
    }

}

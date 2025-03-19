package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.NameContainsKeywordsPredicate;
import seedu.address.model.tutorial.StudentContainsTutorialKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * FindCommand and returns a FindCommand object for execution.
     *
     * @throws ParseException
     *             if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TUTORIAL_NAME);
        String trimmedPreamble = argMultimap.getPreamble().trim();
        ArrayList<String> nameKeywords = trimmedPreamble.isEmpty() ? new ArrayList<>() : new ArrayList<>(Arrays.asList(trimmedPreamble.split("\\s+")));

        List<String> tempTutorialKeywords = argMultimap.getAllValues(PREFIX_TUTORIAL_NAME);
        List<String> tutorialKeywords = new ArrayList<>();

        for (String tutorial : tempTutorialKeywords) {
            String[] parts = tutorial.split("\\s+");
            if (parts.length > 1) {
                tutorialKeywords.add(parts[0]);
                nameKeywords.addAll(Arrays.asList(parts).subList(1, parts.length));
            } else {
                tutorialKeywords.add(tutorial);
            }
        }

        if (nameKeywords.isEmpty() && tutorialKeywords.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        NameContainsKeywordsPredicate namePredicate = nameKeywords.isEmpty()
                ? null
                : new NameContainsKeywordsPredicate(nameKeywords);

        StudentContainsTutorialKeywordsPredicate tutorialPredicate = tutorialKeywords.isEmpty()
                ? null
                : new StudentContainsTutorialKeywordsPredicate(tutorialKeywords);

        return new FindCommand(namePredicate, tutorialPredicate);
    }

}
